package www.onbeatapps.com.ui.ticketScan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentAddBandBinding
import www.onbeatapps.com.databinding.FragmentScanTicketBinding
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import www.onbeatapps.com.ui.homeFragment.PAGE_BACK
import www.onbeatapps.com.ui.login_register.LoginRegisterFragment
import www.onbeatapps.com.ui.login_register.PAGE_TYPE

@AndroidEntryPoint
class TicketScanFragment : BaseFragment<TicketViewModel>() {
    private var _binding: FragmentScanTicketBinding? = null
    private val binding get() = _binding!!
    private val ticketScanViewModel: TicketViewModel by viewModels()
    var code = ""
    val CAMERA_PERMISSION = 124
    var pageType = ""

    override fun setup() {
        pageType = requireArguments().getString(PAGE_TYPE,"login")
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("code")?.observe(
            viewLifecycleOwner) { result ->
            code = result
            ticketScanViewModel.scanTicket(result)
//            binding.editcode.setText(result)
        }
        setUpClick()
        setUpObserver()
    }

    private fun setUpObserver() {
        ticketScanViewModel.qrcode.observe(viewLifecycleOwner) {
            code = it
        }
        ticketScanViewModel.scanStatus.observe(viewLifecycleOwner){
            if (it=="Register"){
                findNavController().navigate(R.id.navigation_login_phone, bundleOf(PAGE_TYPE to "login"))
                ticketScanViewModel.scanStatus.value = ""
            }else if (it=="login"){
                findNavController().navigate(R.id.navigation_login, bundleOf(PAGE_TYPE to "splash"))
                ticketScanViewModel.scanStatus.value = ""
            }
        }

    }

    private fun setUpClick() {
        binding.apply {
           btNext.setOnClickListener {

               editcode.error = null
               if(editcode.text.toString()==""){
                   editcode.error = getString(R.string.ticket_band_continue)
//                   Toast.makeText(mContext,"Add wrist band",Toast.LENGTH_SHORT).show()
               }else {
                   ticketScanViewModel.scanTicket(editcode.text.toString())

               }
           }
            btScanQr.setOnClickListener {
                requestPermissions()
//                findNavController().navigate(R.id.navigation_qr_scan)
            }
            btWhatOn.setOnClickListener {
                findNavController().navigate(R.id.navigation_abou, bundleOf(PAGE_TYPE  to "scan"))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            signature()
            permissiongrant()
        }else requestPermissions()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun requestPermissions() {

        val perms = arrayOf(
            Manifest.permission.CAMERA
            )

        if (EasyPermissions.hasPermissions(this.mContext, *perms)) {
//            signature()
            permissiongrant()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your Camera ",
                CAMERA_PERMISSION,
                *perms
            )
        }
    }

    private fun permissiongrant() {
        findNavController().navigate(R.id.navigation_qr_scan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you
        when (requestCode) {
            CAMERA_PERMISSION -> {
                permissiongrant()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        _binding = FragmentScanTicketBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            AuthActivity.exit()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = ticketScanViewModel

}