package www.onbeatapps.com.ui.login_register

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register_phone.*
import org.json.JSONException
import org.json.JSONObject
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentRegisterPhoneBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity.Companion.exit
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

const val SOCIAL_TOKEN = "social_token"

@AndroidEntryPoint
class LoginRegisterFragment : BaseFragment<AuthViewModel>() {
    private var _binding: FragmentRegisterPhoneBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var RC_SIGN_IN = 102
    private val SMS_PERMISSION = 200
    var type = 0


    companion object {
        var token = ""
        var pageType = ""
        private lateinit var viewModels: AuthViewModel
        lateinit var callbackManager: CallbackManager
        fun handleSignInResult(toke: String?, Fname: String, sname: String, email: String) {
            viewModels.SocName = Fname
            viewModels.SocSName = sname
            viewModels.SocEmail = email
            token = toke.toString()
            viewModels.UserCheck(toke.toString())
        }

        fun userCheck(toke: String?) {
            token = toke.toString()
            val paramObject = JsonObject()
            paramObject.addProperty("media_token", token)
            viewModels.forgotPass(paramObject)
//            viewModels.UserCheck(token!!)
        }

        fun userCheckAuthActivity(token: String?, Fname: String, sname: String, email: String) {

            val paramObject = JsonObject()
            paramObject.addProperty("media_token", token)
            viewModels.forgotPass(paramObject)
//            viewModels.UserCheck(token!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setup() {
        viewModels = authViewModel
        pageType = requireArguments().getString(PAGE_TYPE, "login")
        click()
        setUpObserver()
        setUpAdapter()
        SocialSetUpLogin()
//        requestPermissions()
        setUpViews()
         printHashKey(mContext)
    }

    private fun setUpViews() {
        binding.apply {
            if (pageType == "login") {
                txtTitle.text = resources.getString(R.string.register_or_log_in)
                btNext.text = resources.getString(R.string.login)
                txtOr.text = resources.getString(R.string.or_login_with)
                txtTeams.visibility = View.VISIBLE
                btBack.visibility = View.VISIBLE
            } else if (pageType == "forgot_pass") {
                txtTitle.text = resources.getString(R.string.register_or_log_in)
                btNext.text = resources.getString(R.string.login)
                txtOr.text = resources.getString(R.string.or_login_with)
                txtTeams.visibility = View.VISIBLE
                btBack.visibility = View.GONE
            } else {
                txtTitle.text = resources.getString(R.string.forgot_pin)
                btNext.text = getString(R.string.submit)
                txtOr.text = resources.getString(R.string.or)
                txtTeams.visibility = View.GONE
                btBack.visibility = View.VISIBLE
            }
        }
    }


    private fun setUpAdapter() {
        val myArray = resources.getStringArray(R.array.country_data)

        val countryAdapter = ArrayAdapter(
            mContext,
            R.layout.spinner_text, myArray
        )

        countryAdapter.setDropDownViewResource(R.layout.spinner_drop_text);
        binding.spnCountry.adapter = countryAdapter

    }

    private fun signInGoogle() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        activity?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun SocialSetUpLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso)

        FacebookSdk.sdkInitialize(mContext)
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
//                    authViewModel.mToken = loginResult?.accessToken!!.token
//                    LoginManager.getInstance().logOut()// Getting FB User LoginData
                    authViewModel.setLoginType(2)
                    token = loginResult?.accessToken!!.userId.toString()
//                    Log.e("LoiginCheck","FBtoken")
//                    when (pageType) {
//                        "login" -> authViewModel.UserCheck(token)
//                        "forgot_pass" -> {
//                            userCheck(token)
//                //                        authViewModel.UserCheck(token)
//                        }
//                        "forgot" -> {
//                            userCheck(token)
//                //                        authViewModel.UserCheck(token)
//                        }
//                        else -> {
//                            findNavController().navigate(
//                                R.id.navigation_pin,
//                                bundleOf(
//                                    SOCIAL_TOKEN to token,
//                                    PAGE_TYPE to "forgot",
//                                    "type" to "social"
//                                )
//                            )
//                //                        val paramObject = JsonObject()
//                //                        paramObject.addProperty("media_token", token)
//                //                        authViewModel.forgotPass(paramObject)
//                        }
//                    }
//                    findNavController().navigate(R.id.navigation_pin, bundleOf(SOCIAL_TOKEN to loginResult?.accessToken!!.userId.toString()))
                    val request = GraphRequest.newMeRequest(
                        loginResult!!.accessToken

                    )
                    { jsonObject, response ->

                        getFacebookData(jsonObject)
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "id,first_name,last_name,email,gender")
                    request.parameters = parameters
                    request.executeAsync()
                    // App code
                }

                override fun onCancel() {
                    Toast.makeText(
                        mContext,
                        "Facebook registration failed! Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(
                        mContext,
                        "Facebook registration failed! Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // App code
                }
            })

    }

    fun getFacebookData(pkb: JSONObject) {
        val bundle = Bundle()
        try {
            val id = pkb.getString("id")
//            Log.e("user_dt", com.shopping.basket.Activity.LoginActivity.accessToken_str)
//            strToken = com.shopping.basket.Activity.LoginActivity.accessToken_str
//            strPassword = com.shopping.basket.Activity.LoginActivity.accessToken_str
            //            Log.e("user_dt",object.toString());
            bundle.putString("idFacebook", id)
            if (pkb.has("first_name")) {
                authViewModel.SocName = pkb.getString("first_name")
            }
//            Log.e("user_dt", pkb.getString("first_name"))
            if (pkb.has("last_name")) {
//                bundle.putString("last_name", pkb.getString("last_name"))
                authViewModel.SocSName = "${pkb.getString("last_name")}"
                Log.e("user_dt", pkb.getString("last_name"))
//                viewmodel?.mName =  viewmodel?.mName + " " + pkb.getString("last_name")
            }

            println("pkb/......" + pkb)

//            if (object.has("gender"))
//                bundle.putString("gender", object.getString("gender"));
            if (pkb.has("email")) {
                bundle.putString("email", pkb.getString("email"))
                authViewModel.SocEmail = pkb.getString("email")
//                Log.e("user_dt", pkb.getString("email"))
//                viewmodel?.mEmail = pkb.getString("email")
            } else {
//                Log.v("firebasseRegID",firebasseRegID);
                authViewModel.SocName = pkb.getString("first_name")
                authViewModel.SocSName = pkb.getString("last_name")
//                Log.e("user_dt", name_fb)

            }
            when (pageType) {
                "login" -> authViewModel.UserCheck(token)
                "forgot_pass" -> {
                    userCheck(token)
                    //                        authViewModel.UserCheck(token)
                }
                "forgot" -> {
                    userCheck(token)
                    //                        authViewModel.UserCheck(token)
                }
                else -> {
                    findNavController().navigate(
                        R.id.navigation_pin,
                        bundleOf(
                            SOCIAL_TOKEN to token,
                            PAGE_TYPE to "forgot",
                            "type" to "social"
                        )
                    )
                    //                        val paramObject = JsonObject()
                    //                        paramObject.addProperty("media_token", token)
                    //                        authViewModel.forgotPass(paramObject)
                }
            }
            try {


//                viewmodel?.mName = pkb.getString("first_name")
//                viewmodel?.mEmail = pkb.getString("email")
//                Log.e("user_dt", viewmodel?.mName + "\n" + viewmodel?.mEmail)
//
            } catch (e: JSONException) {
                e.printStackTrace()
            }

//            viewmodel?.addSocialUser()
        } catch (e: java.lang.Exception) {
            Log.e("user_dt", "BUNDLE Exception : " + e.toString())
        }
//        return bundle
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
//        else if (requestCode == SMS_PERMISSION){
//            requestPermissions()
//        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account!!.id
            authViewModel.SocName = "${account!!.displayName}"
            authViewModel.SocSName = "${account!!.givenName}"
            authViewModel.SocEmail = account!!.email.toString()

            authViewModel.setLoginType(2)
            token = idToken!!
            if (pageType == "login")
                authViewModel.UserCheck(token)
            else if (pageType == "forgot_pass") authViewModel.UserCheck(token)
            else {
                findNavController().navigate(
                    R.id.navigation_pin,
                    bundleOf(SOCIAL_TOKEN to token, PAGE_TYPE to "forgot", "type" to "social")
                )

//                val paramObject = JsonObject()
//                paramObject.addProperty("media_token", token)
//                authViewModel.forgotPass(paramObject)
            }
//            findNavController().navigate(R.id.navigation_pin, bundleOf(SOCIAL_TOKEN to idToken!!))
//            mGoogleSignInClient.signOut()
        } catch (e: ApiException) {
            e.printStackTrace()
            Toast.makeText(mContext, "Google registration failed! Try again.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUpObserver() {

        authViewModel.pinStatus.observe(viewLifecycleOwner) {
            if (it == "forgot") {
//                findNavController().navigate(R.id.navigation_otp, bundleOf("otp" to authViewModel.otp,PAGE_TYPE to "forgot"))
                if (type == 0) {
                    findNavController().navigate(
                        R.id.navigation_otp,
                        bundleOf("otp" to authViewModel.otp, PAGE_TYPE to "forgot")
                    )
//                    findNavController().navigate(
//                        R.id.navigation_otp,
//                        bundleOf(PAGE_TYPE to "forgot")
//                    )
                } else {
                    findNavController().navigate(
                        R.id.navigation_pin,
                        bundleOf(SOCIAL_TOKEN to token, PAGE_TYPE to "forgot", "type" to "social")
                    )
                }
                authViewModel.pinStatus.value = null
            } else if (it == "login") {
                findNavController().navigate(
                    R.id.navigation_login_phone,
                    bundleOf(PAGE_TYPE to "login")
                )

                authViewModel.pinStatus.value = "back"
            }
        }
        authViewModel.userCheckStatus.observe(viewLifecycleOwner) {
            if (it == "login") {
                findNavController().navigate(R.id.navigation_login, bundleOf(PAGE_TYPE to "login"))
                authViewModel.userCheckStatus.value = "back"
            } else if (it == "register") {
                if (authViewModel.getLoginTypeStatus() == 1) {
                    authViewModel.sendOtp("${getCountryCode()}${numberCheck()}", 0)
                } else {
                    findNavController().navigate(
                        R.id.navigation_pin,
                        bundleOf(
                            SOCIAL_TOKEN to token, PAGE_TYPE to "login", "type" to "social",
                            "first_name" to viewModels.SocName, "second_name" to
                                    viewModels.SocSName, "email" to viewModels.SocEmail
                        )
                    )
                }
                authViewModel.userCheckStatus.value = "back"
            } else if (it == "popup") {
                if (pageType == "forgot_pass")
                    findNavController().navigate(
                        R.id.navigation_login,
                        bundleOf(PAGE_TYPE to "login")
                    )
                else
                    dialogAlreadyUse()
                authViewModel.userCheckStatus.value = "back"
            }
        }
        authViewModel.otpStatus.observe(viewLifecycleOwner) {
            if (it) {
                if (pageType == "login")
                    findNavController().navigate(
                        R.id.navigation_otp,
                        bundleOf("otp" to authViewModel.otp, PAGE_TYPE to "login")
                    )
//                    findNavController().navigate(
//                        R.id.navigation_otp,
//                        bundleOf(PAGE_TYPE to "login")
//                    )
                else {
                    findNavController().navigate(
                        R.id.navigation_otp,
                        bundleOf("otp" to authViewModel.otp, PAGE_TYPE to "forgot")
                    )
//                    findNavController().navigate(
//                        R.id.navigation_otp,
//                        bundleOf(PAGE_TYPE to "forgot")
//                    )
                }
                authViewModel.otpStatus.value = false
            }
        }
    }

    private fun dialogAlreadyUse() {
        AlreadyUseDialog.newInstance().also {
            it.isCancelable = false
            it.show(childFragmentManager, it.tag)

        }
    }

    private fun numberCheck(): String {
        var phone = binding.editPhone.text.toString()
        if (binding.editPhone.text.toString() != "")
            if (binding.editPhone.text.toString()[0].toString() == "0")
                phone = binding.editPhone.text.toString().drop(1)
        return phone
    }

    private fun click() {
        binding.apply {
            btNext.setOnClickListener {
                type = 0
                val phone = numberCheck()
                if (validation(phone)) {
                    authViewModel.setLoginType(1)
                    if (pageType == "login")
                        authViewModel.UserCheck("${getCountryCode()}${phone}")
                    else if (pageType == "forgot_pass") authViewModel.UserCheck("${getCountryCode()}${phone}")
                    else {
                        authViewModel.setPhoneNumber("${getCountryCode()}${phone}")
                        val paramObject = JsonObject()
                        paramObject.addProperty("phone", "${getCountryCode()}${phone}")
                        authViewModel.forgotPass(paramObject)
                    }
//                    authViewModel.sendOtp("${getCountryCode()}${phone}")
                } else editPhone.error = getString(R.string.vaild_m_number)
            }
            btGoogle.setOnClickListener {
                type = 1
                signInGoogle()
            }
            btFb.setOnClickListener {
                type = 1
                LoginManager.getInstance().logInWithReadPermissions(
                    this@LoginRegisterFragment,
                    Arrays.asList("public_profile", "email", "contact_email")
                )
            }
            txtTeams.setOnClickListener {
                findNavController().navigate(
                    R.id.nav_terms_condition,
                    bundleOf("menu_hide" to false, "page" to "termsLogin")
                )
            }
            btBack.setOnClickListener {
                if (pageType == "login")
                    findNavController().navigate(
                        R.id.navigation_scan,
                        bundleOf(PAGE_TYPE to "login")
                    )
                else findNavController().popBackStack()
//                findNavController().navigate(R.id.navigation_login , bundleOf(PAGE_TYPE to "login"))
            }
        }

    }

    fun getCountryCode(): String {
        val code = binding.spnCountry.selectedItem.toString().split(")")
        return code[0].replace("(", "").replace(")", "").trim()
    }

    private fun validation(phone: String): Boolean {
        binding.editPhone.error = null
        return if (phone == "") return false
        else true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPhoneBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            findNavController().popBackStack()
            if (pageType != "forgot_pass")
                findNavController().popBackStack()
//                AuthActivity.navControllerAuth.navigate(R.id.navigation_login_phone , bundleOf(PAGE_TYPE to "login"))
            else {
                exit()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = authViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager().getPackageInfo(
                pContext.getPackageName(),
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())

                val hashKey: String = Base64.getEncoder().encodeToString(md.digest())
                Log.i("ShaKey", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
//            Log.e("ShaKey", "printHashKey()", e)
        } catch (e: Exception) {
//            Log.e("ShaKey", "printHashKey()", e)
        }
    }

}