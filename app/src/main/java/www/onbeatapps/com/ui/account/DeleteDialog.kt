package www.onbeatapps.com.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.DeleteDialogBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseDialogFragment
import www.onbeatapps.com.ui.setSpendingLimit.SpendingLimitViewModel
import www.onbeatapps.com.utils.Utils
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode

@AndroidEntryPoint
class DeleteDialog : BaseDialogFragment() {

    private var phone: String = ""
    private var _binding: DeleteDialogBinding? = null
    private val binding get() = _binding!!
    private val spendingLimitViewModel: SpendingLimitViewModel by activityViewModels()
    private lateinit var updateFunc: (String) -> Unit
    override fun setup() {
        bindUI()
        setupClickListerner()
    }

    private fun bindUI() {
        binding.apply {
            when (mType) {
                DialogType.UNLINK -> {
                    txtTitle.text = resources.getString(R.string.unlink_ticket_)
                    txtDes.text = resources.getString(R.string.unlink_note)
                    btDelete.text = resources.getString(R.string.unlink_ticket_bt)
                }
                DialogType.DELETE -> {
                    txtTitle.text = resources.getString(R.string.delet_card)
                    txtDes.text = "This will settle your existing pre-authorisation, debiting ${spendingLimitViewModel.Currency()}${
                        NumberFormatForCurrencyCode(spendingLimitViewModel.userAmount().toString().replace("-",""))} from your card"//resources.getString(R.string.delet_settile_note)
                    btDelete.text = resources.getString(R.string.delete)
                }
                DialogType.REPLACE -> {
                    txtTitle.text = resources.getString(R.string.replace_card)
                    txtDes.text = "This will settle your existing pre-authorisation, debiting ${spendingLimitViewModel.Currency()}${NumberFormatForCurrencyCode(spendingLimitViewModel.userAmount().toString().replace("-",""))} from your card"//resources.getString(R.string.replace_settile_note)
                    btDelete.text = resources.getString(R.string.replace)
                }DialogType.LOG_OUT -> {
                    txtTitle.text = resources.getString(R.string.log_out)
                    txtDes.text = resources.getString(R.string.log_out_content)
                    btDelete.text = resources.getString(R.string.logout)
                }
            }
        }
    }


    private fun setupClickListerner() {

        binding.apply {
            btCancel.setOnClickListener {
                dismiss()
                updateFunc("cancel")
            }
            btDelete.setOnClickListener {
                dismiss()
                when (mType) {
                    DialogType.DELETE -> {
                        updateFunc("delete")
                    }
                    DialogType.REPLACE -> {
                        updateFunc("proceed")
                    }
                    DialogType.LOG_OUT -> {
                        updateFunc("logOut")
                    }
                    DialogType.UNLINK -> {
                        updateFunc("unlink")
                    }
                }
            }
        }
    }

    fun addSuccessListerner(update: (String) -> Unit) {
        updateFunc = update
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DeleteDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = DeleteDialog::class.java.simpleName
        private lateinit var mType: DialogType

        @JvmStatic
        fun newInstance(type: DialogType): DeleteDialog {
            mType = type
            return DeleteDialog()
        }
    }
}

enum class DialogType {
    DELETE,
    UNLINK,
    REPLACE,
    LOG_OUT,
}