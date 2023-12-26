package www.onbeatapps.com.ui.eventInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentEventInfoBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.utils.urls


@AndroidEntryPoint
class EventInfoFragment : BaseFragment<EventInfoViewModel>() {
    private var _binding: FragmentEventInfoBinding? = null
    private val binding get() = _binding!!
    private val eventInfoViewModel: EventInfoViewModel by viewModels()

    override fun setup() {
        DashBoardActivity.naviId =  R.id.nav_event_info
        setUpClick()
        setUpObserver()
//        eventInfoViewModel.eventInfo()
//        Glide.with(mContext).load(BuildConfig.EVENT_IMAGE).into(binding.eventImg)
    }

    private fun setUpObserver() {
        eventInfoViewModel.eventDt.observe(viewLifecycleOwner){
            binding.apply {

            }
        }
    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                DashBoardActivity.clickEventSlide()
            }
            btSpotiFy.setOnClickListener {
                openUrl(urls.spotiFy)
            }
            btSoundCloud.setOnClickListener {
                openUrl(urls.soundCloud)
            }
            btWeb.setOnClickListener {
                openUrl(urls.web)
            }
        }
    }

    fun openUrl(url:String){
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } catch (e: Exception) {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentEventInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun getViewModel() = eventInfoViewModel

}