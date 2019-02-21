package diabetes.com.synchronization.diabetesm.csv.readEntries

import java.io.Serializable

class Treatment(var id: String?, val dateTimeFormatted: String?, val carbs: Float?, val carbBolus: Float?, val notes: String?) : Serializable {

    companion object {
        val DATE_TIME_FORMATTED__COLUMN = "dateTimeFormatted"
        val CARBS__COLUMN = "carbs"
        val CARB_BOLUS__COLUMN = "carb_bolus"
        val NOTES__COLUMN = "notes"
        val TIMEZONE__COLUMN = "timezone"
    }

}