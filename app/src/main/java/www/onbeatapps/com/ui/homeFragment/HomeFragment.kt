package www.onbeatapps.com.ui.homeFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.HomeFragmentBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity.Companion.clickEventSlide
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity.Companion.exit
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity.Companion.naviId
import www.onbeatapps.com.utils.NotificationEvent
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode
//import www.onbeatapps.com.utils.Utils.Companion.createPDFFile
import www.onbeatapps.com.utils.urls

const val HIDE_COUNT = "hide_count"
const val PAGE_BACK = "hide_count"

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null
    var writBandBalance = ""

    override fun setup() {
        naviId =  R.id.nav_dash
        setUpRV()
        setUpClick()
        setUpObserver()
        balanceShow()
        homeViewModel.setPage("acc")

//        homeViewModel.getTransationList()
    }

    private fun setUpRV() {
        homeAdapter = HomeAdapter(mContext,homeViewModel)
        binding.recyTrans.apply {
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun setUpObserver() {
        binding.apply {
            homeViewModel.nodat.observe(viewLifecycleOwner) {
                if (it) {
                    noDatHide()
                    homeViewModel.nodat.value = false
                }
            }
            homeViewModel.trList.observe(viewLifecycleOwner) {
                balanceShow()
                btSeeAll.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) {
                    binding.apply {
                        txtNodat.visibility = View.GONE
                        txtTotalSpend.text = "${homeViewModel.currency} ${NumberFormatForCurrencyCode(homeViewModel.total_spent)}"
//                        txtSpendLimit.text =
//                            homeViewModel.roundBalance(homeViewModel.preAuthAmount).toString()
                    }
                    homeAdapter?.setListContent(it)
                } else {
                    txtTotalSpend.text = "${homeViewModel.currency} ${NumberFormatForCurrencyCode("00.00")}"
                    noDatHide()

                }
            }
            homeViewModel.wristBalance.observe(viewLifecycleOwner) {

                binding.txtCurrency1.text = homeViewModel.currency
                binding.txtCurrency2.text = homeViewModel.currency
                binding.txtSpenLimit.text = NumberFormatForCurrencyCode(homeViewModel.wristBalance.value!!.toString())
//                binding.txtSpendLimit.text = homeViewModel.roundBalance(homeViewModel.preAuthAmount)
                if (homeViewModel.wristBalance.value!!.toDouble() > 10) {
//                    if (homeViewModel.getPayment().isNotEmpty() && homeViewModel.wristBalance.value!!.toFloat()>10) {
//                        btSetLimit.text = resources.getString(R.string.add_band)
//                        btSetLimit.visibility = View.GONE
//                    } else
//                        btSetLimit.visibility = View.VISIBLE
                } else {
//                    bandShow()
                    btSetLimit.visibility = View.VISIBLE
                }
            }
        }
    }

    fun bandShow() {
        if (homeViewModel.getPayment().isNotEmpty())
            binding.btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
    }


    fun noDatHide() {
        binding.apply {
            txtNodat.visibility = View.VISIBLE
//            containTr.visibility = View.GONE
        }
    }

    fun balanceShow() {
        binding.apply {
            txtEvenNmae.text = homeViewModel.getEventName()
            if (homeViewModel.getSpentLimit() == "00.0" || homeViewModel.getSpentLimit() == "0"|| homeViewModel.getSpentLimit() == "0.0"){
                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.red_circle))
                txtBandStatus.text = "Please add funds"
            }else if (homeViewModel.getSpentLimit().toFloat() < 10){
                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.orange_circle))
                txtBandStatus.text = "Funds are low"
            }else if(homeViewModel.getBand().isEmpty()){
                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.yello_circle))
                txtBandStatus.text = "Awaiting event check-in"
            } else{
                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.green_circle))
                txtBandStatus.text = "Ready to party!"
            }
            if (homeViewModel.getBand().isEmpty()) {
//                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.red_circle))
//                btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
                btSetLimit.visibility = View.VISIBLE
                if (homeViewModel.getCardStatus()) {
                    btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
                }else btSetLimit.text = resources.getString(R.string.add_card_and_spending_limit)
            } else {
//                imgBandStatus.setBackgroundDrawable(mContext.getDrawable(R.drawable.green_circle))
//                btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
                if (homeViewModel.getCardStatus()) {
                    btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
                }else btSetLimit.text = resources.getString(R.string.add_card_and_spending_limit)
            }
            if (homeViewModel.getPayment().isNotEmpty() && homeViewModel.balance.toDouble()>10){
//                btSetLimit.visibility = View.GONE
            }else{
                btSetLimit.visibility = View.VISIBLE
//                btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
//                if (homeViewModel.getPayment().isEmpty())
//                    btSetLimit.text = resources.getString(R.string.set_new_spending_limit)
            }


        }
    }

    private fun setUpClick() {
        binding.apply {

            btSeeAll.setOnClickListener {
                findNavController().navigate(R.id.navigation_list_transaction)
            }
            btSidMenu.setOnClickListener {
                clickEventSlide()
            }
            btInvest.setOnClickListener {
                openUrl(urls.invest)
//                findNavController().navigate(R.id.nav_terms, bundleOf("menu_hide" to true , "page" to "invest"))
            }
            btSetLimit.setOnClickListener {

//                if (homeViewModel.getBand().isEmpty()) {
//                    findNavController().navigate(
//                        R.id.navigation_add_wrist_band,
//                        bundleOf(HIDE_COUNT to true)
//                    )
//                } else {
////
                    findNavController().navigate(
                        R.id.navigation_spending_limit,
                        bundleOf(HIDE_COUNT to true)
                    )
//                }
            }
            swipeRefresh.setOnRefreshListener {
                homeViewModel.getUserDt()
                swipeRefresh.isRefreshing = false
            }
        }
    }
    fun openUrl(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.dash_board_top)
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            exit()
        }
        return binding.root
    }



    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        homeViewModel.getUserDt()
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = homeViewModel

        @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NotificationEvent) {
        if (event == NotificationEvent.Reload) {
            homeViewModel.getUserDt()

        }
    }

}