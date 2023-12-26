package www.onbeatapps.com.ui.contact

import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.data.local.prefs.AppPreferences
import www.onbeatapps.com.data.remote.ApiInterface
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val api: ApiInterface,
    private val appPreferences: AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }
}