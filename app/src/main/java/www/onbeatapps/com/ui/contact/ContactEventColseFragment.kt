package www.onbeatapps.com.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentContactBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity.Companion.clickEventSlide


@AndroidEntryPoint
class ContactEventColseFragment : BaseFragment<ContactViewModel>() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()

    override fun setup() {
        DashBoardActivity.naviId =  R.id.nav_contact
        setUpClick()

    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                clickEventSlide()
            }
            txtemail.setOnClickListener {
                Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"hello@onbeatapp.com")).apply {
                    // The intent does not have a URI, so declare the "text/plain" MIME type
//                    type = "text/plain"
//                    addCategory(Intent.CATEGORY_APP_EMAIL);
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("")) // recipients
                    putExtra(Intent.EXTRA_SUBJECT, "")
                    putExtra(Intent.EXTRA_TEXT, "")
                    startActivity(this)
                    // You can also attach multiple items by passing an ArrayList of Uris
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = contactViewModel

}