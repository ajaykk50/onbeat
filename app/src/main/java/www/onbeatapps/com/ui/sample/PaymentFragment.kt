package www.onbeatapps.com.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.FragmentPayBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class PaymentFragment : BaseFragment<AuthViewModel>() {
    private var _binding: FragmentPayBinding? = null //
    private val binding get() = _binding!!
    private val homeViewModel: AuthViewModel by viewModels()

    override fun setup() {

        binding.apply {
//            cardForm.setOnFormFieldFocusedListener(activity)
//            cardForm.setOnCardFormSubmitListener(this)

            // initializing clients checks if union pay is enabled
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = homeViewModel






}