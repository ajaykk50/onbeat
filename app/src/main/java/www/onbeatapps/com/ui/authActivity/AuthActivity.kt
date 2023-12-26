package www.onbeatapps.com.ui.authActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.AuthActivityBinding
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.callbackManager
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.handleSignInResult
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.pageType
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.userCheck
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment.Companion.userCheckAuthActivity
import www.onbeatapps.com.ui.login_register.OtpFragment.Companion.getOtpFromMessage
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.ui.login_register.SOCIAL_TOKEN


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class AuthActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: AuthActivityBinding
    private val activityViewModel: AuthViewModel by viewModels()
    var page = 0
    var RC_SIGN_IN = 102
    var SMS_CONSENT_REQUEST = 200
    var lowBalance = ""


    companion object{
        lateinit var navControllerAuth: NavController
         var context:Activity? = null
        fun exit(){
            finishAffinity(context!!)
        }
    }
    @SuppressLint("NewApi")
    override fun getBinding(): View {
        binding = AuthActivityBinding.inflate(layoutInflater)
        this.window.statusBarColor = getColor(R.color.white);
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        return binding.root
    }

    override fun getViewModel() = activityViewModel
    override fun setup(savedInstanceState: Bundle?) {
        getFireToken()

        context = this
        page = intent.getIntExtra("login", 2)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_auth_fragment) as NavHostFragment
        navControllerAuth = navHostFragment.navController

        if (page!=2) navControllerAuth.navigate(R.id.navigation_login, bundleOf(PAGE_TYPE to "splash"))
        else {
            navControllerAuth.navigate(R.id.navigation_scan , bundleOf(PAGE_TYPE to "login"))
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        finishAffinity()
//    }


    private fun getFireToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {

                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            token?.let {
                activityViewModel.setFirToken(it)
            }
        })
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SMS_CONSENT_REQUEST==requestCode) {
            if (resultCode == RESULT_OK) {
                val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                // Extract one-time code from the message and complete verification
                // `message` contains the entire text of the SMS message, so you will need
                // to parse the string.
//                val oneTimeCode = parseOneTimeCode(message)
                getOtpFromMessage(message.toString())
            }
        }
        else if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResul(task)
        }
        else if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun handleSignInResul(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account!!.id

            activityViewModel.setLoginType(2)
            val token = idToken
            var FName  = account!!.displayName!!.split(" ")
            if (pageType =="login")
                handleSignInResult(token,FName[0],FName[1],account!!.email.toString())
//                activityViewModel.UserCheck(token)
            else if (pageType=="forgot") userCheck(token)
            else {
                navControllerAuth.navigate(R.id.navigation_pin, bundleOf(SOCIAL_TOKEN to token,PAGE_TYPE to "forgot","type" to "social"))
//                val paramObject = JsonObject()
//                paramObject.addProperty("media_token", token)
//                authViewModel.forgotPass(paramObject)
            }
//            findNavController().navigate(R.id.navigation_pin, bundleOf(SOCIAL_TOKEN to idToken!!))
//            mGoogleSignInClient.signOut()
        } catch (e: ApiException) {
           // e.printStackTrace()
            Toast.makeText(this,"Google registration failed! Try again.", Toast.LENGTH_SHORT).show()
        }
    }


}