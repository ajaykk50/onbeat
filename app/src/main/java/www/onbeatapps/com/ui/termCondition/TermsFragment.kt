package www.onbeatapps.com.ui.termCondition

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentTermsBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.utils.urls

@AndroidEntryPoint
class TermsFragment : BaseFragment<TermsViewModel>() {
    private var _binding: FragmentTermsBinding? = null
    private val binding get() = _binding!!
    private val termsViewModel: TermsViewModel by viewModels()
    var hideMenu = false
    var page = "terms"
    var url = ""
    companion object{
        var loading :FrameLayout? = null
    }

    override fun setup() {

        loading = binding.frameLoading
        hideMenu = requireArguments().getBoolean("menu_hide", false)
        page = requireArguments().getString("page", "terms")
//        binding.btSideMenu.isVisible = hideMenu


        if (page=="terms") {
            DashBoardActivity.naviId =  R.id.nav_terms
            binding.txtTitle.text = resources.getString(R.string.terms_conditions)
            binding.containToolBar.visibility = View.VISIBLE
            url = urls.terms
            binding.btSideMenu.setImageResource(R.drawable.ic_side_menu_hash)
        }else if (page=="termsLogin"){
            binding.txtTitle.text = resources.getString(R.string.terms_conditions)
            binding.containToolBar.visibility = View.VISIBLE
            url = urls.terms
            binding.btSideMenu.setImageResource(R.drawable.ic_back)
        }
//        else {
//
//            DashBoardActivity.naviId =  R.id.nav_invest
//            binding.txtTitle.text = resources.getString(R.string.invest_onbeat)
//            binding.containToolBar.visibility = View.GONE
//            url = urls.invest
//        }


        setUpClick()
        weViewLoad()
    }

    private fun weViewLoad() {
        binding.apply {
            web.loadUrl(url)
            web.webViewClient = MyWebViewClient()
        }
    }

    private class MyWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            // do your stuff here
            loading?.visibility = View.GONE
        }
    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                 if (page!="termsLogin")
                DashBoardActivity.clickEventSlide()
                else findNavController().popBackStack()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = termsViewModel

}