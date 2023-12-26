package www.onbeatapps.com.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.SampleFragmentBinding
import www.onbeatapps.com.databinding.SecndSplashBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.homeFragment.HomeViewModel

@AndroidEntryPoint
class SecondSplashFragment : BaseFragment<HomeViewModel>() {
    private var _binding: SecndSplashBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun setup() {
//        goHome()
    }

    fun goHome() {
        Handler().postDelayed({
            findNavController().navigate(R.id.navigation_home)
//            finish()
        }, 3000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SecndSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        goHome()
    }

    override fun getViewModel() = homeViewModel

}