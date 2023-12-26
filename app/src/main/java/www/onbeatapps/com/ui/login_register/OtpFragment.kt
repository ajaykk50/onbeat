package www.onbeatapps.com.ui.login_register

//import www.onbeatapps.com.ui.authActivity.AuthActivity.Companion.buttonClicked

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import com.poovam.pinedittextfield.PinField
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentOtpBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment


@AndroidEntryPoint
class OtpFragment : BaseFragment<AuthViewModel>() {

    private val otpViewModel: AuthViewModel by viewModels()

    var otp = 0
    var pageType = ""
    private lateinit var task: Task<Void>

    @SuppressLint("NewApi")
    override fun setup() {
        val task = SmsRetriever.getClient(context).startSmsUserConsent(null /* or null */)
        pageType = requireArguments().getString(PAGE_TYPE, "login")
        otp = requireArguments().getInt("otp")
        setUpObserver()
        setUpClick()
        setUpView()
    }

    companion object {
        private var _binding: FragmentOtpBinding? = null
        private val binding get() = _binding!!
        fun getOtpFromMessage(message: String) {
            val otp = message.split(" ")
            binding.txtOtp.setText(otp[2].toString())
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SMS_CONSENT_REQUEST == requestCode) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                // Extract one-time code from the message and complete verification
                // `message` contains the entire text of the SMS message, so you will need
                // to parse the string.
//                val oneTimeCode = parseOneTimeCode(message)
                getOtpFromMessage(message.toString())
            }
        }

    }

    private val SMS_CONSENT_REQUEST = 200  // Set to an unused request code
    private val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent =
                            extras?.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            activity?.startActivityForResult(consentIntent, SMS_CONSENT_REQUEST)
                        } catch (e: ActivityNotFoundException) {
//                            Log.e("otpError",e.toString())
                            // Handle the exception ...
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Time out occurred, handle the error.
                    }
                }
            }
        }
    }

    private fun setUpView() {
        binding.apply {
            if (pageType == "login") {
                toopBar.text = resources.getString(R.string.register_or_log_in)
            } else {
                toopBar.text = resources.getString(R.string.forgot_pin)
            }
        }
    }

    private fun setUpClick() {
        binding.apply {
            btConfirm.setOnClickListener {
                if (txtOtp.text.toString().length == 4) {
                    if (pageType == "login") {
                        otpViewModel.otpCheck(txtOtp.text.toString())

                    } else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("otp", txtOtp.text.toString())
                        paramObject.addProperty("phone", otpViewModel.getPhone())
                        otpViewModel.forgotPass(paramObject)
                    }
                } else Toast.makeText(
                    mContext,
                    "${resources.getString(R.string.codeErroPin)} ${otpViewModel.getphone()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            btSendAganin.setOnClickListener {
                otpViewModel.otpSendAgain(1)
            }

            btBack.setOnClickListener {
                  findNavController().popBackStack()
            }
        }
    }

    private fun setUpObserver() {
        val listener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
//                AuthActivity.navControllerAuth.navigate(R.id.navigation_pin)
                return@onTextComplete true
            }
        }

        binding.apply {
            txtOtp.onTextCompleteListener = listener
            txtOtp.setText(otp.toString()) //for testing purpose
        }
        otpViewModel.otpStatus1.observe(viewLifecycleOwner) {
            if (it) {
                if (pageType == "login")
                    findNavController().navigate(
                        R.id.navigation_pin,
                        bundleOf(PAGE_TYPE to "login")
                    )
                else findNavController().navigate(
                    R.id.navigation_pin,
                    bundleOf(
                        PAGE_TYPE to "forgot",
                        SOCIAL_TOKEN to binding.txtOtp.text.toString(),
                        "type" to "otp"
                    )
                )
            }
        }
//        otpViewModel.otpStatus.observe(viewLifecycleOwner) {
//            if (it) {
////                binding.txtOtp.setText(otpViewModel.otp.toString())
//            }
//        }
        otpViewModel.pinStatus.observe(viewLifecycleOwner) {
            if (it == "forgot") {
                findNavController().navigate(
                    R.id.navigation_pin,
                    bundleOf(
                        PAGE_TYPE to "forgot",
                        SOCIAL_TOKEN to binding.txtOtp.text.toString(),
                        "type" to "otp"
                    )
                )
                otpViewModel.pinStatus.value = "back"
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            if (pageType == "login") {
//                findNavController().navigate(
//                    R.id.navigation_login_phone,
//                    bundleOf(PAGE_TYPE to "login")
//                )
//                findNavController().popBackStack()
//            }
//            else findNavController().navigate(
//                R.id.navigation_login_phone,
//                bundleOf(PAGE_TYPE to "forgot")
//            )
            AuthActivity.navControllerAuth.popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NewApi")
    override fun onStart() {
//        LocalBroadcastManager.getInstance(mContext)
//            .registerReceiver(receiver, IntentFilter("otpCode"))
//        SmsRetriever.SEND_PERMISSION,
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

        mContext.registerReceiver(smsVerificationReceiver, intentFilter)
        super.onStart()
    }

    override fun onResume() {
//        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, IntentFilter("otpCode"))
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
//        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(smsVerificationReceiver)
//        mContext.unregisterReceiver(smsVerificationReceiver)
    }

    override fun onPause() {
        super.onPause()
//        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(smsVerificationReceiver)
        try {
            mContext.unregisterReceiver(smsVerificationReceiver)
        } catch (e: Exception) {
        }
    }


    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action.equals("otpCode", ignoreCase = true)) {
                val message = intent.getStringExtra("message")
                getOtpFromMessage(message.toString())

            }
        }
    }


    override fun getViewModel() = otpViewModel

}