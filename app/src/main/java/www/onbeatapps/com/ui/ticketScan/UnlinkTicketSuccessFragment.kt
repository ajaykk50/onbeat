package www.onbeatapps.com.ui.ticketScan

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentLostBandBinding
import www.onbeatapps.com.databinding.FragmentTicketTempleatBinding
import www.onbeatapps.com.databinding.FragmentUnlinkSuccessBinding
import www.onbeatapps.com.databinding.FragmentUnlinkTicketBinding
import www.onbeatapps.com.ui.account.AccountViewModel
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity

@AndroidEntryPoint
class UnlinkTicketSuccessFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentTicketTempleatBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()

    override fun setup() {

        setUpClick()
        setUi()

    }
    private fun setUi() {
        binding.apply {
            btBack.visibility = View.GONE
            btYes.visibility = View.GONE
            btNo.text = getString(R.string.got_it)
            txtDoLater.visibility = View.GONE
            toopBar.text = getString(R.string.ticket_unlinked)
            txtContent.text = getString(R.string.ticket_remove_success)
            icon.setImageResource(R.drawable.ic_unlink)
            btNo.setBackgroundDrawable(mContext.getDrawable(R.drawable.bg_orangr_bt))
            btYes.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
//            btYes.setOnClickListener {
//                DashBoardActivity.unlikTicket()
//            }
            btNo.setOnClickListener {
                DashBoardActivity.unlikTicket()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentTicketTempleatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}