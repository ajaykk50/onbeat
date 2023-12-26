package www.onbeatapps.com.ui.welcomeActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.FragmentEnjoyBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity

@AndroidEntryPoint
class EnjoyFragment : BaseFragment<WelcomeViewModel>() {
    private var _binding: FragmentEnjoyBinding? = null
    private val binding get() = _binding!!
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun setup() {
        setUpClick()
    }

    private fun setUpClick() {
        binding.apply {
            btLetGo.setOnClickListener {
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
        _binding = FragmentEnjoyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = welcomeViewModel

}