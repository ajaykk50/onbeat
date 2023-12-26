package www.onbeatapps.com.ui.dashBoardActivity

import dagger.hilt.android.lifecycle.HiltViewModel
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
//                launchApiCall {
//                        val voucher = appDbHelper.getAllStudent()
//
//                }
    }

    fun getLowBalance() = appPreferences.getLowBalance()
    fun getPage() = appPreferences.getPages()
    fun setLowBalance(type: String) {
        appPreferences.setLowBalance(type)
    }

    fun logOut() {
        appPreferences.setLogin(false)
        appPreferences.setBand("")
        appPreferences.setToken("")
        appPreferences.setPaymentID("")
        appPreferences.setTokeCheck(false)
        appPreferences.setBalance("")
        appPreferences.setCardStatus(false)
        appPreferences.setWristBalance("0.0")
        appPreferences.setBalance("0.0")
        appPreferences.setSpendingLimit("0.0")
        appPreferences.setTotalSpent("0.0")
//        appPreferences.setCurrency("£")
        appPreferences.setTouchId(false)
        appPreferences.setTouchIdSetUp(false)
    }


}