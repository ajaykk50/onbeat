package www.onbeatapps.com.utils

import android.R
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatTextView

import androidx.appcompat.widget.LinearLayoutCompat

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View

import androidx.annotation.LayoutRes

import android.widget.ArrayAdapter
import androidx.annotation.Nullable

import androidx.appcompat.widget.AppCompatSpinner


class CountryCodeSpinner  : AppCompatSpinner{
    private  var  arrayList: ArrayList<CountryModel>? = null
    private  var  countryCodeAdapter: CountryCodeAdapter? = null
    constructor(  context: Context?) : super(context!!){}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0){
        fillCountryList()
//        countryCodeAdapter = CountryCodeAdapter(context!!, arrayList!!)
        countryCodeAdapter =  CountryCodeAdapter(context!!, R.layout.simple_spinner_item,
            arrayList!!
        )
        super.setAdapter(countryCodeAdapter)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: kotlin.Int) : super(context!!, attrs, defStyle){
        fillCountryList()
//        countryCodeAdapter = CountryCodeAdapter(context!!, arrayList!!)
        countryCodeAdapter =  CountryCodeAdapter(context, R.layout.simple_spinner_item,
            arrayList!!
        )
        super.setAdapter(countryCodeAdapter)
    }
    private   fun fillCountryList(){
        //https://emojipedia.org/flags/
        arrayList = ArrayList()
        arrayList!!.clear()
        val add =
            arrayList!!.add(CountryModel("af", "Afghanistan", "+93", "🇦🇫"))
        arrayList!!.add(CountryModel("al", "Albania", "+355", "🇦🇱"))
        arrayList!!.add(CountryModel("dz", "Algeria", "+213", "🇩🇿"))
        arrayList!!.add(CountryModel("as", "American Samoa", "+1684", "🇦🇸"))
        arrayList!!.add(CountryModel("ad", "Andorra", "+376", "🇦🇩"))
        arrayList!!.add(CountryModel("ao", "Angola", "+244", "🇦🇴"))
        arrayList!!.add(CountryModel("ai", "Anguilla", "+1264", "🇦🇮"))
        arrayList!!.add(CountryModel("aq", "Antarctica", "+672", "🇦🇶"))
        arrayList!!.add(CountryModel("ag", "Antigua and Barbuda", "+1268", "🇦🇬"))
        arrayList!!.add(CountryModel("ar", "Argentina", "+54", "🇦🇷"))
        arrayList!!.add(CountryModel("am", "Armenia", "+374", "🇦🇲"))
        arrayList!!.add(CountryModel("aw", "Aruba", "+297", "🇦🇼"))
        arrayList!!.add(CountryModel("au", "Australia", "+61", "🇦🇺"))
        arrayList!!.add(CountryModel("at", "Austria", "+43", "🇦🇹"))
        arrayList!!.add(CountryModel("az", "Azerbaijan", "+994", "🇦🇿"))
        arrayList!!.add(CountryModel("bs", "Bahamas", "+1242", "🇧🇸"))
        arrayList!!.add(CountryModel("bh", "Bahrain", "+973", "🇧🇭"))
        arrayList!!.add(CountryModel("bd", "Bangladesh", "+880", "🇧🇩"))
        arrayList!!.add(CountryModel("bb", "Barbados", "+1242", "🇧🇧"))
        arrayList!!.add(CountryModel("by", "Belarus", "+375", "🇧🇾"))
        arrayList!!.add(CountryModel("be", "Belgium", "+32", "🇧🇪"))
        arrayList!!.add(CountryModel("bz", "Belize", "+501", "🇧🇿"))
        arrayList!!.add(CountryModel("bj", "Benin", "+229", "🇧🇯"))
        arrayList!!.add(CountryModel("bm", "Bermuda", "+1441", "🇧🇲"))
        arrayList!!.add(CountryModel("bt", "Bhutan", "+975", "🇧🇹"))
        arrayList!!.add(CountryModel("bo", "Bolivia", "+591", "🇧🇴"))
        arrayList!!.add(CountryModel("ba", "Bosnia And Herzegovina", "+387", "🇧🇦"))
        arrayList!!.add(CountryModel("bw", "Botswana", "+267", "🇧🇼"))
        arrayList!!.add(CountryModel("br", "Brazil", "+55", "🇧🇷"))
        arrayList!!.add(CountryModel("io", "British Indian Ocean Territory", "+246", "🇮🇴"))
        arrayList!!.add(CountryModel("vg", "British Virgin Islands", "+1284", "🇻🇬"))
        arrayList!!.add(CountryModel("bn", "Brunei Darussalam", "+673", "🇧🇳"))
        arrayList!!.add(CountryModel("bg", "Bulgaria", "+359", "🇧🇬"))
        arrayList!!.add(CountryModel("bf", "Burkina Faso", "+226", "🇧🇫"))
        arrayList!!.add(CountryModel("bi", "Burundi", "+257", "🇧🇮"))
        arrayList!!.add(CountryModel("kh", "Cambodia", "+855", "🇰🇭"))
        arrayList!!.add(CountryModel("cm", "Cameroon", "+237", "🇨🇲"))
        arrayList!!.add(CountryModel("ca", "Canada", "+1", "🇨🇦"))
        arrayList!!.add(CountryModel("cv", "Cape Verde", "+238", "🇨🇻"))
        arrayList!!.add(CountryModel("ky", "Cayman Islands", "+345", "🇰🇾"))
        arrayList!!.add(CountryModel("cf", "Central African Republic", "+236", "🇨🇫"))
        arrayList!!.add(CountryModel("td", "Chad", "+235", "🇹🇩"))
        arrayList!!.add(CountryModel("cl", "Chile", "+56", "🇨🇱"))
        arrayList!!.add(CountryModel("cn", "China", "+86", "🇨🇳"))
        arrayList!!.add(CountryModel("cx", "Christmas Island", "+61", "🇨🇽"))
        arrayList!!.add(CountryModel("cc", "Cocos (keeling) Islands", "+61", "🇨🇨"))
        arrayList!!.add(CountryModel("co", "Colombia", "+57", "🇨🇴"))
        arrayList!!.add(CountryModel("km", "Comoros", "+269", "🇰🇲"))
        arrayList!!.add(CountryModel("ck", "Cook Islands", "+682", "🇨🇰"))
        arrayList!!.add(CountryModel("cr", "Costa Rica", "+506", "🇨🇷"))
        arrayList!!.add(CountryModel("hr", "Croatia", "+385", "🇭🇷"))
        arrayList!!.add(CountryModel("cu", "Cuba", "+53", "🇨🇺"))
        arrayList!!.add(CountryModel("cy", "Cyprus", "+357", "🇨🇾"))
        arrayList!!.add(CountryModel("cz", "Czech Republic", "+420", "🇨🇿"))
        arrayList!!.add(CountryModel("ci", "Côte D'ivoire", "+225", "🇨🇮"))
        arrayList!!.add(CountryModel("cd", "Democratic Republic of the Congo", "+243", "🇨🇩"))
        arrayList!!.add(CountryModel("dk", "Denmark", "+45", "🇩🇰"))
        arrayList!!.add(CountryModel("dj", "Djibouti", "+253", "🇩🇯"))
        arrayList!!.add(CountryModel("dm", "Dominica", "+1767", "🇩🇲"))
        arrayList!!.add(CountryModel("do", "Dominican Republic", "+1849", "🇩🇴"))
        arrayList!!.add(CountryModel("ec", "Ecuador", "+593", "🇪🇨"))
        arrayList!!.add(CountryModel("eg", "Egypt", "+20", "🇪🇬"))
        arrayList!!.add(CountryModel("sv", "El Salvador", "+503", "🇸🇻"))
        arrayList!!.add(CountryModel("gq", "Equatorial Guinea", "+240", "🇬🇶"))
        arrayList!!.add(CountryModel("er", "Eritrea", "+291", "🇪🇷"))
        arrayList!!.add(CountryModel("ee", "Estonia", "+372", "🇪🇪"))
        arrayList!!.add(CountryModel("et", "Ethiopia", "+251", "🇪🇹"))
        arrayList!!.add(CountryModel("fk", "Falkland Islands (malvinas)", "+500", "🇫🇰"))
        arrayList!!.add(CountryModel("fo", "Faroe Islands", "+298", "🇫🇴"))
        arrayList!!.add(CountryModel("fj", "Fiji", "+679", "🇫🇯"))
        arrayList!!.add(CountryModel("fi", "Finland", "+358", "🇫🇮"))
        arrayList!!.add(CountryModel("fr", "France", "+33", "🇫🇷"))
        arrayList!!.add(CountryModel("gf", "French Guiana", "+594", "🇬🇫"))
        arrayList!!.add(CountryModel("pf", "French Polynesia", "+689", "🇵🇫"))
        arrayList!!.add(CountryModel("ga", "Gabon", "+241", "🇬🇦"))
        arrayList!!.add(CountryModel("gm", "Gambia", "+220", "🇬🇲"))
        arrayList!!.add(CountryModel("ge", "Georgia", "+995", "🇬🇪"))
        arrayList!!.add(CountryModel("de", "Germany", "+49", "🇩🇪"))
        arrayList!!.add(CountryModel("gh", "Ghana", "+233", "🇬🇭"))
        arrayList!!.add(CountryModel("gi", "Gibraltar", "+350", "🇬🇮"))
        arrayList!!.add(CountryModel("gr", "Greece", "+30", "🇬🇷"))
        arrayList!!.add(CountryModel("gl", "Greenland", "+299", "🇬🇱"))
        arrayList!!.add(CountryModel("gd", "Grenada", "+1473", "🇬🇩"))
        arrayList!!.add(CountryModel("gp", "Guadeloupe", "+590", "🇬🇵"))
        arrayList!!.add(CountryModel("gu", "Guam", "+1671", "🇬🇺"))
        arrayList!!.add(CountryModel("gt", "Guatemala", "+502", "🇬🇹"))
        arrayList!!.add(CountryModel("gg", "Guernsey", "+44", "🇬🇬"))
        arrayList!!.add(CountryModel("gn", "Guinea", "+224", "🇬🇳"))
        arrayList!!.add(CountryModel("gw", "Guinea-Bissau", "+245", "🇬🇼"))
        arrayList!!.add(CountryModel("gy", "Guyana", "+592", "🇬🇾"))
        arrayList!!.add(CountryModel("ht", "Haiti", "+509", "🇭🇹"))
        arrayList!!.add(CountryModel("va", "Holy See (Vatican City State)", "+379", "🇻🇦"))
        arrayList!!.add(CountryModel("hn", "Honduras", "+504", "🇭🇳"))
        arrayList!!.add(CountryModel("hk", "Hong Kong", "+852", "🇭🇰"))
        arrayList!!.add(CountryModel("hu", "Hungary", "+36", "🇭🇺"))
        arrayList!!.add(CountryModel("is", "Iceland", "+354", "🇮🇸"))
        arrayList!!.add(CountryModel("in", "India", "+91", "🇮🇳"))
        arrayList!!.add(CountryModel("id", "Indonesia", "+62", "🇮🇩"))
        arrayList!!.add(CountryModel("ir", "Iran", "+98", "🇮🇷"))
        arrayList!!.add(CountryModel("iq", "Iraq", "+964", "🇮🇶"))
        arrayList!!.add(CountryModel("ie", "Ireland", "+353", "🇮🇪"))
        arrayList!!.add(CountryModel("im", "Isle Of Man", "+44", "🇮🇲"))
        arrayList!!.add(CountryModel("il", "Israel", "+972", "🇮🇱"))
        arrayList!!.add(CountryModel("it", "Italy", "+39", "🇮🇹"))
        arrayList!!.add(CountryModel("jm", "Jamaica", "+1876", "🇯🇲"))
        arrayList!!.add(CountryModel("jp", "Japan", "+81", "🇯🇵"))
        arrayList!!.add(CountryModel("je", "Jersey", "+44", "🇯🇪"))
        arrayList!!.add(CountryModel("jo", "Jordan", "+962", "🇯🇴"))
        arrayList!!.add(CountryModel("kz", "Kazakhstan", "+7", "🇰🇿"))
        arrayList!!.add(CountryModel("ke", "Kenya", "+254", "🇰🇪"))
        arrayList!!.add(CountryModel("ki", "Kiribati", "+686", "🇰🇮"))
        arrayList!!.add(CountryModel("xk", "Kosovo", "+383", "🇽🇰"))
        arrayList!!.add(CountryModel("kw", "Kuwait", "+965", "🇰🇼"))
        arrayList!!.add(CountryModel("kg", "Kyrgyzstan", "+996", "🇰🇬"))
        arrayList!!.add(CountryModel("la", "Lao People's Democratic Republic", "+856", "🇱🇦"))
        arrayList!!.add(CountryModel("lv", "Latvia", "+371", "🇱🇻"))
        arrayList!!.add(CountryModel("lb", "Lebanon", "+961", "🇱🇧"))
        arrayList!!.add(CountryModel("ls", "Lesotho", "+266", "🇱🇸"))
        arrayList!!.add(CountryModel("lr", "Liberia", "+231", "🇱🇷"))
        arrayList!!.add(CountryModel("ly", "Libya", "+218", "🇱🇾"))
        arrayList!!.add(CountryModel("li", "Liechtenstein", "+423", "🇱🇮"))
        arrayList!!.add(CountryModel("lt", "Lithuania", "+370", "🇱🇹"))
        arrayList!!.add(CountryModel("lu", "Luxembourg", "+352", "🇱🇺"))
        arrayList!!.add(CountryModel("mo", "Macao Sar China", "+853", "🇲🇴"))
        arrayList!!.add(CountryModel("mk", "Macedonia", "+389", "🇲🇰"))
        arrayList!!.add(CountryModel("mg", "Madagascar", "+261", "🇲🇬"))
        arrayList!!.add(CountryModel("mw", "Malawi", "+265", "🇲🇼"))
        arrayList!!.add(CountryModel("my", "Malaysia", "+60", "🇲🇾"))
        arrayList!!.add(CountryModel("mv", "Maldives", "+960", "🇲🇻"))
        arrayList!!.add(CountryModel("ml", "Mali", "+223", "🇲🇱"))
        arrayList!!.add(CountryModel("mt", "Malta", "+356", "🇲🇹"))
        arrayList!!.add(CountryModel("mh", "Marshall Islands", "+692", "🇲🇭"))
        arrayList!!.add(CountryModel("mq", "Martinique", "+596", "🇲🇶"))
        arrayList!!.add(CountryModel("mr", "Mauritania", "+222", "🇲🇷"))
        arrayList!!.add(CountryModel("mu", "Mauritius", "+230", "🇲🇺"))
        arrayList!!.add(CountryModel("yt", "Mayotte", "+262", "🇾🇹"))
        arrayList!!.add(CountryModel("mx", "Mexico", "+52", "🇲🇽"))
        arrayList!!.add(CountryModel("fm", "Micronesia", "+691", "🇫🇲"))
        arrayList!!.add(CountryModel("md", "Moldova", "+373", "🇲🇩"))
        arrayList!!.add(CountryModel("mc", "Monaco", "+377", "🇲🇨"))
        arrayList!!.add(CountryModel("mn", "Mongolia", "+976", "🇲🇳"))
        arrayList!!.add(CountryModel("me", "Montenegro", "+382", "🇲🇪"))
        arrayList!!.add(CountryModel("ms", "Montserrat", "+1664", "🇲🇸"))
        arrayList!!.add(CountryModel("ma", "Morocco", "+212", "🇲🇦"))
        arrayList!!.add(CountryModel("mz", "Mozambique", "+258", "🇲🇿"))
        arrayList!!.add(CountryModel("mm", "Myanmar (Burma)", "+95", "🇲🇲"))
        arrayList!!.add(CountryModel("na", "Namibia", "+264", "🇳🇦"))
        arrayList!!.add(CountryModel("nr", "Nauru", "+674", "🇳🇷"))
        arrayList!!.add(CountryModel("np", "Nepal", "+977", "🇳🇵"))
        arrayList!!.add(CountryModel("nl", "Netherlands", "+31", "🇳🇱"))
        arrayList!!.add(CountryModel("nc", "New Caledonia", "+687", "🇳🇨"))
        arrayList!!.add(CountryModel("nz", "New Zealand", "+64", "🇳🇿"))
        arrayList!!.add(CountryModel("ni", "Nicaragua", "+505", "🇳🇮"))
        arrayList!!.add(CountryModel("ne", "Niger", "+227", "🇳🇪"))
        arrayList!!.add(CountryModel("ng", "Nigeria", "+234", "🇳🇬"))
        arrayList!!.add(CountryModel("nu", "Niue", "+683", "🇳🇺"))
        arrayList!!.add(CountryModel("nf", "Norfolk Island", "+1670", "🇳🇫"))
        arrayList!!.add(CountryModel("kp", "North Korea", "+672", "🇰🇵"))
        arrayList!!.add(CountryModel("mp", "Northern Mariana Islands", "+850", "🇲🇵"))
        arrayList!!.add(CountryModel("no", "Norway", "+47", "🇳🇴"))
        arrayList!!.add(CountryModel("om", "Oman", "+968", "🇴🇲"))
        arrayList!!.add(CountryModel("pk", "Pakistan", "+92", "🇵🇰"))
        arrayList!!.add(CountryModel("pw", "Palau", "+680", "🇵🇼"))
        arrayList!!.add(CountryModel("ps", "Palestinian Territory, Occupied", "+970", "🇵🇸"))
        arrayList!!.add(CountryModel("pa", "Panama", "+507", "🇵🇦"))
        arrayList!!.add(CountryModel("pg", "Papua New Guinea", "+675", "🇵🇬"))
        arrayList!!.add(CountryModel("py", "Paraguay", "+595", "🇵🇾"))
        arrayList!!.add(CountryModel("pe", "Peru", "+51", "🇵🇪"))
        arrayList!!.add(CountryModel("ph", "Philippines", "+63", "🇵🇭"))
        arrayList!!.add(CountryModel("pn", "Pitcairn Islands", "+870", "🇵🇳"))
        arrayList!!.add(CountryModel("pl", "Poland", "+48", "🇵🇱"))
        arrayList!!.add(CountryModel("pt", "Portugal", "+351", "🇵🇹"))
        arrayList!!.add(CountryModel("pr", "Puerto Rico", "+1939", "🇵🇷"))
        arrayList!!.add(CountryModel("qa", "Qatar", "+974", "🇶🇦"))
        arrayList!!.add(CountryModel("cg", "Republic of the Congo - Brazzaville", "+242", "🇨🇬"))
        arrayList!!.add(CountryModel("ro", "Romania", "+40", "🇷🇴"))
        arrayList!!.add(CountryModel("ru", "Russian Federation", "+7", "🇷🇺"))
        arrayList!!.add(CountryModel("rw", "Rwanda", "+250", "🇷🇼"))
        arrayList!!.add(CountryModel("re", "Réunion", "+262", "🇷🇪"))
        arrayList!!.add(CountryModel("bl", "Saint Barthélemy", "+590", "🇧🇱"))
        arrayList!!.add(CountryModel("sh", "Saint Helena", "+290", "🇸🇭"))
        arrayList!!.add(CountryModel("kn", "Saint Kitts & Nevis", "+1869", "🇰🇳"))
        arrayList!!.add(CountryModel("lc", "Saint Lucia", "+1758", "🇱🇨"))
        arrayList!!.add(CountryModel("mf", "Saint Martin", "+590", "🇲🇫"))
        arrayList!!.add(CountryModel("pm", "Saint Pierre & Miquelon", "+508", "🇵🇲"))
        arrayList!!.add(CountryModel("vc", "Saint Vincent & The Grenadines", "+1784", "🇻🇨"))
        arrayList!!.add(CountryModel("ws", "Samoa", "+685", "🇼🇸"))
        arrayList!!.add(CountryModel("sm", "San Marino", "+378", "🇸🇲"))
        arrayList!!.add(CountryModel("st", "Sao Tome & Principe", "+239", "🇸🇹"))
        arrayList!!.add(CountryModel("sa", "Saudi Arabia", "+966", "🇸🇦"))
        arrayList!!.add(CountryModel("sn", "Senegal", "+221", "🇸🇳"))
        arrayList!!.add(CountryModel("rs", "Serbia", "+381", "🇷🇸"))
        arrayList!!.add(CountryModel("sc", "Seychelles", "+248", "🇸🇨"))
        arrayList!!.add(CountryModel("sl", "Sierra Leone", "+232", "🇸🇱"))
        arrayList!!.add(CountryModel("sg", "Singapore", "+65", "🇸🇬"))
        arrayList!!.add(CountryModel("sx", "Sint Maarten", "+1", "🇸🇽"))
        arrayList!!.add(CountryModel("sk", "Slovakia", "+421", "🇸🇰"))
        arrayList!!.add(CountryModel("si", "Slovenia", "+386", "🇸🇮"))
        arrayList!!.add(CountryModel("sb", "Solomon Islands", "+677", "🇸🇧"))
        arrayList!!.add(CountryModel("so", "Somalia", "+252", "🇸🇴"))
        arrayList!!.add(CountryModel("za", "South Africa", "+27", "🇿🇦"))
        arrayList!!.add(CountryModel("gs", "South Africa (South Georgia & South Sandwich Islands)", "+500", "🇬🇸"))
        arrayList!!.add(CountryModel("kr", "South Korea", "+82", "🇰🇷"))
        arrayList!!.add(CountryModel("ss", "South Sudan", "+211", "🇸🇸"))
        arrayList!!.add(CountryModel("es", "Spain", "+34", "🇪🇸"))
        arrayList!!.add(CountryModel("lk", "Sri Lanka", "+94", "🇱🇰"))
        arrayList!!.add(CountryModel("sd", "Sudan", "+249", "🇸🇩"))
        arrayList!!.add(CountryModel("sr", "Suriname", "+597", "🇸🇷"))
        arrayList!!.add(CountryModel("sz", "Swaziland", "+268", "🇸🇿"))
        arrayList!!.add(CountryModel("se", "Sweden", "+46", "🇸🇪"))
        arrayList!!.add(CountryModel("ch", "Switzerland", "+41", "🇨🇭"))
        arrayList!!.add(CountryModel("sy", "Syrian Arab Republic", "+963", "🇸🇾"))
        arrayList!!.add(CountryModel("tw", "Taiwan", "+886", "🇹🇼"))
        arrayList!!.add(CountryModel("tj", "Tajikistan", "+992", "🇹🇯"))
        arrayList!!.add(CountryModel("tz", "Tanzania", "+255", "🇹🇿"))
        arrayList!!.add(CountryModel("th", "Thailand", "+66", "🇹🇭"))
        arrayList!!.add(CountryModel("tl", "Timor-Leste", "+670", "🇹🇱"))
        arrayList!!.add(CountryModel("tg", "Togo", "+228", "🇹🇬"))
        arrayList!!.add(CountryModel("tk", "Tokelau", "+690", "🇹🇰"))
        arrayList!!.add(CountryModel("to", "Tonga", "+676", "🇹🇴"))
        arrayList!!.add(CountryModel("tt", "Trinidad & Tobago", "+1868", "🇹🇹"))
        arrayList!!.add(CountryModel("tn", "Tunisia", "+216", "🇹🇳"))
        arrayList!!.add(CountryModel("tr", "Turkey", "+90", "🇹🇷"))
        arrayList!!.add(CountryModel("tm", "Turkmenistan", "+993", "🇹🇲"))
        arrayList!!.add(CountryModel("tc", "Turks & Caicos Islands", "+1649", "🇹🇨"))
        arrayList!!.add(CountryModel("tv", "Tuvalu", "+688", "🇹🇻"))
        arrayList!!.add(CountryModel("ug", "Uganda", "+256", "🇺🇬"))
        arrayList!!.add(CountryModel("ua", "Ukraine", "+380", "🇺🇦"))
        arrayList!!.add(CountryModel("ae", "United Arab Emirates", "+971", "🇦🇪"))
        arrayList!!.add(CountryModel("gb", "United Kingdom", "+44", "🇬🇧"))
        arrayList!!.add(CountryModel("us", "United States", "+1", "🇺🇸"))
        arrayList!!.add(CountryModel("uy", "Uruguay", "+598", "🇺🇾"))
        arrayList!!.add(CountryModel("vi", "US Virgin Islands", "+1340", "🇻🇮"))
        arrayList!!.add(CountryModel("uz", "Uzbekistan", "+998", "🇺🇿"))
        arrayList!!.add(CountryModel("vu", "Vanuatu", "+678", "🇻🇺"))
        arrayList!!.add(CountryModel("ve", "Venezuela", "+58", "🇻🇪"))
        arrayList!!.add(CountryModel("vn", "Vietnam", "+84", "🇻🇳"))
        arrayList!!.add(CountryModel("wf", "Wallis And Futuna", "+681", "🇼🇫"))
        arrayList!!.add(CountryModel("ye", "Yemen", "+967", "🇾🇪"))
        arrayList!!.add(CountryModel("zm", "Zambia", "+260", "🇿🇲"))
        arrayList!!.add(CountryModel("zw", "Zimbabwe", "+263", "🇿🇼"))
        arrayList!!.add(CountryModel("ax", "Åland Islands", "+358", "🇦🇽"))
    }
    inner  class CountryModel     constructor (var iso: kotlin.String, var countryName: kotlin.String, var phoneCode: kotlin.String, var countryFlag: kotlin.String){

    }
    inner  class CountryCodeAdapter  : ArrayAdapter<CountryModel?>{
        private  var  inflater: LayoutInflater
        private  var  mContext: Context? = null
        private  var  arrayList: ArrayList<CountryModel>
        private  var  resource: kotlin.Int = 0
        constructor(context: Context, arrayList: ArrayList<CountryModel>) : super(context, 0,
            arrayList as List<CountryModel?>
        ){
            this.inflater = LayoutInflater.from(context)
            this.mContext = context
            this.arrayList = arrayList
        }
        constructor(context: Context, @LayoutRes resource: kotlin.Int, arrayList: ArrayList<CountryModel>) : super(context, resource, 0,
            arrayList as List<CountryModel?>
        ){
            this.inflater = LayoutInflater.from(context)
            this.mContext = context
            this.arrayList = arrayList
            this.resource = resource
        }
        override fun getDropDownView(position: kotlin.Int, @Nullable convertView: View, parent: ViewGroup): View {
            return createItemView(position, convertView, parent)
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createItemView(position, convertView!!, parent)
        }
        private   fun createItemView(position: kotlin.Int, convertView: View, parent: ViewGroup): View {
            val  view: View
            val model: CountryModel = arrayList.get(position)
            if (resource == 0){
                val rootLayout: LinearLayoutCompat = LinearLayoutCompat(context)
                rootLayout.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                rootLayout.setBackgroundColor(Color.WHITE)
                val ccTextView: AppCompatTextView = AppCompatTextView(context)
                ccTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                ccTextView.setBackgroundColor(Color.WHITE)
                ccTextView.setTextColor(Color.BLACK)
                ccTextView.setPadding(10, 5, 10, 5)
                ccTextView.setTextSize(15f)
                ccTextView.setText(model.countryFlag + "  " + model.countryName + "  (" + model.phoneCode + ")")
                rootLayout.addView(ccTextView)
                view = rootLayout
            } else {
                view = inflater.inflate(resource, parent, false)
                val tvCountry: AppCompatTextView = view.findViewById(R.id.text1) as AppCompatTextView
                tvCountry.setText(model.countryFlag + "  " + model.countryName + "  (" + model.phoneCode + ")")
            }
            return view
        }
    }
}