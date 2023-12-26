package www.onbeatapps.com.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentEditPinBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class PinEditFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentEditPinBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()
    var phone = ""

    override fun setup() {

        setUpClick()
        setUpObserver()

    }

    private fun setUpObserver() {
        accountViewModel.editStatus.observe(viewLifecycleOwner) {
            if (it) {
                accountViewModel.setPin(binding.txtNewPin.text.toString())
                accountViewModel.showToast("Passcode changed successfully")
                popBack()
            }
        }
    }

    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                popBack()
            }
            btSave.setOnClickListener {
                binding.apply {
                    if (txtOldPin.text.toString().length != 4)
                        Toast.makeText(mContext,resources.getString(R.string.entet_old_pass),Toast.LENGTH_SHORT).show()
                    else if (txtNewPin.text.toString().length != 4)
                    Toast.makeText(mContext,resources.getString(R.string.entet_newPass),Toast.LENGTH_SHORT).show()
                    else if (txtNewPin.text.toString() != txtConPin.text.toString()) {
                        Toast.makeText(mContext,resources.getString(R.string.not_match_pin),Toast.LENGTH_SHORT).show()
                    } else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("old_pin", txtOldPin.text.toString())
                        paramObject.addProperty("pin", txtNewPin.text.toString())
                        paramObject.addProperty("gender", "")
                        accountViewModel.editData(paramObject,4)
                    }
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentEditPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}