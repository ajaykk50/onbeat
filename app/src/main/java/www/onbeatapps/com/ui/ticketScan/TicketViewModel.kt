package www.onbeatapps.com.ui.ticketScan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }

    private val _qrcode = MutableLiveData<String>()
    val qrcode: LiveData<String>
        get() = _qrcode

    fun addCode(code: String) {
        _qrcode.value = code
    }

    private val _scanStatus = MutableLiveData<String>()
    val scanStatus: MutableLiveData<String>
        get() = _scanStatus

    fun scanTicket(ticket: String) {
        launchApiCall {
            appPreferences.apply {
                appPreferences.setTicket(ticket)
                val paramObject = JsonObject()
                paramObject.addProperty("code", ticket)
                when (val response = api.tickectScan(getEventId(), paramObject)) {
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {

//                            if (response.body.message == "Create a customer in both v2 and v3") {
                                _scanStatus.value = "Register"
//                            }
                        } else {
                            if (response.body?.message == "Already used ticket"){
                                setUuId(response.body.uuid)
                                userId(response.body.id)
                                _scanStatus.value = "login"
                            }
                            else showToast(response.body.message)
                        }

                    }
                    is NetworkResponse.Error -> {
                        showToast(response.body?.message)

                        setExceptionUtil(response)
                    }
                }
            }
        }
//        appPreferences.setBand("123")
    }
}