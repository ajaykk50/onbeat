package www.onbeatapps.com.ui.setPin

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.biometric.BiometricManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.poovam.pinedittextfield.PinField
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentSetPinBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.ui.login_register.SOCIAL_TOKEN

@AndroidEntryPoint
class PinSetFragment : BaseFragment<AuthViewModel>() {
    private var _binding: FragmentSetPinBinding? = null
    private val binding get() = _binding!!
    private val PinSetViewModel: AuthViewModel by viewModels()
    var passStatus = false
    var firstPin = ""
    var socialToken = ""
    var pageType = ""
    var loginType = ""

    override fun setup() {
        binding.containFileld.visibility = View.VISIBLE
        socialToken = arguments?.getString(SOCIAL_TOKEN, "").toString()
        pageType = requireArguments().getString(PAGE_TYPE, "login")
        loginType = requireArguments().getString("type", "otp")
        PinSetViewModel.socialtoken = socialToken
        if (pageType == "login" && loginType == "social"){
            PinSetViewModel.SocName = requireArguments().getString("first_name")!!
            PinSetViewModel.SocSName = requireArguments().getString("second_name")!!
            PinSetViewModel.SocEmail = requireArguments().getString("email")!!
        }
        setUpObserver()
        setUpClick()
    }

    private fun setUpClick() {
        binding.apply {
            btConfirm.setOnClickListener {
                if (!passStatus) {

                    firstPin = txtPin.text.toString()
                    if (firstPin.length == 4) {
                        txtPin.setText("")
                        btConfirm.setText(resources.getString(R.string.confirm))
                        passStatus = true
                        txtTitle.text = "Confirm passcode"
                        txtLabel.text = "Enter passcode again"
                    } else Toast.makeText(
                        mContext,
                        getString(R.string.entet_pin1),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (firstPin == txtPin.text.toString()) {
                        if (pageType == "login")
                            PinSetViewModel.getUser(firstPin)
                        else {
                            val paramObject = JsonObject()
                            if (loginType == "otp") {
                                paramObject.addProperty("otp", socialToken)
                                paramObject.addProperty("phone", PinSetViewModel.getPhone())
                            }
                            else
                                paramObject.addProperty("media_token", socialToken)
                            paramObject.addProperty("pin", firstPin)
                            PinSetViewModel.forgotPass(paramObject)
                        }
                    } else {
                        txtPin.setText("")
                        Toast.makeText(
                            mContext,
                            getString(R.string.passcode_not_match),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    private fun pinsuccess() {
        PinSetViewModel.setToknCheck(true)
        binding.containFileld.visibility = View.GONE
        binding.containSuccess.visibility = View.VISIBLE
        chancekfaceID()

    }

    fun chancekfaceID() {
        val biometricManager = BiometricManager.from(mContext)
        when (biometricManager!!.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Handler().postDelayed({
                    findNavController().navigate(R.id.navigation_facId)
                }, 800)

//                Toast.makeText(
//                    mContext,
//                    "App can authenticate using biometrics",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            // App can authenticate using biometrics
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                findNavController().navigate(R.id.navigation_welcome)
//                Toast.makeText(
//                    mContext,
//                    "No biometric features available on this device",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            // No biometric features available on this device
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                findNavController().navigate(R.id.navigation_welcome)
//                Toast.makeText(
//                    mContext,
//                    "No biometric features available on this device",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            // Biometric features are currently unavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                findNavController().navigate(R.id.navigation_welcome)
//                Toast.makeText(
//                    mContext,
//                    "The user hasn't associated any biometric credentials with their account",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            // The user hasn't associated any biometric credentials with their account
        }
    }

    private fun setUpObserver() {
        PinSetViewModel.pinStatus.observe(viewLifecycleOwner) {
//            if (it) {
                findNavController().navigate(R.id.navigation_login,  bundleOf(PAGE_TYPE to "forgot_pass"))
//            } else {
//            findNavController().navigate(R.id.navigation_scan, bundleOf(PAGE_TYPE to "login"))
//                findNavController().navigate(
//                    R.id.navigation_login_phone,
//                    bundleOf(PAGE_TYPE to "forgot_pass")
//                )
//            }
        }
        val listener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {

                return@onTextComplete true
            }
        }

        binding.apply {
            txtPin.onTextCompleteListener = listener
        }
        PinSetViewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it) pinsuccess()
        }
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetPinBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (passStatus) {
                firstPin = ""
                passStatus = false
                binding.btConfirm.text = resources.getString(R.string.next)
                binding.txtTitle.text = "Set a passcode"
                binding.txtLabel.text = getString(R.string.set_pass_easy)
            } else AuthActivity.navControllerAuth.popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = PinSetViewModel

}