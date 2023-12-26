package www.onbeatapps.com.ui.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.stripe.android.*
import com.stripe.android.core.injection.PUBLISHABLE_KEY
import com.stripe.android.googlepaylauncher.GooglePayEnvironment
import com.stripe.android.googlepaylauncher.GooglePayLauncher
import com.stripe.android.googlepaylauncher.GooglePayPaymentMethodLauncher
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardMultilineWidget
import dagger.hilt.android.AndroidEntryPoint
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.coroutines.launch
import www.onbeatapps.com.databinding.FragmentPayBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import java.lang.ref.WeakReference
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.databinding.GooglePlayBinding


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class GoolePayActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: GooglePlayBinding
    private val activityViewModel: AuthViewModel by viewModels()
    private var clientSecret = "pi_1JEwTbHS4W4sRkKuGh1FSp9v_secret_VSWGGRW4FBX4ZhPGQ9u0lJPxo"
    private var paymentIntentClientSecret =
        "pi_1JEwTbHS4W4sRkKuGh1FSp9v_secret_VSWGGRW4FBX4ZhPGQ9u0lJPxo"
    private lateinit var stripe: Stripe
    var payType = ""
    override fun getBinding(): View {
        binding = GooglePlayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getViewModel() = activityViewModel
    override fun setup(savedInstanceState: Bundle?) {

//        stripe = Stripe(applicationContext, "pk_test_TYooMQauvdEDq54NiTphI7jx")
//        stripe = Stripe(applicationContext, BuildConfig.STRIP_KEY)
        click()

        PaymentConfiguration.init(this, BuildConfig.STRIP_KEY)
    }

    private fun click() {
        val googlePayLauncher = GooglePayPaymentMethodLauncher(
            activity = this,
            config = GooglePayPaymentMethodLauncher.Config(
                environment = GooglePayEnvironment.Test,
                merchantCountryCode = "FR",
                merchantName = "Widget Store"
            ),
            readyCallback = ::onGooglePayReady,
            resultCallback = ::onGooglePayResult
        )

//        val googlePayLauncher = GooglePayLauncher(
//            activity = this,
//            config = GooglePayLauncher.Config(
//                environment = GooglePayEnvironment.Test,
//                merchantCountryCode = "US",
//                merchantName = "Widget Store"
//            ),
//            readyCallback = ::onGooglePayReady,
//            resultCallback = ::onGooglePayResult
//        )

        binding.apply {
            pay.setOnClickListener {
//                googlePayLauncher.presentForPaymentIntent(clientSecret)
                googlePayLauncher.present(
                    currencyCode = "EUR",
                    amount = 1
                )
            }

        }
    }
    private fun onGooglePayResult(result: GooglePayLauncher.Result) {
        // implemented below
    }

    private fun onGooglePayReady(isReady: Boolean) {
//        googlePayButton.isEnabled = isReady
    }

    private fun onGooglePayResult(
        result: GooglePayPaymentMethodLauncher.Result
    ) {
        when (result) {
            is GooglePayPaymentMethodLauncher.Result.Completed -> {
                // Payment details successfully captured.
                // Send the paymentMethodId to your server to finalize payment.
                val paymentMethodId = result.paymentMethod.id
                Toast.makeText(this,"paymentMethodId:$paymentMethodId",Toast.LENGTH_SHORT).show()
            }
            GooglePayPaymentMethodLauncher.Result.Canceled -> {
                // User canceled the operation
            }
            is GooglePayPaymentMethodLauncher.Result.Failed -> {
                // Operation failed; inspect `result.error` for the exception
            }
        }
    }



}