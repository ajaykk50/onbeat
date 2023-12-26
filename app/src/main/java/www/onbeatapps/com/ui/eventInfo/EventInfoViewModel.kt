package www.onbeatapps.com.ui.eventInfo

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.data.model.api.response.EventInfoRESPONSE
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EventInfoViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }

    private val _eventDt = MutableLiveData<EventInfoRESPONSE>()
    val eventDt: MutableLiveData<EventInfoRESPONSE>
        get() = _eventDt
    fun eventInfo() {
        launchApiCall {
            appPreferences.apply {
                when (val response = api.eventInfo(getEventId())){
                    is NetworkResponse.Success -> {
                        if(response.body.isSuccess()) {
                           _eventDt.value = response.body
                        }else showToast(response.body.message)


                    }is NetworkResponse.Error -> {
                    showToast(response.body?.message)
                    setExceptionUtil(response)
                }
                }
            }
        }
    }
}