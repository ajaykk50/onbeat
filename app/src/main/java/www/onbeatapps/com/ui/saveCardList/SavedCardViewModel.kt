package www.onbeatapps.com.ui.saveCardList

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.data.local.prefs.AppPreferences
import www.onbeatapps.com.data.model.api.response.ListCardRESPONSE
import www.onbeatapps.com.data.remote.ApiInterface
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SavedCardViewModel @Inject constructor(
    private val api: ApiInterface,
    private val appPreferences: AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }

    private val _cardDt = MutableLiveData<ListCardRESPONSE>()
    val cardDt: MutableLiveData<ListCardRESPONSE>
        get() = _cardDt
    fun getUserDt() {
        launchApiCall {
            appPreferences.apply {
                when (val response = api.listCard(getEventId(),userId())){
                    is NetworkResponse.Success -> {
                        if (response.body.isSuccess()) {
                            val data = response.body
                            _cardDt.value = data
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