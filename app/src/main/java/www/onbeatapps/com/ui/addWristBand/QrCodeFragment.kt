package www.onbeatapps.com.ui.addWristBand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.FragmentQrCodeScanBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class QrCodeFragment : BaseFragment<AddwristBandViewModel>() {
    private var _binding: FragmentQrCodeScanBinding? = null
    private val binding get() = _binding!!
    private val addBandViewModel: AddwristBandViewModel by viewModels()
    private lateinit var codeScanner: CodeScanner

    override fun setup() {
        setUpClick()
        setUpScanner()


    }

    private fun setUpObserver() {

    }

    private fun setUpScanner() {
        codeScanner = CodeScanner(mContext, binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                addBandViewModel.addCode(it.text)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("code", it.text)
                findNavController().popBackStack()
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
               findNavController().popBackStack()
           }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = addBandViewModel

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}