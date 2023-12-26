package www.onbeatapps.com.utils

//import me.kariot.invoicegenerator.data.*
//import me.kariot.invoicegenerator.utils.InvoiceGenerator
import android.util.Log
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object{

        var pageHeight = 1120
        var pagewidth = 792

        fun dateConvert(date: String): String {
            val strDate = date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")//"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            val output = SimpleDateFormat("dd.MM.yyyy hh:mm aa")
            val date = dateFormat.parse(strDate)
            val data1 = output.format(date)
            return data1.toString()
        }
        fun TimStamp():String{
            return   SimpleDateFormat("yyyyMMddHHmm").format( Date())
        }

        fun NumberFormatForCurrencyCode(number:String): String {

            val currentLocale = Locale.getDefault()
            var formattedCurrency =
                DecimalFormat.getCurrencyInstance(currentLocale).format(number.toDouble())
            val currencyCode = Currency.getInstance(currentLocale)
            val symbol: String = currencyCode.symbol
                formattedCurrency = formattedCurrency.replace(symbol,"")

//            val nf = NumberFormat.getCurrencyInstance(Locale(Code))
//            val pattern = (nf as DecimalFormat).toPattern()
//            val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
//            val newFormat: NumberFormat = DecimalFormat(newPattern)
            return formattedCurrency
        }

        fun convetDateToTime(dateConvet:String): String {
            var date: Date? = null
            var time =""
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss")
            val temp = "Thu Dec 17 15:37:43 GMT+05:30 2015"
            try {
                date = formatter.parse(dateConvet.replace(" GMT",""))
                time =SimpleDateFormat("HH:mm").format(date)
                Log.e("formated date ", date.toString() + "")
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return time
        }

        fun artisInfoConvert(dateConvet:String):String{
            var date: Date? = null
            var time =""
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss")
            try {
                date = formatter.parse(dateConvet.replace(" GMT",""))
                time =SimpleDateFormat("EEEE dd/MM").format(date)

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return time
        }

    }
}