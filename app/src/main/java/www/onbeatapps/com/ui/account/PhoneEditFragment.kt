package www.onbeatapps.com.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentPhoneEditBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class PhoneEditFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentPhoneEditBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()
    var phone = ""

    override fun setup() {
        if (arguments != null)
            phone = requireArguments().getString("phone", "")

        setUpClick()
        setUpAdapter()
        setUpObserver()

    }

    private fun setUpObserver() {
        binding.apply {
            if (phone != "")
                editPhone.setText(phone)
        }
        accountViewModel.editStatus.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(mContext,"Phone number saved successfully.y", Toast.LENGTH_SHORT).show()
                popBack()
            }
        }
    }

    private fun setUpAdapter() {
        val myArray = resources.getStringArray(R.array.country_data)

        val countryAdapter = ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item, myArray
        )

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnCountry.adapter = countryAdapter

    }

    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btSave.setOnClickListener {
                binding.apply {
                    if (editPhone.text.toString() != null && editPhone.text.toString().length < 10)
                        editPhone.error = resources.getString(R.string.vaild_m_number)
                    else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("phone", editPhone.text.toString())
                        accountViewModel.editData(paramObject,2)
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
        _binding = FragmentPhoneEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}