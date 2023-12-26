package www.onbeatapps.com.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.SplashActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.eventClosed.EventClosedActivity
import java.lang.Exception


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: SplashActivityBinding
    private val authViewModel: AuthViewModel by viewModels()
    var type = 0

    companion object {
        var types = ""
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun getBinding(): View {
        binding = SplashActivityBinding.inflate(layoutInflater)
        this.window.statusBarColor = getColor(R.color.white)
        return binding.root
    }

    override fun getViewModel() = authViewModel
    override fun setup(savedInstanceState: Bundle?) {

        type = intent.getIntExtra("type", 0)
        if (intent.hasExtra("pkb")) {
            if (intent.extras!!.getString("pkb") == "low balance")
                authViewModel.setLowBance("low balance")
        }
        authViewModel.setToknCheck(false)
        authViewModel.setPage("splash")
        if (type == 1) {
            launchActivity<AuthActivity> {
                putExtra("login", 1)
            }
        } else {
            authViewModel.getEvent()
            setUpObserver()
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getStringExtra("pkb") == "low balance") {
            authViewModel.setLowBance("low balance")
        }
    }

    override fun onResume() {
        super.onResume()


//        if (type == 1) {
//            launchActivity<AuthActivity> {
//                putExtra("login", 1)
//            }
//        } else {
//
//            authViewModel.getEvent()
//            setUpObserver()
//        }

    }


    private fun setUpObserver() {

        authViewModel.eventCloseStatus.observe(this) {
            if (it) {
                launchActivity<EventClosedActivity> {
                }
            }
        }

        authViewModel.eventIDStatus.observe(this) {
            if (it) {
                if (versionCheck())
                    showUpdationDialog()
                else
                    goHome()
            }
        }

    }

    private fun versionCheck(): Boolean {
        try {
            val versionName: Double? =
                getPackageManager().getPackageInfo(getPackageName(), 0).versionName?.toDouble()
            if (!(authViewModel.appVersion.isEmpty())) {
                val appVersion: Double? = authViewModel.appVersion.toDouble()
                if (appVersion!! > versionName!!)
                    return true
                else
                    return false
            } else
                return false
        } catch (e: Exception) {
            return false
        }
    }

    private fun showUpdationDialog() {
        VersionUpdateDialog.newInstance().also {
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }

    fun goHome() {
        Handler().postDelayed({
            if (authViewModel.getLogin()) {
                launchActivity<AuthActivity> {
                    putExtra("login", 1)
                }
            } else {
                launchActivity<AuthActivity> {
                    putExtra("login", 2)
                }
            }
//            finish()
        }, 3000)
    }


}