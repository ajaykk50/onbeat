package www.onbeatapps.com.ui.eventClosed

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tr_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import www.onbeatapps.com.BuildConfig
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.ActivityEventClosedBinding
import www.onbeatapps.com.databinding.SampleActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.utils.Utils
import www.onbeatapps.com.utils.urls
import java.io.File

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class EventClosedActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: ActivityEventClosedBinding
    private val eventViewModel: AuthViewModel by viewModels()
    var STORAGE_PERMISSION = 454
    val appPerms = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    override fun getBinding(): View {
        this?.window?.statusBarColor = resources.getColor(R.color.white)
        this?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding = ActivityEventClosedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getViewModel() = eventViewModel
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun setup(savedInstanceState: Bundle?) {

        setUpclick()
        setUpObserver()
    }

    private fun setUpObserver() {
        eventViewModel.download.observe(this){
            if (!it.isNullOrEmpty()){
                downloadFile(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpclick() {
        binding.apply {
            btInvest.setOnClickListener {
                openUrl(urls.invest)
            }
            btDowload.setOnClickListener {
                requestPermissions(appPerms, STORAGE_PERMISSION);
            }
            btContact.setOnClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "hello@onbeatapp.com")).apply {
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("")) // recipients
                    putExtra(Intent.EXTRA_SUBJECT, "")
                    putExtra(Intent.EXTRA_TEXT, "")
                    startActivity(this)
                }
            }
            btWhatOnbeat.setOnClickListener {
                launchActivity<WhatOnbeatActivity> {
                }
            }
        }
    }

    fun openUrl(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun downloadFile( url : String){
        // fileName -> fileName with extension
        var fileName = "OnBeat${Utils.TimStamp()}.pdf"
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("OnBeat Transaction")
            .setDescription("trList")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        val downloadManager= this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)


        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        val uri = this?.let {
            FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", file)
        }

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val chooser = Intent.createChooser(intent, "Open with")

        startActivity(chooser)
//        downloadFile1(file.toString())
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissiongrant()
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    Toast.makeText(this,"This app needs access to you storage for PDF download",
                        Toast.LENGTH_LONG).show()
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
       if ( eventViewModel.getEventID() != 0)
        eventViewModel.downloadPDF()
        else eventViewModel.showToast("Receipt not available for download, please contact support")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            STORAGE_PERMISSION -> {
                permissiongrant()
            }
        }
    }
    override fun onBackPressed(){
        super.onBackPressed()
        finishAffinity()

    }

}