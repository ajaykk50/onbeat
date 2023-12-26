package www.onbeatapps.com.ui.addCard

import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.data.remote.ApiInterface
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val api: ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {

        }
    }
    fun getCurrency() = appPreferences.getCurrency()
    fun getClientSecretKey(): String {
        return appPreferences.getClientSecret()
    }
    fun cardStatus(){
        appPreferences.apply {
           setCardStatus(true)
            setBalance(getSpendingLimit())
        }
    }
    fun loading(status:Boolean){
        setLoading(status)
    }
    fun setCardSve(){
        appPreferences.setCardStatus(true)
    }
    fun paymentID(payId:String){
        appPreferences.setPaymentID(payId)
    }

    var cardId = ""
    fun saveCard() {
        launchApiCall {
            appPreferences.apply {
                when (val response = api.cardSave(getEventId(),userId())){
                    is NetworkResponse.Success -> {
//                        setCardStatus(true)
                        cardId = response.body.clientSecret
                        setSpendingLimit("00.0")
                        setBalance("00.0")
                        setCardStatus(false)

                    }is NetworkResponse.Error -> {
                    showToast(response.body?.message)
                    setExceptionUtil(response)
                }
                }
            }
        }
    }
}