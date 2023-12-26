package www.onbeatapps.com.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentAboutBinding
import www.onbeatapps.com.databinding.FragmentEventInfoBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.utils.Utils.Companion.dateConvert

const val PAGE_TYPE = "page_type"
const val AMOUT = "amount"
@AndroidEntryPoint
class aboutFragment : BaseFragment<aboutViewModel>() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val eventInfoViewModel: aboutViewModel by viewModels()
    var pageType = ""

    override fun setup() {
        arguments?.let {
            pageType = it.getString(PAGE_TYPE).toString()
        }
        setUpClick()
        setUpPage()
    }

    private fun setUpPage() {
        binding.apply {
            if (pageType=="scan"){
                txtTitle.text = getString(R.string.what_is_on)
                txtContent.text = getString(R.string.content_what_onbeat)
            }else if (pageType == "preAuth"){
                txtTitle.text = getString(R.string.about_pre_auth)
//                txtContent.text = HtmlCompat.fromHtml(getString(R.string.what_is_pre_auth), 0)
            }
        }

    }


    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {

                if (pageType == "preAuth") {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("type", "PREE_AUTH")
                    findNavController().popBackStack()
                }else findNavController().popBackStack()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun getViewModel() = eventInfoViewModel

}