package www.onbeatapps.com.ui.artist

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.ArtistDetailsRESPONSE
import www.onbeatapps.com.databinding.FragmentArtistInfoBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.lineUp.LineUpAdapter
import www.onbeatapps.com.ui.lineUp.LineUpViewModel
import www.onbeatapps.com.utils.Utils.Companion.artisInfoConvert
import www.onbeatapps.com.utils.Utils.Companion.convetDateToTime

@AndroidEntryPoint
class ArtistInfoFragment : BaseFragment<LineUpViewModel>() {
    private var _binding: FragmentArtistInfoBinding? = null
    private val binding get() = _binding!!
    private val lineUpViewModel: LineUpViewModel by viewModels()
    private var dateAdapter: ArtistDateAdapter? = null
    var artisId = ""
    var spotify = ""
    var bandcamp = ""
    var other = ""
    var SoundCloud = ""

    override fun setup() {
        DashBoardActivity.naviId =  R.id.navigation_artist_info
        artisId = requireArguments().getString("artistId","")
        setUpClick()
        setUpObser()
        setUpRv()
        lineUpViewModel.getArtistDetails(artisId)
    }

    private fun setUpRv() {
        binding.apply {
            dateAdapter = ArtistDateAdapter(mContext){

            }
            recyTime.apply {
                setHasFixedSize(true)
                adapter = dateAdapter
            }
        }
    }

    private fun setUpObser() {
//        lineUpViewModel?.details?.observe(viewLifecycleOwner){
//            if(it) bindData(lineUpViewModel?.artistDetails?.value!![0])
//        }

        lineUpViewModel?.artistDetails?.observe(viewLifecycleOwner){
            if (it.isNotEmpty())  bindData(it[0])
        }
    }

    private fun bindData(artistDt: ArtistDetailsRESPONSE.Data) {
        binding.apply {

            lldetails.visibility = View.VISIBLE
            tvName.text = artistDt!!.artistName
            tvType.text = artistDt.genre
            dateAdapter!!.setListContent(artistDt.performance)
            tvLive.isVisible = artistDt.live
//            tvTime.text = "${artisInfoConvert(artistDt!!.startDate)}, ${convetDateToTime(artistDt!!.startDate)}\n${artisInfoConvert(artistDt!!.endDate)}, ${convetDateToTime(artistDt!!.endDate)}"

            tvDis.text = artistDt.description

            if (artistDt.spotify.isNotEmpty()) {
                btSpotify.visibility = View.VISIBLE
                spotify = artistDt.spotify
            }else{
                btSpotify.visibility = View.GONE
                spotify = ""
            }
            if (artistDt.soundCloud.isNotEmpty()) {
                btSoundCloud.visibility = View.VISIBLE
                SoundCloud = artistDt.soundCloud
            }else{
                btSoundCloud.visibility = View.GONE
                SoundCloud = ""
            }
            if (artistDt.bandcamp.isNotEmpty()) {
                btBandcamp.visibility = View.VISIBLE
                bandcamp = artistDt.bandcamp
            }else{
                btBandcamp.visibility = View.GONE
                bandcamp = ""
            }
            if (artistDt.other.isNotEmpty()) {
                btOther.visibility = View.VISIBLE
                other = artistDt.other
            }else{
                btOther.visibility = View.GONE
                other = ""
            }
            Glide.with(mContext).load(artistDt.image).placeholder(R.drawable.ic_user).into(eventImg)
        }

    }

    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
               popBack()
            }
            btSpotify.setOnClickListener {
                openUrl(spotify)
            }
            btOther.setOnClickListener {
                openUrl(other)
            }
            btSoundCloud.setOnClickListener {
                openUrl(SoundCloud)
            }
            btBandcamp.setOnClickListener {
                openUrl(bandcamp)
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
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        _binding = FragmentArtistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        lineUpViewModel?.details?.value = false
    }

    override fun getViewModel() = lineUpViewModel

}