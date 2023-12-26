package www.onbeatapps.com.ui.welcomeActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentWelcomeBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<WelcomeViewModel>() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun setup() {
        setUpClick()

    }

    private fun setUpClick() {
        binding.apply {
            btBegin.setOnClickListener {
                findNavController().navigate(R.id.navigation_spending_limit, bundleOf(HIDE_COUNT to false))
            }
            btLater.setOnClickListener {
                baseActivity.launchActivity<DashBoardActivity>{
                    putExtra("type",0)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = welcomeViewModel

}