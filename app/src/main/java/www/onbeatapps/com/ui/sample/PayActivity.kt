package www.onbeatapps.com.ui.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.stripe.android.*
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


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class PayActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: FragmentPayBinding
    private val activityViewModel: AuthViewModel by viewModels()
    private var paymentIntentClientSecret =
        "pi_1JEwTbHS4W4sRkKuGh1FSp9v_secret_VSWGGRW4FBX4ZhPGQ9u0lJPxo"
    private lateinit var stripe: Stripe
    var payType = ""
    override fun getBinding(): View {
        binding = FragmentPayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getViewModel() = activityViewModel
    override fun setup(savedInstanceState: Bundle?) {
        binding.apply {
//            cardInputWidget.setCardNumber("411111111111")
        }
//        stripe = Stripe(applicationContext, "pk_test_TYooMQauvdEDq54NiTphI7jx")
        stripe = Stripe(applicationContext, BuildConfig.STRIP_KEY)
        click()
    }

    private fun click() {
        binding.apply {
            btPay.setOnClickListener {
                payType = "pay"
                pay(cardInputWidget)
//                saveCard(cardInputWidget)
//                save(cardInputWidget)
            }
            btSave.setOnClickListener {
                payType = "save"
                saveCard(cardInputWidget)
            }
            btClear.setOnClickListener {
                ediSecrit.setText("")
            }
            btScan.setOnClickListener {
                val intent = Intent(this@PayActivity, CardIOActivity::class.java)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                    .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false)
                    .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                    .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                    .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false)
                startActivityForResult(intent, 100)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            var resultDisplayStr: String
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult: CreditCard =
                    data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)!!

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = """
                Card Number: ${scanResult.cardNumber}
                
                """.trimIndent()
                binding.cardInputWidget.setCardNumber(scanResult.cardNumber)

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid) {
                    resultDisplayStr += """
                    Expiration Date: ${scanResult.expiryMonth}/${scanResult.expiryYear}
                    
                    """.trimIndent()
                    binding.cardInputWidget.setExpiryDate(
                        scanResult.expiryMonth,
                        scanResult.expiryYear
                    )
                }
                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += """CVV has ${scanResult.cvv.length} digits."""
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += """
                    Postal Code: ${scanResult.postalCode}
                    
                    """.trimIndent()
                }
            } else {
                resultDisplayStr = "Scan was canceled."
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        } else {
            val weakActivity = WeakReference<Activity>(this)
            if (payType == "pay") {
                if (stripe.isPaymentResult(requestCode, data)) {
                    lifecycleScope.launch {
                        runCatching {
                            stripe.getPaymentIntentResult(requestCode, data!!).intent
                        }.fold(
                            onSuccess = { paymentIntent ->
                                val status = paymentIntent.status
                                if (status == StripeIntent.Status.Succeeded) {
                                    val gson = GsonBuilder().setPrettyPrinting().create()
//                                Toast.makeText(this@PayActivity,"Payment succeeded",Toast.LENGTH_SHORT).show()

//                                displayAlert(
//                                    weakActivity.get()!!,
//                                    "Payment succeeded",
//                                    gson.toJson(paymentIntent),
//                                    restartDemo = true
//                                )
                                    weakActivity.get()?.let { activity ->
                                        displayAlert(
                                            activity,
                                            "Payment succeeded",
                                            gson.toJson(paymentIntent)
                                        )
                                    }
                                } else {
//                                Toast.makeText(this@PayActivity,"Payment failed",Toast.LENGTH_SHORT).show()

//                                displayAlert(
//                                    weakActivity.get()!!,
//                                    "Payment failed",
//                                    paymentIntent.lastPaymentError?.message ?: ""
//                                )
                                    weakActivity.get()?.let { activity ->
                                        displayAlert(
                                            activity,
                                            "Payment failed",
                                            paymentIntent.lastPaymentError?.message.orEmpty()
                                        )
                                    }
                                }
                            },
                            onFailure = {
//                            Toast.makeText(this@PayActivity,"Payment failed",Toast.LENGTH_SHORT).show()

                                displayAlert(weakActivity.get()!!, "Payment failed", it.toString())
                            }
                        )
                    }
                }
            }
//            val weakActivity = WeakReference<Activity>(this)
            else{
                if (stripe.isSetupResult(requestCode, data)) {
                    lifecycleScope.launch {
                        runCatching {
                            stripe.getSetupIntentResult(requestCode, data!!).intent
                        }.fold(
                            onSuccess = { setupIntent ->
                                val status = setupIntent.status
                                if (status == StripeIntent.Status.Succeeded) {
                                    // Setup completed successfully
                                    val gson = GsonBuilder().setPrettyPrinting().create()
                                    weakActivity.get()?.let { activity ->
                                        displayAlert(
                                            activity,
                                            "Payment succeeded",
                                            gson.toJson(setupIntent)
                                        )
                                    }
                                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                                    // Setup failed
                                    weakActivity.get()?.let { activity ->
                                        displayAlert(
                                            activity,
                                            "Payment failed",
                                            setupIntent.lastSetupError?.message.toString()
                                        )
                                    }
                                }
                            },
                            onFailure = {
                                // Setup reqest failed
                                displayAlert(weakActivity.get()!!, "Payment failed", it.toString())
                            }
                        )
                    }
                }
        }
        }
    }

    fun pay(cardInputWidget: CardMultilineWidget) {
        val params = cardInputWidget.paymentMethodCreateParams
        if (params != null) {
//            val confirmParams = ConfirmPaymentIntentParams
//                .createWithPaymentMethodCreateParams(
//                    params, paymentIntentClientSecret, null, false,
//                    mapOf("setup_future_usage" to "on_session")
//                )
//            stripe = Stripe(
//                applicationContext,
//                PaymentConfiguration.getInstance(applicationContext).publishableKey
//            )
//            stripe.confirmPayment(this@PayActivity, confirmParams)

            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, binding.ediSecrit.text.toString())
                stripe.confirmPayment(this, confirmParams)
            }
        }
    }
    fun saveCard(cardInputWidget: CardMultilineWidget){
        cardInputWidget.paymentMethodCreateParams?.let { paymentMethodParams ->
            // Create SetupIntent confirm parameters with the above
            val confirmParams = ConfirmSetupIntentParams
                .create(paymentMethodParams, binding.ediSecrit.text.toString())
            stripe.confirmSetupIntent(this, confirmParams)
        }
    }

    fun save(cardInputWidget: CardMultilineWidget) {
        val params = cardInputWidget.paymentMethodCreateParams ?: return

        stripe = Stripe(
            applicationContext,
            PaymentConfiguration.getInstance(applicationContext).publishableKey
        )
        lifecycleScope.launch {
            runCatching {
                stripe.createPaymentMethod(params).id
            }.fold(
                onSuccess = { paymentMethodId ->
                    // Send paymentMethodId to your server for the next steps
                },
                onFailure = {
                    // Create PaymentMethod failed, display the error to the user
                }
            )
        }
    }

    private fun displayAlert(
        activity: Activity,
        title: String,
        message: String,
        restartDemo: Boolean = false
    ) {
        runOnUiThread {
            val builder = AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)

            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }


}