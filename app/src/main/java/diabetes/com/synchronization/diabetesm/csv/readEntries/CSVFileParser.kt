package diabetes.com.synchronization.diabetesm.csv.readEntries

import android.arch.lifecycle.MutableLiveData
import android.os.Environment
import diabetes.com.synchronization.common.base.application.BaseApplication
import diabetes.com.synchronization.common.data.transaction.ResponseLiveData
import diabetes.com.synchronization.common.helpers.TIME_FORMAT__ISO_8601
import diabetes.com.synchronization.common.helpers.convertDiabetesMDateToISO8601
import diabetes.com.synchronization.common.helpers.getEnteredBy
import diabetes.com.synchronization.communication.network.treatments.getTreatments.GetTreatmentsResponseBody
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentRequestData
import diabetes.com.synchronization.communication.network.treatments.postTreatment.PostTreatmentResponseBody
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*


class CSVFileParser(val getTreatments: Array<GetTreatmentsResponseBody>) {

    private val DIABETESM_FILE_PREFIX = "mydiabetes-entries-export"
    private val DIABETESM_DATE_FILE_FORMAT = "yyyyMMddhhmm"

    private val postTreatmentLiveData = MutableLiveData<ResponseLiveData<Array<PostTreatmentResponseBody>>>()

    fun parseExport(interval: Int): Boolean {
        val file = findNewestExportFile()

        if (file != null) {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            bufferedReader.readLine()

            val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())

            val dateFormat = SimpleDateFormat(TIME_FORMAT__ISO_8601, Locale.ENGLISH)
            val date = getCalendarDate(interval)

            for (csvRecord in csvParser) {
                val dateTime = csvRecord.get(Treatment.DATE_TIME_FORMATTED__COLUMN)
                val timezone = csvRecord.get(Treatment.TIMEZONE__COLUMN)
                val dateTimeFormatted = convertDiabetesMDateToISO8601(dateTime, timezone)
                val carbs = csvRecord.get(Treatment.CARBS__COLUMN).toFloat()
                val carbBolus = csvRecord.get(Treatment.CARB_BOLUS__COLUMN).toFloat()
                val notes = csvRecord.get(Treatment.NOTES__COLUMN)

                if (dateFormat.parse(dateTimeFormatted).before(date)) {
                    break
                }

                val treatment = Treatment(null, dateTimeFormatted, carbs, carbBolus, notes)
                val identifiedTreatment = identifyTreatment(treatment)

                if (identifiedTreatment.id != null) {
                    //TODO should be update - not found in documentation, probably delete followed by insert
                } else {
                    postTreatment(identifiedTreatment)
                }
            }

            bufferedReader.close()

            return true
        }

        return false
    }

    private fun findNewestExportFile(): File? {
        val filePath = Environment.getExternalStorageDirectory()
        val foundFiles = filePath.listFiles { _, name -> name.startsWith(DIABETESM_FILE_PREFIX) }

        if (foundFiles.isNotEmpty()) {
            foundFiles.sortWith(Comparator { file1, file2 ->
                getDateFromDiabetesMExportFile(file2.name).compareTo(getDateFromDiabetesMExportFile(file1.name))
            })

            val files = foundFiles.toMutableList()
            val file = files.removeAt(0)
            deleteOlderExports(files)

            return file
        }

        return null
    }

    private fun getDateFromDiabetesMExportFile(fileName: String): Date {
        val dateFormat = SimpleDateFormat(DIABETESM_DATE_FILE_FORMAT, Locale.ENGLISH)
        return dateFormat.parse(fileName.substring(DIABETESM_FILE_PREFIX.length + 1, fileName.length - 4))
    }

    private fun deleteOlderExports(files: MutableList<File>) {
        files.forEach { it.delete() }
    }

    private fun getCalendarDate(interval: Int): Date {
        val calendar = Calendar.getInstance()

        calendar.time = Date()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        calendar.add(Calendar.DATE, -interval)

        return calendar.time
    }

    private fun postTreatment(treatment: Treatment) {
        treatment.carbBolus?.let { carbBolus ->
            if (carbBolus > 0.0f) {
                postMealBolus(treatment)
            }
        }

        treatment.carbs?.let { carbs ->
            if (carbs > 0.0f) {
                postCarbCorrection(treatment)
            }
        }
    }

    private fun postMealBolus(treatment: Treatment) {
        val postTreatmentRequestData = PostTreatmentRequestData(getEnteredBy(), EventType.MEAL_BOLUS.type, 0.0f, treatment.dateTimeFormatted!!, treatment.carbBolus!!, "")

        BaseApplication.applicationServer.executePostTreatmentTransaction(postTreatmentRequestData, postTreatmentLiveData)
    }

    private fun postCarbCorrection(treatment: Treatment) {
        val postTreatmentRequestData = PostTreatmentRequestData(getEnteredBy(), EventType.CARB_CORRECTION.type, treatment.carbs!!, treatment.dateTimeFormatted!!, 0.0f, treatment.notes!!)

        BaseApplication.applicationServer.executePostTreatmentTransaction(postTreatmentRequestData, postTreatmentLiveData)
    }

    private fun identifyTreatment(treatment: Treatment): Treatment {
        getTreatments.forEach {
            if (it.createdAt == treatment.dateTimeFormatted && ((it.eventType == EventType.CARB_CORRECTION.type && treatment.carbs!! > 0.0f) || (it.eventType == EventType.MEAL_BOLUS.type && treatment.carbBolus!! > 0.0f))) {
                treatment.id = it.id
                return treatment
            }
        }

        return treatment
    }
}

