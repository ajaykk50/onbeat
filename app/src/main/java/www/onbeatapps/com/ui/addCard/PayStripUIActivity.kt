package www.onbeatapps.com.ui.addCard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.databinding.ActivityStripUiBinding
import www.onbeatapps.com.databinding.SplashActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.setSpendingLimit.USER_EXTRA

@AndroidEntryPoint
class PayStripUIActivity : BaseActivity<AuthViewModel>() {
    private lateinit var binding: ActivityStripUiBinding
    private val addCardViewModel : AuthViewModel by viewModels()
    var hideCount = false
    private lateinit var stripe: Stripe
    private lateinit var paymentSheet: PaymentSheet


     var customerId = "cus_JseqIYVCBYVBu6"
     var ephemeralKeySecret = "ek_test_YWNjdF8xSkRTakFIUzRXNHNSa0t1LFhlYmp1ekpEd3NqMEtQZ2t6ZU1WWUhCN1RnVncxVlk_00EboqG1TJ"
     var paymentIntentClientSecret = "pi_1JEujRHS4W4sRkKuGAaqvrYw_secret_yJW4HvI8jwfqyPXlFdDwxFVax"

    override fun setup(savedInstanceState: Bundle?) {
//        hideCount = requireArguments().getBoolean(HIDE_COUNT,false)
        customerId = intent.getStringExtra("customer" )!!
        ephemeralKeySecret = intent.getStringExtra("ephKey")!!
        paymentIntentClientSecret = intent.getStringExtra("clint")!!
        stripe = Stripe(this, BuildConfig.STRIP_KEY)
        paymentSheet = PaymentSheet(this) { result ->
            onPaymentSheetResult(result)
        }
        presentPaymentSheet()
//        setUpClick()
//        setUpObserver()
    }


    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
//            "pi_1JG2wkHS4W4sRkKusu5elPjK_secret_dlqAUtaSALi9JgKGTRbJ5Hjm7",
            PaymentSheet.Configuration(
                merchantDisplayName = "OnBeat",
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
                Toast.makeText(
                    this,
                    "Payment Canceled",
                    Toast.LENGTH_LONG
                ).show()
                val result = Intent()
                result.putExtra(USER_EXTRA, "fail")
                setResult(Activity.RESULT_OK, result)
                finish()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(
                    this,
                    "Payment Failed. See logcat for details.",
                    Toast.LENGTH_LONG
                ).show()
                val result = Intent()
                result.putExtra(USER_EXTRA, "fail")
                setResult(Activity.RESULT_OK, result)
                finish()
            }
            is PaymentSheetResult.Completed -> {
                Toast.makeText(
                    this,
                    "Payment Complete",
                    Toast.LENGTH_LONG
                ).show()
                val result = Intent()
                result.putExtra(USER_EXTRA, "success")
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
    }

    override fun getBinding(): View {
        binding = ActivityStripUiBinding.inflate(layoutInflater)
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun getViewModel() = addCardViewModel


}