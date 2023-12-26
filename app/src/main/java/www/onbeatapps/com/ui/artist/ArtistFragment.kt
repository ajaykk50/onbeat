package www.onbeatapps.com.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentArtistBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.lineUp.LineUpViewModel


@AndroidEntryPoint
class ArtistFragment : BaseFragment<LineUpViewModel>() {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private val artistViewModel: LineUpViewModel by viewModels()
    private var artistAdapter: ArtistAdapter? = null

    override fun setup() {
        DashBoardActivity.naviId =  R.id.navigation_artist
        setUpRV()
        setUpClick()
        setUpObserver()
        artistViewModel.getArtist()
    }


    private fun setUpObserver() {
        artistViewModel.artistList.observe(viewLifecycleOwner){
            artistAdapter!!.setListContent(it)
        }
    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                DashBoardActivity.clickEventSlide()
            }
        }
    }

    private fun setUpRV() {
        val layoutManager = GridLayoutManager(mContext, 2)

        artistAdapter = ArtistAdapter(mContext){
            findNavController().navigate(R.id.navigation_artist_info, bundleOf("artistId" to it.artistCode))
        }
        binding.recyArtiest.apply {
            setLayoutManager(layoutManager)
            setHasFixedSize(true)
            adapter = artistAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = artistViewModel

}