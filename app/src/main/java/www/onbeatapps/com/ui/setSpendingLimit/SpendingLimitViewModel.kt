package www.onbeatapps.com.ui.setSpendingLimit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import www.onbeatapps.com.data.model.api.response.ListCardRESPONSE
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SpendingLimitViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }
    fun setLowBalance(type: String) {
        appPreferences.setLowBalance(type)
    }
    fun getUsedAmount(): Double {
        appPreferences.apply {
            return   if (getWristBalance().toDouble()>0)getLastAddAmount().toDouble() - getWristBalance().toDouble() else 0.00
        }
    }
    fun userAmount(): Double{
        return if (appPreferences.getWristBalance().toDouble()>0&&appPreferences.getBalance().toDouble()>0)appPreferences.getBalance().toDouble()- appPreferences.getWristBalance().toDouble()  else 0.00
    }

    fun Currency(): String {
        return appPreferences.getCurrency()
    }
    fun setTempAmount(amount:String){
        appPreferences.setTempAmount(amount)
    }
    fun getTemAmount() = appPreferences.getTempAmount()

    var paymentIntentClientSecret = ""
    var customerId = ""
    var ephemeralKeySecret = ""
    private val _addLimit = MutableLiveData<Boolean>()
    val addLimit: MutableLiveData<Boolean>
        get() = _addLimit

    fun addSpendLimit(amont: String) {
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                val paramObject = JsonObject()
                val TotalaAmount = amont.toInt() * 100
                paramObject.addProperty("amount", TotalaAmount.toString())
                when (val response = api.addSpentLimit(getEventId(), userId(), paramObject)) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            setSpendingLimit("${amont}")
                            setLastAddAmount("${amont}")
                            setClientSecret(response.body.clientSecret)

                            paymentIntentClientSecret = response.body.clientSecret
                            ephemeralKeySecret = response.body.emepleralSecret
                            customerId = response.body.customer
                            _addLimit.value = true
                        } else showToast(response.body.message)
                        setLoading(false)
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

    private val _cardDt = MutableLiveData<ListCardRESPONSE>()
    val cardDt: MutableLiveData<ListCardRESPONSE>
        get() = _cardDt


    fun getListCard(amount: String) {
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                when (val response = api.listCard(getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            val data = response.body
//                            if (!data.card.isNullOrEmpty())
                            _cardDt.value = data

                        } else {
                            showToast(response.body.message)
                        setLoading(false)
                        }
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

    private val _settleStatus = MutableLiveData<Boolean>()
    val settleStatus: MutableLiveData<Boolean>
        get() = _settleStatus

    fun settle(payId: String) {
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            val paramObject = JsonObject()
            paramObject.addProperty("payment_id", payId)
//            paramObject.addProperty("settle_amount", appPreferences.getTotalSpent().replace(".0","").toInt())
            appPreferences.apply {
                when (val response = api.settle(getEventId(), userId(), paramObject)) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            _settleStatus.value = true
                        } else {
                            showToast(response.body.message)
                            setLoading(false)
                        }
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

    fun getCardStatus(): Boolean {
        return appPreferences.getCardStatus()
    }

    fun setBalance(amount: String) {
        appPreferences.setBalance(amount)
    }
}