package www.onbeatapps.com.ui.setSpendingLimit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.stripe.android.Stripe
import com.stripe.android.getPaymentIntentResult
import com.stripe.android.model.StripeIntent
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentSpendingLimitBinding
import www.onbeatapps.com.ui.addCard.PayStripUIActivity
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode
import java.lang.ref.WeakReference

const val USER_EXTRA = "user_extra"

@AndroidEntryPoint
class SetSpendingLimitFragment : BaseFragment<SpendingLimitViewModel>() {
    private var _binding: FragmentSpendingLimitBinding? = null
    private val binding get() = _binding!!
    private val spendingLimitViewModel: SpendingLimitViewModel by viewModels()
    var hideCount = false
    var amount = ""
    var spendingAdapter: SpendingLimitAdapter? = null
    private lateinit var stripe: Stripe
    private lateinit var paymentSheet: PaymentSheet
    var status = ""

    override fun setup() {
        hideCount = requireArguments().getBoolean(HIDE_COUNT, false)
        setUpClick()
        setUpObserver()
        setUpRecy()
        displayContent("SPENT")
        spendingLimitViewModel.setLowBalance("")
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("type")
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                displayContent(result)
            }
    }

    private fun displayContent(type: String) {

        binding.apply {
            if (type == "SPENT") {
                containPreauth.visibility = View.GONE
                containSpend.visibility = View.VISIBLE
            } else {
                containPreauth.visibility = View.VISIBLE
                containSpend.visibility = View.GONE
//                Log.e("Amount","${spendingLimitViewModel.Currency()}${spendingLimitViewModel.getUsedAmount()}")
//                txtContent.text = "This will settle your existing pre-authorisation, debiting ${spendingLimitViewModel.Currency()}${spendingLimitViewModel.getUsedAmount()} from your card.\n\nYour new wallet balance will then be ${spendingLimitViewModel.Currency()}$amount"
                txtContent.text =
                    "This will settle your existing pre-authorisation, debiting ${spendingLimitViewModel.Currency()}${
                        NumberFormatForCurrencyCode(spendingLimitViewModel.userAmount().toString())
                    } from your card.\n\nPlease note: Settlement can take up to 7 days depending on your bank\n\nTop up will pre-authorise ${spendingLimitViewModel.Currency()}${
                        NumberFormatForCurrencyCode(
                            spendingLimitViewModel.getTemAmount()
                        )
                    } on your card and your new wallet balance will then be ${spendingLimitViewModel.Currency()}${
                        NumberFormatForCurrencyCode(
                            spendingLimitViewModel.getTemAmount()
                        )
                    }"
            }
        }
    }


    private fun setUpRecy() {
        binding.apply {

            val layoutManager = GridLayoutManager(mContext, 2)
            recyPrice.layoutManager = layoutManager
            spendingAdapter = SpendingLimitAdapter(mContext, spendingLimitViewModel) {
                refreshamout()
                amount = it.toString()
                spendingLimitViewModel.setTempAmount(amount)

            }
            recyPrice.apply {
                setHasFixedSize(true)
                adapter = spendingAdapter
            }
            spendingAdapter!!.setListContent(1)
        }

    }

    fun refreshamout() {
        binding.apply {
            editAmount.setText("")
        }
    }

    private fun setUpObserver() {

        binding.editAmount.addTextChangedListener {
            amount = if (it.isNullOrBlank()) {
                ""
            } else {
                it.toString()
            }
            if (binding.editAmount.text.toString() != "")
                spendingLimitViewModel.setTempAmount(amount)
        }
        binding.editAmount.hint =
            "Enter custom amount (Minimum ${spendingLimitViewModel.Currency()}20)"// & Max ${spendingLimitViewModel.Currency()}50)"
        binding.txtSubContent.text =
            "(Remember, the card limit is ${spendingLimitViewModel.Currency()}50... we suggest using it all)"
        if (hideCount) {
            binding.btback.visibility = View.VISIBLE
            binding.containNote.visibility = View.GONE
            binding.txtNote.visibility = View.GONE
            if (spendingLimitViewModel.getUsedAmount() != 0.0) {
                binding.txtFundAdd.visibility = View.GONE
                binding.txtSetil.visibility = View.VISIBLE
            } else {
                binding.txtFundAdd.visibility = View.VISIBLE
                binding.txtSetil.visibility = View.GONE
            }
        } else {
            binding.txtNote.text = getString(R.string.all_fund_no_use)
            binding.containNote.visibility = View.GONE
            binding.txtNote.visibility = View.GONE
            binding.btback.visibility = View.INVISIBLE
        }
        binding.apply {
            if (!hideCount) {
                btSkip.text = getString(R.string.skip_for_now)
                toopBar.visibility = View.VISIBLE
            } else {
                btSkip.text = getString(R.string.cancel)
                toopBar.visibility = View.GONE
            }
            editAmount.addTextChangedListener {
                amount = it.toString().trim()
                spendingAdapter?.notifiy()

            }
            spendingLimitViewModel.cardDt.observe(viewLifecycleOwner) {
                if (it.card.isNotEmpty()) {
                    spendingLimitViewModel.settle(it.card[0].paymentMethodId)
                } else {
                    spendingLimitViewModel.addSpendLimit("${spendingLimitViewModel.getTemAmount()}")
//
                }
            }
            spendingLimitViewModel.settleStatus.observe(viewLifecycleOwner) {
                if (it) {
                    spendingLimitViewModel.addSpendLimit("${spendingLimitViewModel.getTemAmount()}")
//                    callPaySheet()
                }
            }
        }

        spendingLimitViewModel.addLimit.observe(viewLifecycleOwner) {
            if (it) {
                if (!spendingLimitViewModel.getCardStatus()) {
                    findNavController().navigate(
                        R.id.navigation_add_card,
                        bundleOf(
                            HIDE_COUNT to hideCount,
                            "Amount" to spendingLimitViewModel.getTemAmount()
                        )
                    )
                } else {
//                    val weakActivity = WeakReference<Context>(mContext)
//                    val activity = weakActivity.get()!!
//                    stripe.handleNextActionForPayment(this, spendingLimitViewModel.paymentIntentClientSecret)
                    callPaySheet()
//                    spendingLimitViewModel.getListCard(amount)


                }
                spendingLimitViewModel.addLimit.value = false
            }
        }
    }


    fun callPaySheet() {
        val i = Intent(mContext, PayStripUIActivity::class.java)
        i.putExtra("clint", spendingLimitViewModel.paymentIntentClientSecret)
        i.putExtra("customer", spendingLimitViewModel.customerId)
        i.putExtra("ephKey", spendingLimitViewModel.ephemeralKeySecret)
        startActivityForResult(i, 150)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (requestCode) {100 -> {
        if (requestCode == 150) {
            val status = data?.extras?.getString(USER_EXTRA)
            if (status == "success") {
                spendingLimitViewModel.setBalance(amount)
                findNavController().navigate(R.id.navigation_home)
            }

        } else {
            val weakActivity = WeakReference<Context>(mContext)
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
                                        mContext,
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
                                        mContext,
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


//            else -> super.onActivityResult(requestCode, resultCode, data)
//        }
    }

    private fun displayAlert(
        activity: Context,
        title: String,
        message: String,
        restartDemo: Boolean = false
    ) {
//        runOnUiThread {
        val builder = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)

        builder.setPositiveButton("Ok", null)
        builder.create().show()
//        }
    }

    private fun setUpClick() {
        binding.apply {
            btFind.setOnClickListener {
                findNavController().navigate(R.id.navigation_abou, bundleOf(PAGE_TYPE to "preAuth"))
            }
            txtFundAdd.setOnClickListener {
                findNavController().navigate(R.id.navigation_abou, bundleOf(PAGE_TYPE to "preAuth"))
            }
            txtSetil.setOnClickListener {
                findNavController().navigate(R.id.navigation_abou, bundleOf(PAGE_TYPE to "preAuth"))
            }
            btback.setOnClickListener {
                if (hideCount)
                    findNavController().navigate(R.id.navigation_home)
                else baseActivity.launchActivity<DashBoardActivity> {
                    putExtra("type", 1)
                }
            }
            btBack.setOnClickListener {
                displayContent("SPENT")
            }
            btNext.setOnClickListener {
                if (amount != "" && amount.isNotEmpty()) {
                    if (amount.toInt() < 20) {
                        Toast.makeText(
                            mContext,
                            "Minimum required spending limit is ${spendingLimitViewModel.Currency()} 20. Try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (amount.toInt() > 200) {
                        Toast.makeText(
                            mContext,
                            "Maximum allowed spending limit is ${spendingLimitViewModel.Currency()} 200. Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (spendingLimitViewModel.getCardStatus())
                            displayContent("PREE_AUTH")
                        else {
                            if (spendingLimitViewModel.getCardStatus())
                                spendingLimitViewModel.getListCard(spendingLimitViewModel.getTemAmount())
                            else
                                spendingLimitViewModel.addSpendLimit("${spendingLimitViewModel.getTemAmount()}")
                        }
                    }
                } else Toast.makeText(
                    mContext,
                    "Enter a custom limit or select a limit from the options given.",
                    Toast.LENGTH_SHORT
                ).show()
//                }else{
//                    presentPaymentSheet()
////                    findNavController().navigate(R.id.nav_saved_Card)
//                }

            }
            btYes.setOnClickListener {
                if (spendingLimitViewModel.getCardStatus())
                    spendingLimitViewModel.getListCard(amount)
                else
                    spendingLimitViewModel.addSpendLimit("$amount")
            }
            btNo.setOnClickListener {
                displayContent("SPENT")
            }
            btSkip.setOnClickListener {
                if (hideCount)
                    findNavController().navigate(R.id.navigation_home)
                else baseActivity.launchActivity<DashBoardActivity> {
                    putExtra("type", 0)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentSpendingLimitBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (hideCount)
                findNavController().navigate(R.id.navigation_home)
            else {
//                baseActivity.launchActivity<DashBoardActivity> { }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = spendingLimitViewModel

}