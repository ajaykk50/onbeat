package www.onbeatapps.com.ui.addWristBand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentCancelBandBinding
import www.onbeatapps.com.ui.account.AccountViewModel
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class CancelWristFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentCancelBandBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()

    override fun setup() {

        setUpClick()
        setUpObserver()

    }

    private fun setUpObserver() {
        accountViewModel.deleteCall.observe(viewLifecycleOwner){
            if (it) accountViewModel.bandTage()
        }
        accountViewModel.banStatus.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(R.id.nav_account)
            }
        }
    }


    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btOk.setOnClickListener {
                if (!accountViewModel.getCardStatus())
                accountViewModel.bandTage()
                else accountViewModel.settle(1)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentCancelBandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}