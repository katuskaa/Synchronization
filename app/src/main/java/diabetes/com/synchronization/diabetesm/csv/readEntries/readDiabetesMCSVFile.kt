package diabetes.com.synchronization.diabetesm.csv.readEntries

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.os.Environment
import android.os.FileObserver.CREATE
import diabetes.com.synchronization.common.helpers.verifyStoragePermissions
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import android.provider.Settings.System.canWrite
import diabetes.com.synchronization.BuildConfig
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.common.helpers.getISO_8601Date
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentResponseBody
import org.intellij.lang.annotations.RegExp
import java.util.regex.Pattern

/**
 *  0   "DateTimeFormatted",                     "2019-01-19 12:50:00"                   (localtime)
 *  1   "glucose",                               "13.7"
 *  2   "carbs",                                 "45.0"
 *      "proteins",
 *      "fats",
 *      "calories",
 *  6   "carb_bolus",                            "3.5"
 *      "correction_bolus",
 *      "extended_bolus",
 *      "extended_bolus_duration",
 * 10   "basal",                                 "7.0"
 *      "basal_is_rate",
 * 12   "bolus_insulin_type",                    "15"                                    (Actrapid)
 * 13   "basal_insulin_type",                    "14"                                    (Insulatard)
 *      "weight_entry",
 * 15   "weight",                                "43.3"
 * 16   "category",                              "1" "2" ...                             (pred ranajkami, ranajky, po ranajkach ...)
 *      "category_name",
 *      "carb_ratio_factor",
 *      "insulin_sensitivity_factor",
 * 20   "notes",                                 "slepačia polievka, špaldový celozrnný chlieb, volské oká, rajčiny, uhorky"
 *      "is_sensor",
 *      "pressure_sys",
 *      "pressure_dia",
 *      "pulse",
 *      "injection_bolus_site",
 *      "injection_basal_site",
 *      "finger_test_site",
 *      "ketones",
 *      "google_fit_source",
 * 30   "timezone",                              "Europe/Bratislava"
 *      "exercise_index",
 *      "exercise_comment",
 *      "exercise_duration",
 *      "medications",
 *      "food",
 * 36   "us_units",                              "false"
 *      "hba1c",
 *      "cholesterol_total",
 *      "cholesterol_ldl",
 *      "cholesterol_hdl",
 *      "triglycerides",
 *      "microalbumin_test_type",
 *      "microalbumin",
 *      "creatinine_clearance",
 *      "egfr",
 *      "cystatin_c",
 *      "albumin",
 *      "creatinine",
 *      "calcium",
 *      "total_protein",
 *      "sodium",
 *      "potassium",
 *      "bicarbonate",
 *      "chloride",
 *      "alp",
 *      "alt",
 *      "ast",
 *      "bilirubin",
 * 59   "bun"
 *
 *
 */

/**
 *  0   "DateTimeFormatted",                     "2019-01-19 12:50:00"                   (localtime Europe/Bratislava CET UTC+1)
 *  1   "glucose",                               "13.7"
 *  2   "carbs",                                 "45.0"
 *  6   "carb_bolus",                            "3.5"
 * 10   "basal",                                 "7.0"
 * 12   "bolus_insulin_type",                    "15"                                    (Actrapid)
 * 13   "basal_insulin_type",                    "14"                                    (Insulatard)
 * 15   "weight",                                "43.3"
 * 16   "category",                              "1" "2" ...                             (pred ranajkami, ranajky, po ranajkach ...)
 * 20   "notes",                                 "slepačia polievka, špaldový celozrnný chlieb, volské oká, rajčiny, uhorky"
 * 30   "timezone",                              "Europe/Bratislava"
 */
open class readDiabetesMCSVFile(val countOfDays: Int) {

/**
    *  1   "DateTimeFormatted",                     "2019-01-19 12:50:00"                   (localtime)
    *  2   "glucose",                               "13.7"
    *  3   "carbs",                                 "45.0"
    *      "proteins",
    *      "fats",
    *      "calories",
    *  7   "carb_bolus",                            "3.5"
    *      "correction_bolus",
    *      "extended_bolus",
    *      "extended_bolus_duration",
    * 11   "basal",                                 "7.0"
    *      "basal_is_rate",
    * 13   "bolus_insulin_type",                    "15"                                    (Actrapid)
    * 14   "basal_insulin_type",                    "14"                                    (Insulatard)
    *      "weight_entry",
    * 16   "weight",                                "43.3"
    * 17   "category",                              "1" "2" ...                             (pred ranajkami, ranajky, po ranajkach ...)
    *      "category_name",
    *      "carb_ratio_factor",
    *      "insulin_sensitivity_factor",
    * 20   "notes",                                 "slepačia polievka, špaldový celozrnný chlieb, volské oká, rajčiny, uhorky"
    *      "is_sensor",
    *      "pressure_sys",
    *      "pressure_dia",
    *      "pulse",
    *      "injection_bolus_site",
    *      "injection_basal_site",
    *      "finger_test_site",
    *      "ketones",
    *      "google_fit_source",
    * 29   "timezone",                              "Europe/Bratislava"
*/

    val CSVIDX_DateTimeFormatted    =  1
    //val CSVIDX_glucose              =  2
    val CSVIDX_carbs                =  3
    val CSVIDX_carb_bolus           =  7
    //val CSVIDX_basal                = 11
    //val CSVIDX_bolus_insulin_type   = 13
    //val CSVIDX_basal_insulin_type   = 14
    //val CSVIDX_weight               = 16
    //val CSVIDX_category             = 17
    val CSVIDX_notes                = 20
    val CSVIDX_timezone             = 29

    init {

        var fileReader :BufferedReader? = null

        try {
            var line: String?

            var filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            println(filePath)
            fileReader = BufferedReader(FileReader(filePath.toString() +  "/mydiabetes-entries-export.csv"))

            //fileReader = BufferedReader(FileReader("/data/data/com.mydiabetes/files/mydiabetes-entries-export-201901201144.csv"))
            //fileReader = BufferedReader(FileReader("/data/data/diabetes.com.synchronization/mydiabetes-entries-export-201901201143.csv"))
            //fileReader = BufferedReader(FileReader("/data/mydiabetes-entries-export-201901201143.csv"))
            //fileReader = BufferedReader(FileReader("/mydiabetes-entries-export-201901201143.csv"))

            //val postTreatmentRequestDatas = ArrayList<PostTreatmentRequestData>()
            val postTreatmentLiveData = MutableLiveData<ResponseLiveData<Array<PostTreatmentResponseBody>>>()

            line = fileReader.readLine()
            println(line)

            // Read CSV header line
            line = fileReader.readLine()
            println(line)

            // Read the file line by line starting from the second line
            //val linePattern = "(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|\$))".toPattern()
            //val lineRegex = Regex("(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|\$))")
            //val lineRegex = Regex("\"([^\"]*)\"")
            val lineRegex = Regex("(\",+\")|(^\")|(\"$)")
            //lineRegex.pattern = "(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|\$))"
            //lineRegex.pattern = "aaa"
            line = fileReader.readLine()
            while (line != null) {

                val tokens = lineRegex.split(line)
                //val tokens = line.split("(\",\")|(,,\")|(\",,)|(,,)".toPattern())
                if (tokens.size == 60) {
//                    val diabetesMDateTimeFormatted  = tokens[CSVIDX_DateTimeFormatted].split("\"")[1]
//                    val diabetesMTimeZone           = tokens[CSVIDX_timezone].split("\"")[1]
//                    val diabetesMCarb_bolus         = tokens[CSVIDX_carb_bolus].split("\"")[1].toFloat()
//                    val diabetesMCarbs              = tokens[CSVIDX_carbs].split("\"")[1].toFloat().toInt()
//                    val diabetesMNotes              = tokens[CSVIDX_notes].split("\"")[1]
                    val diabetesMDateTimeFormatted  = tokens[CSVIDX_DateTimeFormatted]
                    val diabetesMTimeZone           = tokens[CSVIDX_timezone]
                    val diabetesMCarb_bolus         = tokens[CSVIDX_carb_bolus].toFloat()
                    val diabetesMCarbs              = tokens[CSVIDX_carbs].toFloat().toInt()
                    val diabetesMNotes              = tokens[CSVIDX_notes]
                    if(diabetesMDateTimeFormatted.compareTo("2019-01-12") > 0)
                    {
                        val iso8601date = getISO_8601Date(diabetesMDateTimeFormatted, diabetesMTimeZone)
                        if(diabetesMCarb_bolus > 0.0f) {
                            println("carb_bolus: " + tokens[CSVIDX_carb_bolus] + " line: " + line);
                            val postTreatmentRequestData = PostTreatmentRequestData(
                                    BuildConfig.SERVER_URL_TOKEN.split("-")[0],
                                    "Meal Bolus",
                                    "",
                                    0,
                                    0,
                                    iso8601date,
                                    diabetesMCarb_bolus,
                                    "")
                            //postTreatmentRequestDatas.add(postTreatmentRequestData)
                            BaseApplication.applicationServer.executePostTreatmentTransaction(postTreatmentRequestData, postTreatmentLiveData)
                        }
                        if (diabetesMCarbs > 0)
                        {
                            println("carbs: " + tokens[CSVIDX_carbs] + " line: " + line);
                            val postTreatmentRequestData = PostTreatmentRequestData(
                                    BuildConfig.SERVER_URL_TOKEN.split("-")[0],
                                    "Carb Correction",
                                    "",
                                    diabetesMCarbs,
                                    0,
                                    iso8601date,
                                    0.0f,
                                    diabetesMNotes)
                            BaseApplication.applicationServer.executePostTreatmentTransaction(postTreatmentRequestData, postTreatmentLiveData)
                        }
                    }
                }

                line = fileReader.readLine()
            }


        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader Error!")
                e.printStackTrace()
            }
        }
    }
}