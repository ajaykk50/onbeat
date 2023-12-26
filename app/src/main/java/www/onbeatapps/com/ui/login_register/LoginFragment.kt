package www.onbeatapps.com.ui.login_register

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentLoginBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity.Companion.exit
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import java.util.concurrent.Executor

const val PAGE_TYPE = "page_type"
@AndroidEntryPoint
class LoginFragment : BaseFragment<AuthViewModel>() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: AuthViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    var pageType = ""
    var balancLow = ""


    override fun setup() {
        balancLow = requireArguments().getString("balancLow","")
        pageType = requireArguments().getString(PAGE_TYPE,"login")
        binding.containFileld.visibility = View.VISIBLE
        getFireToken()
        setUpObserver()
        setUpClick()
        loginViewModel.setPage("login")
        Log.e("key_value",loginViewModel.getBalance())
//        authFace()

    }

    private fun authFace() {
        executor = ContextCompat.getMainExecutor(mContext)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
//                    Toast.makeText(mContext,
//                        "Authentication error: $errString", Toast.LENGTH_SHORT)
//                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    loginViewModel.touchLogin()
//                    Toast.makeText(mContext,
//                        "Authentication succeeded!", Toast.LENGTH_SHORT)
//                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(mContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

         promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login With Fingerprint")
            .setSubtitle("")
            .setNegativeButtonText("Use account password")
            .setConfirmationRequired(true)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    private fun setUpClick() {
       binding.apply {
           btLogin.setOnClickListener {
               if (txtPin.text.toString().length==4) {
                   loginViewModel.loginUser(txtPin.text.toString())

               }else Toast.makeText(mContext,getString(R.string.entet_pin1),Toast.LENGTH_SHORT).show()
           }
           btFogotPin.setOnClickListener {
               findNavController().navigate(R.id.navigation_login_phone, bundleOf(PAGE_TYPE to "forgot"))
           }
       }
    }

    private fun setUpObserver() {

        loginViewModel.loginstatus.observe(viewLifecycleOwner){
            if (it){
                binding.txtPin.setText("")
                loginViewModel._loginstatus.value = false
            }
        }

        if (loginViewModel.getTouchID())
            if (pageType=="splash")authFace()

        loginViewModel.loginStatus.observe(viewLifecycleOwner){
            if (it)  {
                loginViewModel.setToknCheck(true)
//              findNavController().navigate(R.id.navigation_add_wrist_band)
                baseActivity.launchActivity<DashBoardActivity> {

                    putExtra("type",1)
                }
            }
        }

    }

    private fun getFireToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {

                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            token?.let {
                loginViewModel.setFirToken(it)
            }
        })
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
           if (pageType=="splash") exit()
            else {
                findNavController().navigate(R.id.navigation_login_phone, bundleOf(PAGE_TYPE to "login"))
           }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = loginViewModel

}