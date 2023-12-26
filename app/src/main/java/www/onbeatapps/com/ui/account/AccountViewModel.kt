package www.onbeatapps.com.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import www.onbeatapps.com.data.model.api.response.UserDetailsRESPONSE
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }

    fun getLowBalance() = appPreferences.getLowBalance()
    fun setLowBalance(type: String) {
        appPreferences.setLowBalance(type)
    }

    fun setPage(s: String) {
        appPreferences.setPages(s)
    }

    fun getBansStatus(): String {
        return appPreferences.getBand()
    }

    fun getCardStatus(): Boolean {
        return appPreferences.getCardStatus()
    }

    fun getLoginType(): Int {
        return appPreferences.getLoginType()
    }

    fun getTouch(): Boolean {
        return appPreferences.getTouchId()
    }

    fun setTouch(status: Boolean) {
        appPreferences.setTouchId(status)
    }

    fun getTouchSetUP(): Boolean {
        return appPreferences.getTouchIdSetUp()
    }

    fun setUpFacId() {
        appPreferences.setTouchIdSetUp(true)
    }

    private val _userDt = MutableLiveData<UserDetailsRESPONSE>()
    val userDt: MutableLiveData<UserDetailsRESPONSE>
        get() = _userDt

    fun getTicket() = appPreferences.getTicket()
    fun getBand() = appPreferences.getBand()

    var gTagList: List<UserDetailsRESPONSE.Gtag> = listOf()
    var cardList: List<UserDetailsRESPONSE.Cards> = listOf()
    var ticketType = ""
    var regType = ""
    fun getUserDt() {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                when (val response = api.getUserDt(getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        val data = response.body
                        regType = data.registrationType ?: ""
                        setBalance(data.money)
                        setWristBalance(data.globalRefundableMoney.toString())
                        setPreauthuMoney(data.money)
                        gTagList = data.gtags
                        cardList = data.card
                        if (data.gtags != null && data.gtags.isNotEmpty()) {
                            for (i in data.gtags.indices) {
                                if (!data.gtags[i].banned) {
                                    setBand(data.gtags[i].tagUid)
                                    break
                                } else setBand("")
                            }
                        } else setBand("")
                        if (data.ticket != null && data.ticket.isNotEmpty()) {
                            for (i in data.ticket.indices) {
                                if (!data.ticket[i].banned) {
                                    setTicket(data.ticket[i].code)
                                    ticketType = data.ticket[i].type_name
                                    break
                                } else setTicket("")
                            }
                        } else setTicket("")
                        if (data.card.isNotEmpty()) setPaymentID(data.card[0].paymentMethodId)
                        if (response.body.firstName != null)
                            setFirstName(response.body.firstName.toString())
                        else setFirstName("")
                        _userDt.value = data
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

    private val _banStatus = MutableLiveData<Boolean>()
    val banStatus: MutableLiveData<Boolean>
        get() = _banStatus

    fun bandTage() {
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                when (val response = api.banBand(getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            setSpendingLimit("00.0")
                            setBalance("00.0")
                            setBand("")
                            _banStatus.value = true
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

    fun getPayment() = appPreferences.getPaymentID()

    private val _unlinkStatus = MutableLiveData<Boolean>()
    val unlinkStatus: MutableLiveData<Boolean>
        get() = _unlinkStatus

    fun unlinkTicket() {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                when (val response = api.unlinkTicket(getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            _unlinkStatus.value = true
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


    fun setPin(pin: String) {
        appPreferences.setPinNumber(pin)
    }

    private val _editStatus = MutableLiveData<Boolean>()
    val editStatus: MutableLiveData<Boolean>
        get() = _editStatus

    private val _emailStatus = MutableLiveData<Boolean>()
    val emailStatus: MutableLiveData<Boolean>
        get() = _emailStatus

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: MutableLiveData<Boolean>
        get() = _updateStatus
    var editType = 0

    fun editData(data: JsonObject, type: Int) {
        editType = type
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                when (val response = api.editProfile(getEventId(), userId(), data)) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
//                            setBand("")
                            showToast(response.body.message)
                            _editStatus.value = true
                            _updateStatus.value = true

                        } else {
                            if (data.toString().contains("email"))
                                _emailStatus.value = true
                            else showToast(response.body.message)
                        }
                        setLoading(false)
                    }
                    is NetworkResponse.Error -> {
                        setLoading(false)
                        if (data.toString().contains("email"))
                            _emailStatus.value = true
                        else {
                            showToast(response.body?.message)
                            setExceptionUtil(response)
                        }

                    }
                }
            }
        }
    }

    fun getFirstNmae(): String {
        return appPreferences.getFirstName()
    }

    private val _deleteCall = MutableLiveData<Boolean>()
    val deleteCall: MutableLiveData<Boolean>
        get() = _deleteCall

    fun settle(type: Int) {
        callType = type
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            appPreferences.apply {
                val paramObject = JsonObject()
                paramObject.addProperty("payment_id", appPreferences.getPaymentID())
                when (val response = api.settle(getEventId(), userId(), paramObject)) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            setSpendingLimit("00.0")
                            setBalance("00.0")
                            _deleteCall.value = true
//                            getUserDt()
                        } else {
                            if (response.body?.message == "Zero transaction found") {
                                _deleteCall.value = true
                            }
//                            showToast(response.body.message)
                        }
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


    private val _settilCall = MutableLiveData<Boolean>()
    val settilCall: MutableLiveData<Boolean>
        get() = _settilCall
    private val _userDtCall = MutableLiveData<Boolean>()
    val userDtCall: MutableLiveData<Boolean>
        get() = _userDtCall
    var callType = 0
    fun cardDelet(type: Int) {
        callType = type
//        launchApiCall {
        viewModelScope.launch {
            setLoading(true)
            val paramObject = JsonObject()
            paramObject.addProperty("payment_id", appPreferences.getPaymentID())
            appPreferences.apply {
                when (val response = api.cardDelete(getPaymentID(), getEventId(), userId())) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            setSpendingLimit("00.0")
                            setBalance("00.0")
                            setCardStatus(false)
                            setPaymentID("")
                            showToast(response.body.message)
                            _userDtCall.value = true
//                            getUserDt()
                        } else {
                            if (response.body.message == "Settle amount and try again!!") {
                                _settilCall.value = true
//                                settle()
                            } else
                                showToast(response.body.message)
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

    fun cardStatus(status: Boolean) {
        appPreferences.setCardStatus(status)
    }
}