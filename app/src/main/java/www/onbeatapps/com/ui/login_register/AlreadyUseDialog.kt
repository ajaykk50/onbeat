package www.onbeatapps.com.ui.login_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.DialogAlreadyRegisterBinding
import www.onbeatapps.com.ui.account.DeleteDialog
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseDialogFragment

@AndroidEntryPoint
class AlreadyUseDialog : BaseDialogFragment() {

    private var _binding: DialogAlreadyRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()
    override fun setup() {
        setupClickListerner()
    }




    private fun setupClickListerner() {

        binding.apply {

            btOk.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogAlreadyRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = AlreadyUseDialog::class.java.simpleName

        @JvmStatic
        fun newInstance(): AlreadyUseDialog {
            return AlreadyUseDialog()
        }
    }
}
