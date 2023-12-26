package www.onbeatapps.com.ui.setFaceId

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentFaceIdBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment
import java.util.concurrent.Executor

@AndroidEntryPoint
class FaceIdFragment : BaseFragment<AuthViewModel>() {
    private var _binding: FragmentFaceIdBinding? = null
    private val binding get() = _binding!!
    private val pinViewModel: AuthViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun setup() {

        click()
        appAuthFacID()

    }
    sealed class Biometrics {
        object None : Biometrics()
        sealed class Available : Biometrics() {
            object Face : Available()
        }
    }

    private fun appAuthFacID() {
//        val biometricManager = BiometricManager.from(mContext)
//        val isCapable = when (biometricManager.canAuthenticate()) {
//            BiometricManager.BIOMETRIC_SUCCESS,
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> true
//            else -> false
//        }
//        val packageManager : PackageManager = mContext.packageManager

        executor = ContextCompat.getMainExecutor(mContext)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        mContext,
                        "TouchID authentication failed! Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication error
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    pinViewModel.setTouchId()
                    findNavController().navigate(R.id.navigation_welcome)
                    Toast.makeText(
                        mContext,
                        "TouchID authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication succeeded!
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        mContext,
                        "TouchID authentication failed! Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication failed
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate with TouchID")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(true)
            .build()


    }

    private fun click() {
        binding.apply {
            btyes.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
//                BiometricPrompt.PromptInfo.Builder().apply {
//                    setTitle("Biometric login for my app")
//                    setSubtitle("test")
//                    setConfirmationRequired(true)
//                    setDeviceCredentialAllowed(true)
//                }.build()

            }
            btNo.setOnClickListener {
                pinViewModel.setTouchIdSetUP()
                findNavController().navigate(R.id.navigation_welcome)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFaceIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = pinViewModel

}