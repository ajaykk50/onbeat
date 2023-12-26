package www.onbeatapps.com.ui.noInternetActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.ActivityNoInternetBinding
import www.onbeatapps.com.databinding.AuthActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.callbackManager


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class noInternetActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: ActivityNoInternetBinding
    private val activityViewModel: AuthViewModel by viewModels()
    var page = 0

    companion object{
         var context:noInternetActivity? = null
        fun exit(){
            if (context!=null)
                context!!.finish()
        }
    }
    @SuppressLint("NewApi")
    override fun getBinding(): View {
        binding = ActivityNoInternetBinding.inflate(layoutInflater)
        this.window.statusBarColor = getColor(R.color.white);
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        return binding.root
    }

    override fun getViewModel() = activityViewModel
    override fun setup(savedInstanceState: Bundle?) {
        context = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}