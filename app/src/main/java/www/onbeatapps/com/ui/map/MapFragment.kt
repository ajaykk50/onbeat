package www.onbeatapps.com.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentMapsBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HomeViewModel
import java.util.*


@AndroidEntryPoint
class MapFragment : BaseFragment<HomeViewModel>() {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel: HomeViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->

        val Nave = LatLng(39.70868, 2.59637)
        googleMap.addMarker(MarkerOptions().position(Nave).title("Vistamar: Diseminado Vistamar, 1\n 07170 Valldemossa, Illes Balears\n Spain"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Nave))


        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    39.70868, 2.59637
                ), 13.0f
            )
        )
    }

    override fun setup() {
        DashBoardActivity.naviId = R.id.navigation_map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        setupClick()
    }

    private fun setupClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                DashBoardActivity.clickEventSlide()
            }
            btFirst.setOnClickListener {
                openMap(39.70868, 2.59637)
            }

        }
    }

    fun openMap(latitude: Double, longitude: Double) {
        val uri = java.lang.String.format(
            Locale.ENGLISH,
            "http://maps.google.com/maps?q=loc:%f,%f",
            latitude,
            longitude
        )
//        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        mContext.startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = mapViewModel

}