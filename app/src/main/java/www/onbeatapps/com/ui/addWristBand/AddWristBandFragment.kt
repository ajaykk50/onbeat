package www.onbeatapps.com.ui.addWristBand

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentAddBandBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import www.onbeatapps.com.ui.homeFragment.PAGE_BACK

@AndroidEntryPoint
class AddWristBandFragment : BaseFragment<AddwristBandViewModel>() {
    private var _binding: FragmentAddBandBinding? = null
    private val binding get() = _binding!!
    private val addBandViewModel: AddwristBandViewModel by viewModels()
    var code = ""
    val CAMERA_PERMISSION = 124
    var hideCount = false
    var backPage = false

    override fun setup() {

        hideCount = requireArguments().getBoolean(HIDE_COUNT,false)
        backPage = requireArguments().getBoolean(PAGE_BACK,false)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("code")?.observe(
            viewLifecycleOwner) { result ->
            code = result
            addBandViewModel.addWristBand(result)
//            binding.editcode.setText(result)
        }
        setUpClick()
        setUpObserver()
    }

    private fun setUpObserver() {
        binding.apply {
            if (hideCount) {
                toopBar.visibility = View.GONE
                btSkip.visibility = View.GONE
//                btback.visibility = View.VISIBLE
            }
            else{
                btSkip.visibility = View.VISIBLE
                toopBar.visibility = View.VISIBLE
//                btback.visibility = View.INVISIBLE
            }
        }
        addBandViewModel.qrcode.observe(viewLifecycleOwner) {
            code = it
        }
        addBandViewModel.bandStatus.observe(viewLifecycleOwner){
            if (it){
                if (!backPage)
                findNavController().navigate(
                    R.id.navigation_spending_limit,
                    bundleOf(HIDE_COUNT to hideCount)
                )
                else findNavController().popBackStack()
            }
        }
    }

    private fun setUpClick() {
        binding.apply {
           btNext.setOnClickListener {
//               findNavController().navigate(
//                   R.id.navigation_spending_limit,
//                   bundleOf(HIDE_COUNT to hideCount)
//               )
               editcode.error = null
               if(editcode.text.toString()==""){
                   editcode.error = getString(R.string.add_band_continue)
//                   Toast.makeText(mContext,"Add wrist band",Toast.LENGTH_SHORT).show()
               }else {
                   addBandViewModel.addWristBand(editcode.text.toString())

               }
           }
            btScanQr.setOnClickListener {
                requestPermissions()
//                findNavController().navigate(R.id.navigation_qr_scan)
            }
            btSkip.setOnClickListener {
                baseActivity.launchActivity<DashBoardActivity> {
                    putExtra("type",1)
                }
            }
            btback.setOnClickListener {
                findNavController().popBackStack()
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
        _binding = FragmentAddBandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = addBandViewModel

    override fun onResume() {
        super.onResume()
//        Toast.makeText(mContext,code,Toast.LENGTH_SHORT).show()

    }

}