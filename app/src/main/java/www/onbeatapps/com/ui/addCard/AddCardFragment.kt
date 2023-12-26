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
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.stripe.android.Stripe
import com.stripe.android.getPaymentIntentResult
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardMultilineWidget
import dagger.hilt.android.AndroidEntryPoint
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import kotlinx.coroutines.launch
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentAddCardBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.utils.Utils
import java.lang.ref.WeakReference

@AndroidEntryPoint
class AddCardFragment : BaseFragment<AddCardViewModel>() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val addCardViewModel: AddCardViewModel by viewModels()
    var hideCount = false
    var amount = "0"
    private lateinit var stripe: Stripe

    override fun setup() {
        hideCount = requireArguments().getBoolean(HIDE_COUNT,false)
        amount = requireArguments().getString("Amount","0")
        stripe = Stripe(mContext, BuildConfig.STRIP_KEY)
        setUpClick()
        setUpObserver()
    }
    private fun setUpObserver() {
        binding.apply {
            tvTextAmount.text = "Top up will pre-authorise ${addCardViewModel.getCurrency()} ${Utils.NumberFormatForCurrencyCode(amount)}\non your card"
            if (!hideCount) {
                btSkip.visibility = View.VISIBLE
                binding.btBack.visibility = View.VISIBLE
//                btback.visibility = View.INVISIBLE
            }
            else {
                btSkip.visibility = View.VISIBLE
                binding.btBack.visibility = View.VISIBLE
//                btNext.text = "Next"
//                btback.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpClick() {
        binding.apply {
            txtFundAdd.setOnClickListener {
                findNavController().navigate(R.id.navigation_abou, bundleOf(PAGE_TYPE to "preAuth"))
            }
            btBack.setOnClickListener {
//                findNavController().navigate(R.id.action_navigation_add_card_to_navigation_spending_limit,
//                    bundleOf(HIDE_COUNT to hideCount))
//                if (hideCount)
                findNavController().popBackStack()
//                else
//                findNavController().navigate(R.id.action_navigation_add_card_to_navigation_spending_limit,
//                    bundleOf(HIDE_COUNT to hideCount))


            }
            btNext.setOnClickListener {
                pay(cardInputWidget)
//                findNavController().navigate(R.id.action_navigation_add_card_to_navigation_enjoy2)
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
            btSkip.setOnClickListener {
                if (hideCount)
                    findNavController().navigate(R.id.navigation_home)
                else baseActivity.launchActivity<DashBoardActivity> {
                    putExtra("type",1)
                }
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

                resultDisplayStr = """
                Card Number: ${scanResult.cardNumber}
                
                """.trimIndent()
                binding.cardInputWidget.setCardNumber(scanResult.cardNumber)

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
        }
        else {
            val weakActivity = WeakReference<Context>(mContext)
            if (stripe.isPaymentResult(requestCode, data)) {
                lifecycleScope.launch {
                    runCatching {
                        stripe.getPaymentIntentResult(requestCode, data!!).intent
                    }.fold(
                        onSuccess = { paymentIntent ->
                            when (paymentIntent.status) {
                                StripeIntent.Status.RequiresCapture -> {
                                    addCardViewModel.loading(false)
                                    addCardViewModel.cardStatus()
                                    Toast.makeText(mContext,"Pre-authorisation  is successful.",Toast.LENGTH_SHORT).show()
                                    if (!hideCount) findNavController().navigate(R.id.action_navigation_add_card_to_navigation_enjoy2)
                                    else baseActivity.launchActivity<DashBoardActivity> {
                                        putExtra("type",1)
                                    }

                                }
                                StripeIntent.Status.Succeeded -> {
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
                                            "Payment succeeded",
                                            gson.toJson(paymentIntent)
                                        )
                                    }
                                }
                                else -> {
                                    addCardViewModel.loading(false)
                                    Toast.makeText(mContext,"Payment failed",Toast.LENGTH_SHORT).show()

                        //                                displayAlert(
                        //                                    weakActivity.get()!!,
                        //                                    "Payment failed",
                        //                                    paymentIntent.lastPaymentError?.message ?: ""
                        //                                )
                                                        weakActivity.get()?.let { activity ->
                                                            displayAlert(
                                                                "Payment failed",
                                                                paymentIntent.lastPaymentError?.message.orEmpty()
                                                            )
                                                        }
                                }
                            }
                        },
                        onFailure = {
                            addCardViewModel.loading(false)
                            Toast.makeText(mContext,"Payment failed",Toast.LENGTH_SHORT).show()
                            it.printStackTrace()
//                            displayAlert( "Payment failed", it.toString())
                        }
                    )
                }
            }
        }
    }

    fun pay(cardInputWidget: CardMultilineWidget) {
        val params = cardInputWidget.paymentMethodCreateParams
        if (params != null) {

            addCardViewModel.loading(true)
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, addCardViewModel.getClientSecretKey())
                stripe.confirmPayment(this, confirmParams)
            }
        }
    }

    private fun displayAlert(
        title: String,
        message: String,
        restartDemo: Boolean = false
    ) {
//        runOnUiThread {
            val builder = AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)

            builder.setPositiveButton("Ok", null)
            builder.create().show()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){

            if (hideCount)
                findNavController().navigate(R.id.action_navigation_add_card_to_navigation_spending_limit,
                    bundleOf(HIDE_COUNT to hideCount))
            else baseActivity.launchActivity<DashBoardActivity> {
                putExtra("type",1)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = addCardViewModel

}