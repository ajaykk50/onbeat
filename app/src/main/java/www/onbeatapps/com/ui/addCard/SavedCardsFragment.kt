package www.onbeatapps.com.ui.addCard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.navigation.fragment.findNavController
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.SplashActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment


@AndroidEntryPoint
class SavedCardsFragment : BaseFragment<AuthViewModel>() {
    private var _binding: SplashActivityBinding? = null
    private val binding get() = _binding!!
    private val addCardViewModel : AuthViewModel by viewModels()
    var hideCount = false
    private lateinit var stripe: Stripe
    private lateinit var paymentSheet: PaymentSheet
    private var lifecycle: LifecycleRegistry? = null

     var customerId = "cus_JslGdAot2gz8Wy"
     var ephemeralKeySecret = "ek_test_YWNjdF8xSkRTakFIUzRXNHNSa0t1LERuZkdxSWR1MlhjMDhIakFwdU54YVZBUzJOOU1iRmY_00jxGw3Tjr"
     var paymentIntentClientSecret = "pi_1JFBEGHS4W4sRkKuaiAl9L7P_secret_bcUmbF59I8DGcUL1fgK5AHwQK"

    override fun setup() {
        customerId = requireArguments().getString("customer")!!
        ephemeralKeySecret = requireArguments().getString("ephKey")!!
        paymentIntentClientSecret = requireArguments().getString("clint")!!
        stripe = Stripe(mContext, BuildConfig.STRIP_KEY)
        lifecycle =  LifecycleRegistry(baseActivity)
        lifecycle!!.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        payStrip()

    }

    fun payStrip(){

        paymentSheet = PaymentSheet(baseActivity) { result ->
            onPaymentSheetResult(result)
        }
        presentPaymentSheet()
    }


    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Example, Inc.",
                customer = PaymentSheet.CustomerConfiguration(
                    id = customerId,
                    ephemeralKeySecret = ephemeralKeySecret
                )
            )
        )
    }
    private fun onPaymentSheetResult(
        paymentSheetResult: PaymentSheetResult
    ) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                findNavController().popBackStack()
                Toast.makeText(
                    mContext,
                    "Payment Canceled",
                    Toast.LENGTH_LONG
                ).show()
            }
            is PaymentSheetResult.Failed -> {
                findNavController().popBackStack()
                Toast.makeText(
                    mContext,
                    "Payment Failed. See logcat for details.",
                    Toast.LENGTH_LONG
                ).show()
            }
            is PaymentSheetResult.Completed -> {
                findNavController().navigate(R.id.navigation_enjoy)
                Toast.makeText(
                    mContext,
                    "Payment Complete",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = SplashActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    override fun getViewModel() = addCardViewModel


}