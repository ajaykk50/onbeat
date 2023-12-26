package www.onbeatapps.com.ui.homeFragment

import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import www.onbeatapps.com.data.local.prefs.AppPreferences
import www.onbeatapps.com.data.model.api.response.TransationListRESPONSE
import www.onbeatapps.com.data.remote.ApiInterface
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiInterface,
    private val appPreferences: AppPreferences
) : BaseViewModel() {

    init {

    }
    var name=""
    var phone = ""
    var email = ""
    fun getDt(){
        name = appPreferences.getFirstName()
        phone = appPreferences.getPhone()
    }
    fun getLowBalance() = appPreferences.getLowBalance()
    fun setLowBalance(type: String) {
        appPreferences.setLowBalance(type)
    }
    fun getBand(): String {
        return appPreferences.getBand()
    }
    fun getEventName(): String {
        return appPreferences.getEventName()
    }
    fun getSpend(): String {
        return appPreferences.getSpendingLimit()
    }
    fun getCardStatus(): Boolean {
        return appPreferences.getCardStatus()
    }
    fun getSpentLimit(): String {
        return appPreferences.getWristBalance()
    }
    private val _trList = MutableLiveData<List<TransationListRESPONSE.Transation>>()
    val trList: MutableLiveData<List<TransationListRESPONSE.Transation>>
        get() = _trList
    var balance = "00.0"
    var total_spent = "00.0"
    var nodat = MutableLiveData<Boolean>()
    fun getTransationList() {
//        launchApiCall {
            viewModelScope.launch {
            appPreferences.apply {
                setLoading(true)
                when (val response = api.transationList(getEventId(),userId())){
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            val data = response.body

                            updateCurrency(data.currency)
                            balance = data.balance//roundBalance(data.balance.toFloat())//response.body.balance.toString()
                            total_spent =data.total_spent// roundBalance(data.total_spent.toFloat())//response.body.total_spent.toString()
                            setTotalSpent(data.total_spent)//roundBalance(data.total_spent.toFloat()))

                            _wristBalance.value =data.balance// roundBalance(data.balance.toFloat())
                            _trList.value = data.transation
                        }else {
                            setTotalSpent("00.0")
                            showToast(response.body.message)
                            nodat.value  = true
                        }
                        setLoading(false)
                    }is NetworkResponse.Error -> {
                    nodat.value  = true
                    showToast(response.body?.message)
                    setExceptionUtil(response)
                    setLoading(false)
                }
                }
            }
        }
    }

    private val _pdfUrl = MutableLiveData<String>()
    val pdfUrl: MutableLiveData<String>
        get() = _pdfUrl

    fun downloadTransationList() {
//        launchApiCall {
            viewModelScope.launch {
            appPreferences.apply {
                setLoading(true)
                when (val response = api.transationListDpwnload(getEventId(),userId())){
                    is NetworkResponse.Success -> {
                            val data = response.body
                        _pdfUrl.value = data.url
                        setLoading(false)
                    }is NetworkResponse.Error -> {

                    showToast(response.body?.message)
                    setExceptionUtil(response)
                    setLoading(false)
                }
                }
            }
        }
    }



    fun updateCurrency(country: String) {
        val currencyCode = Currency.getInstance(country)
        val symbol: String = currencyCode.symbol
        currency = symbol
        appPreferences.setCurrency(currency)
    }
    fun Currency(): String {
        return appPreferences.getCurrency()
    }
    fun roundBalance(num:Float): String {
        return num.toString()
//        val num = 1.34567
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
//         return df.format(num.toDouble())
    }

    fun getCode() = appPreferences.getCurrencyCode()
    fun getPayment() = appPreferences.getPaymentID()
    var preAuthAmount = ""
    private val _wristBalance = MutableLiveData<String>()
    val wristBalance: MutableLiveData<String>
        get() = _wristBalance
    var currency = ""
    fun getUserDt() {
//        launchApiCall {
            viewModelScope.launch {

                setLoading(true)
            appPreferences.apply {
//                Log.e("userDt",getEventId().toString()+"\n"+userId()+"\n"+getBand()+"\n"+getToken())
                when (val response = api.getUserDt(getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        val data = response.body
                        setBalance(data.money)
                        setWristBalance(data.globalRefundableMoney.toString())
                        preAuthAmount = data.money
                        name = "${data.firstName} ${data.lastName}"
                        phone = if (data.phone!=null)data.phone.toString() else ""
                        email = if (data.email!=null)data.email.toString() else ""
                        if (data.gtags != null && data.gtags.isNotEmpty()) {
                            for (i in  data.gtags.indices) {
                                if (!data.gtags[i].banned) {
                                    setBand(data.gtags[i].tagUid)
                                    break
                                }else setBand("")
                            }
                        }else setBand("")
                        if (data.card.isNotEmpty()) {
                            setCardStatus(true)
                            setPaymentID(data.card[0].paymentMethodId)
                        }
                        if (response.body.firstName != null)
                            setFirstName(response.body.firstName.toString())
                        else setFirstName("")
                        getTransationList()
//                        setLoading(false)
                    }
                    is NetworkResponse.Error -> {
                        showToast(response.body?.message)
                        setExceptionUtil(response)
                        setLoading(false)
                    }
                }
            }
        }
    }

    fun setPage(s: String) {
        appPreferences.setPages(s)
    }
}