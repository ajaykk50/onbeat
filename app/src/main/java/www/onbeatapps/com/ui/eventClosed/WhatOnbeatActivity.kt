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
import www.onbeatapps.com.databinding.FragmentAboutBinding
import www.onbeatapps.com.databinding.SampleActivityBinding
import www.onbeatapps.com.ui.authActivity.AuthViewModel
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.login_register.PAGE_TYPE
import www.onbeatapps.com.utils.Utils
import www.onbeatapps.com.utils.urls
import java.io.File

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class WhatOnbeatActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: FragmentAboutBinding
    private val eventViewModel: AuthViewModel by viewModels()
    var STORAGE_PERMISSION = 454
    val appPerms = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    override fun getBinding(): View {
        this?.window?.statusBarColor = resources.getColor(R.color.white)
        this?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding = FragmentAboutBinding.inflate(layoutInflater)
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
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpclick() {
        binding.apply {
            txtTitle.text = getString(R.string.what_is_on)
            txtContent.text = getString(R.string.content_what_onbeat)
            btBack.setOnClickListener {
                finish()
            }

        }
    }







    override fun onBackPressed(){
        super.onBackPressed()
        finish()

    }

}