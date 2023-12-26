package www.onbeatapps.com.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by PKB on 02-06-2021.
 * PKB@gmail.com
 */
abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun setLoading(status: Boolean) {
        _loading.value = status
    }

    private val _exceptionEvent = MutableLiveData<ExceptionEvent>()
    val exceptionEvent: LiveData<ExceptionEvent>
        get() = _exceptionEvent

    fun setExceptionUtil(error: www.onbeatapps.com.data.remote.coroutine.NetworkResponse.Error<www.onbeatapps.com.data.ErrorBody>) {
        setExceptionEvent(error.body, error.code, error.throwable)
    }

    fun setExceptionEvent(errorBody: www.onbeatapps.com.data.ErrorBody?, errorCode: Int?, throwable: Throwable?) {
        _exceptionEvent.value = ExceptionEvent.Throw(errorBody, errorCode, throwable)
    }

    fun settleExceptionEvent() {
        _exceptionEvent.value = ExceptionEvent.Finish
    }

    protected fun launchApiCall(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            setLoading(true)
            block()
            setLoading(false)
        }
    }
//    fun tokenRefresh(){
//        viewModelScope.
//    }

    private val _toastEvent = MutableLiveData<Event>()
    val toastEvent: LiveData<Event>
        get() = _toastEvent

    fun setToastEvent(data: String) {
        _toastEvent.value = Event.Data(data)
    }

    fun settleToastEvent() {
        _toastEvent.value = Event.Finish
    }

    private val _backEvent = MutableLiveData<Event>()
    val backEvent: LiveData<Event>
        get() = _backEvent

    fun setbackEvent() {
        _backEvent.value = Event.Data("")
    }

    fun settlebackEvent() {
        _backEvent.value = Event.Finish
    }

    private val _events = MutableLiveData<Events>()
    val events: LiveData<Events>
        get() = _events

    fun setEvent(eventType: EventTypes) {
        _events.value = Events.Data(eventType)
    }

    fun settleEvent() {
        _events.value = Events.Finish
    }

    fun showToast(message: String?) {
        if (!message.isNullOrEmpty()) setToastEvent(message)
    }
}