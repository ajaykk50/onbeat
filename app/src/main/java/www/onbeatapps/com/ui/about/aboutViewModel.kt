package www.onbeatapps.com.ui.about

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import www.onbeatapps.com.data.model.api.response.ListCardRESPONSE
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class aboutViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }


    }

//    fun rxJava(){
//        viewModelScope.launch {
//            api.getTop(1)
//                ?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
//                ?.subscribeWith(object : Observer<List<ListCardRESPONSE.Card?>?>() {
//                    fun onSuccess(notes: List<ListCardRESPONSE.Card?>?) {
//                        // Received all notes
//                    }
//
//                    fun onError(e: Throwable?) {
//                        // Network error
//                    }
//                })
//        }
//
//    }

}