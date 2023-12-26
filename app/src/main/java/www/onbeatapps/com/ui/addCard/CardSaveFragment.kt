package www.onbeatapps.com.ui.addCard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stripe.android.Stripe
import com.stripe.android.getSetupIntentResult
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardMultilineWidget
import dagger.hilt.android.AndroidEntryPoint
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import kotlinx.coroutines.launch
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.SaveCardStripRESPONSE
import www.onbeatapps.com.databinding.FragmentAddCardBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import java.lang.ref.WeakReference

@AndroidEntryPoint
class CardSaveFragment : BaseFragment<AddCardViewModel>() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val addCardViewModel: AddCardViewModel by viewModels()
    private lateinit var stripe: Stripe

    override fun setup() {
        stripe = Stripe(mContext, BuildConfig.STRIP_KEY)
        setUpClick()
        setUpObserver()
        addCardViewModel.saveCard()
    }
    private fun setUpObserver() {
//        addCardViewModel.cardId.observe(viewLifecycleOwner)
        binding.apply {
            txtTitle.text = resources.getString(R.string.add_card)
            tvTextAmount.visibility = View.GONE
            containNote.visibility = View.GONE
            btNext.text = resources.getString(R.string.add_card)
        }
    }

    private fun setUpClick() {
        binding.apply {

            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btSkip.setOnClickListener {
                findNavController().popBackStack()
            }
            btNext.setOnClickListener {
//                addCardViewModel.loading(true)
                saveCard(cardInputWidget)
            }
            btScanCard.setOnClickListener {
                    val intent = Intent(mContext, CardIOActivity::class.java)
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
                    resultDisplayStr += """CVV has ${scanResult.cvv.length} digits.
"""
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
        }
        else {
            val weakActivity = WeakReference<Context>(mContext)
            if (stripe.isSetupResult(requestCode, data)) {
                lifecycleScope.launch {
                    runCatching {
                        stripe.getSetupIntentResult(requestCode, data!!).intent
                    }.fold(
                        onSuccess = { setupIntent ->
                            val status = setupIntent.status
                            if (status == StripeIntent.Status.Succeeded) {
//                                addCardViewModel.setCardSve()

                                val gson = GsonBuilder().setPrettyPrinting().create()
                                val dta = gson.toJson(setupIntent)
                                val gson1 = Gson()

                                val paymentData: SaveCardStripRESPONSE = gson1.fromJson(dta, SaveCardStripRESPONSE::class.java)

                                addCardViewModel.paymentID(paymentData.paymentMethodId)
                                addCardViewModel.showToast("New card added successfully.")
                                addCardViewModel.loading(false)
                                findNavController().popBackStack()
                                // Setup completed successfully

//                                weakActivity.get()?.let { activity ->
//                                    displayAlert(
//                                        activity,
//                                        "Payment succeeded",
//                                        gson.toJson(setupIntent)
//                                    )
//                                }
                            } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                                addCardViewModel.loading(false)
                                Toast.makeText(mContext,"Payment failed", Toast.LENGTH_SHORT).show()
                                // Setup failed
//                                weakActivity.get()?.let { activity ->
//                                    displayAlert(
//                                        activity,
//                                        "Payment failed",
//                                        setupIntent.lastSetupError?.message.toString()
//                                    )
//                                }

                            }
                        },
                        onFailure = {
                            // Setup reqest failed
                            addCardViewModel.loading(false)
                            Toast.makeText(mContext,"Payment failed", Toast.LENGTH_SHORT).show()

//                            displayAlert(weakActivity.get()!!, "Payment failed", it.toString())
                        }
                    )
                }
            }
        }
    }

    fun saveCard(cardInputWidget: CardMultilineWidget){
        val params = cardInputWidget.paymentMethodCreateParams
        if (params != null) {
            addCardViewModel.loading(true)
            cardInputWidget.paymentMethodCreateParams?.let { paymentMethodParams ->
                // Create SetupIntent confirm parameters with the above
                val confirmParams = ConfirmSetupIntentParams
                    .create(paymentMethodParams, addCardViewModel.cardId)
                stripe.confirmSetupIntent(this, confirmParams)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = addCardViewModel

}