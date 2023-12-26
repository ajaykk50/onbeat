package www.onbeatapps.com.ui.welcomeActivity

import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

        init {
//                launchApiCall {
//                        val voucher = appDbHelper.getAllStudent()
//
//                }
        }




}