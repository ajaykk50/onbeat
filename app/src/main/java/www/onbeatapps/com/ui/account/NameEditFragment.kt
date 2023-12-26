package www.onbeatapps.com.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentNameEditBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class NameEditFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentNameEditBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()
    var fname = ""
    var lname = ""

    override fun setup() {
        val arguments = arguments
        if (arguments!=null) {
            fname = requireArguments().getString("f_name", "")
            lname = requireArguments().getString("l_name", "")
        }

        setUpClick()
        setUpObserver()

    }

    private fun setUpObserver() {
        binding.apply {
            editfName.setText(fname)
            editsname.setText(lname)
        }
        accountViewModel.editStatus.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(mContext,"Name saved successfully.",Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }


    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btSave.setOnClickListener {
                if(validate()) {
                    val paramObject = JsonObject()
                    paramObject.addProperty("first_name", editfName.text.toString())
                    paramObject.addProperty("last_name", editsname.text.toString())
                    accountViewModel.editData(paramObject,1)
                }
            }
        }
    }

    private fun validate(): Boolean {

        binding.apply {
            editfName.error = null
            editsname.error = null
            if (editfName.text.toString().isNullOrBlank()) {
                editfName.error = resources.getString(R.string.enter_name)
                return false
            }
           else if (editsname.text.toString().isNullOrBlank()){
//                editsname.error = resources.getString(R.string.enter_s_name)
//                return false
                editsname.setText("")
                return true
            }
            else return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentNameEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}