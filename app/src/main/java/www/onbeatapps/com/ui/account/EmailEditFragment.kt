package www.onbeatapps.com.ui.account

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentEmailEditBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class EmailEditFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentEmailEditBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by viewModels()
    var email= ""

    override fun setup() {
        val arguments = arguments
        if (arguments!=null) {
            email = requireArguments().getString("email", "")
        }
        setUpClick()
        setUpObsever()

    }

    private fun setUpObsever() {
        binding.apply {
            if(email!=""){
                editEmail.setText(email)
            }
        }
        accountViewModel.editStatus.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(mContext,"Email saved successfully", Toast.LENGTH_SHORT).show()
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
                editEmail.error = null
                if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.text.toString()).matches()) {
                    editEmail.error = "Enter valid mail"
                }else{
                    val paramObject = JsonObject()
                    paramObject.addProperty("email", editEmail.text.toString())
                    accountViewModel.editData(paramObject,2)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentEmailEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = accountViewModel

}