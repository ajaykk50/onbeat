package www.onbeatapps.com.ui.addWristBand

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddwristBandViewModel @Inject constructor(
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

    fun addCode(code: String){
        _qrcode.value = code
    }

    private val _bandStatus = MutableLiveData<Boolean>()
    val bandStatus: MutableLiveData<Boolean>
        get() = _bandStatus
    fun addWristBand(bandId: String) {
        launchApiCall {
            appPreferences.apply {
                val paramObject = JsonObject()
                    paramObject.addProperty("tag_uid", bandId)
                when (val response = api.addBant(getEventId(),userId(),paramObject)){
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {

                            setBand(bandId)
                            showToast("Wristband added.")
                            _bandStatus.value = true
                        }
                        else showToast(response.body.message)

                    }is NetworkResponse.Error -> {
                    showToast(response.body?.message)
                    setExceptionUtil(response)
                }
                }
            }
        }
//        appPreferences.setBand("123")
    }
}