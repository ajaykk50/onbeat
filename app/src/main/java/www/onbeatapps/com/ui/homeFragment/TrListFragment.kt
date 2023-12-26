package www.onbeatapps.com.ui.homeFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentTrListBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.utils.NotificationEvent
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode
import www.onbeatapps.com.utils.Utils.Companion.TimStamp
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


@AndroidEntryPoint
class TrListFragment : BaseFragment<HomeViewModel>() {
    private var _binding: FragmentTrListBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null
    var writBandBalance = ""
    var STORAGE_PERMISSION = 454
    val appPerms = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun setup() {

        setUpRV()
        setUpClick()
        setUpObserver()
        homeViewModel.getTransationList()
    }

    private fun setUpRV() {
        homeAdapter = HomeAdapter(mContext,homeViewModel)
        binding.recyTrans.apply {
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun setUpObserver() {
        binding.apply {
            homeViewModel.trList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    homeAdapter?.setListContent(it)
                    txtTotalSpend.text = "${homeViewModel.currency} ${NumberFormatForCurrencyCode(homeViewModel.total_spent)}"
                }else {
                    txtTotalSpend.text = "${homeViewModel.currency} ${NumberFormatForCurrencyCode("00.00")}"
                    noDatHide()
                }
            }

            homeViewModel.pdfUrl.observe(viewLifecycleOwner){
//                downloadFile(it)
                downloadFile(it)
            }
        }

    }

    fun noDatHide() {
        binding.apply {
            txtNodat.visibility = View.VISIBLE
//            containTr.visibility = View.GONE
        }
    }
    private fun setUpClick() {
        binding.apply {
            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
            swipeRefresh.setOnRefreshListener {
                homeViewModel.getTransationList()
                swipeRefresh.isRefreshing = false
            }
            btDowload.setOnClickListener {
                requestPermissions(appPerms, STORAGE_PERMISSION);
//                requestPermissions()

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        _binding = FragmentTrListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun downloadFile( url : String){
        // fileName -> fileName with extension
        var fileName = "OnBeat${TimStamp()}.pdf"
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("OnBeat Transaction")
            .setDescription("trList")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        val downloadManager= mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)


        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        val uri = context?.let {
            FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", file)
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val chooser = Intent.createChooser(intent, "Open with")

            startActivity(chooser)
//        downloadFile1(file.toString())
    }

    fun downloadFile1(urls:  String) {
        mContext.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urls)
            )
        )
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = homeViewModel

        @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NotificationEvent) {
        if (event == NotificationEvent.Reload) {
            homeViewModel.getUserDt()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            STORAGE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissiongrant()
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    Toast.makeText(mContext,"This app needs access to you storage for PDF download",Toast.LENGTH_LONG).show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    private fun permissiongrant() {
//        findNavController().navigate(R.id.navigation_qr_scan)
        homeViewModel.downloadTransationList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you
        when (requestCode) {
            STORAGE_PERMISSION -> {
                permissiongrant()
            }
        }
    }

}