package www.onbeatapps.com.ui.dashBoardActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.DashBoardActivityBinding
import www.onbeatapps.com.ui.account.DeleteDialog
import www.onbeatapps.com.ui.account.DialogType
import www.onbeatapps.com.ui.authActivity.AuthActivity
import www.onbeatapps.com.ui.base.BaseActivity
import www.onbeatapps.com.ui.base.launchActivity
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import www.onbeatapps.com.ui.splash.SplashActivity
import www.onbeatapps.com.utils.urls


/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class DashBoardActivity : BaseActivity<DashBoardViewModel>(),
    NavigationView.OnNavigationItemSelectedListener {
    private  var navViewSild: NavigationView? = null

    companion object {
        var type = 1
        var naviId = 0
        var getactivity: DashBoardActivity? = null
        private lateinit var binding: DashBoardActivityBinding
        private lateinit var dViewModel:DashBoardViewModel
        var navController: NavController? = null
        var drawer_layout: DrawerLayout? = null
        var nav_Menu: Menu? = null
        @SuppressLint("WrongConstant")
        fun clickEventSlide() {
            if (binding.drawerLayout.isDrawerOpen(Gravity.START)) {
                binding.drawerLayout.closeDrawer(Gravity.START)
            } else {
                binding.drawerLayout.openDrawer(Gravity.START)
            }
        }
        fun exit(){
            getactivity?.finishAffinity()
        }
        fun unlikTicket(){
            dViewModel.logOut()
            getactivity!!.launchActivity<AuthActivity> {}
            getactivity?.finishAffinity()
        }


    }



    private val dashBoardViewModel: DashBoardViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getBinding(): View {
        binding = DashBoardActivityBinding.inflate(layoutInflater)
        this.window.statusBarColor = getColor(R.color.dash_board_top);
        return binding.root
    }

    override fun getViewModel() = dashBoardViewModel
    override fun setup(savedInstanceState: Bundle?) {
        naviId = R.id.nav_dash
        getactivity = this
        dViewModel = dashBoardViewModel
//        navController = findNavController(R.id.nav_dashBoard)
        if (intent.hasExtra("pkb")) {
            if (intent.extras!!.getString("pkb")=="low balance")
                dashBoardViewModel.setLowBalance("low balance")
        }
//        if (intent.extras!!.getInt("type")==0){
            type = intent.extras!!.getInt("type")
//        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_dashBoard) as NavHostFragment
        navController = navHostFragment.navController
        if (dashBoardViewModel.getLowBalance() == "low balance") {
//            if (dashBoardViewModel.getPage()=="home")
            dashBoardViewModel.setLowBalance("")
            navController!!.navigate(
                R.id.navigation_spending_limit,
                bundleOf(HIDE_COUNT to true))
//            else{
//                navController!!.navigate(R.id.nav_account)
//            }
        }else{
            if (type == 0){
                navController!!.navigate(R.id.navigation_splash)
            }
        }

        val navView: NavigationView = binding.navView
        navView.setupWithNavController(navController!!)
        navView?.setNavigationItemSelectedListener(this)
        setUpClick()
        setUpObserver()
    }

    //Side Navigation Click
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_account -> {
                if (naviId!=R.id.nav_account) {
                    navController!!.navigate(R.id.nav_account)
                    naviId = R.id.nav_account
                }
            }
            R.id.nav_dash -> {
                if (naviId!=R.id.nav_dash) {
                navController!!.navigate(R.id.navigation_home)
                    naviId = R.id.nav_dash
                }
            }
//            R.id.nav_event_info -> {
//                if (naviId!=R.id.nav_event_info) {
//                navController!!.navigate(R.id.nav_event_info)
//                    naviId = R.id.nav_event_info
//                }
//            }
            R.id.nav_contact -> {
                if (naviId!=R.id.nav_contact) {
//                    navController!!.navigate(R.id.nav_contact)
//                    naviId = R.id.nav_contact

                    Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "hello@onbeatapp.com")).apply {
                        // The intent does not have a URI, so declare the "text/plain" MIME type
//                    type = "text/plain"
//                    addCategory(Intent.CATEGORY_APP_EMAIL);
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("")) // recipients
                        putExtra(Intent.EXTRA_SUBJECT, "")
                        putExtra(Intent.EXTRA_TEXT, "")
                        startActivity(this)
                    }
                }
            }
            R.id.nav_terms -> {
            if (naviId!=R.id.nav_terms) {
                navController!!.navigate(R.id.nav_terms, bundleOf("menu_hide" to true , "page" to "terms"))
                naviId = R.id.nav_terms
            }
            }
//            R.id.nav_invest -> {
//                openUrl(urls.invest)
////            if (naviId!=R.id.nav_invest) {
////                navController!!.navigate(R.id.nav_terms, bundleOf("menu_hide" to true , "page" to "invest"))
////                naviId = R.id.nav_invest
////            }
//            }
            R.id.navigation_map -> {
                if (naviId!=R.id.navigation_map) {
                    naviId = R.id.navigation_map
                    navController!!.navigate(R.id.navigation_map)
                }
            }
            R.id.navigation_line_up -> {
                if (naviId!=R.id.navigation_line_up) {
                    naviId = R.id.navigation_line_up
                    navController!!.navigate(R.id.navigation_line_up)
                }
            }
            R.id.navigation_artist -> {
                if (naviId!=R.id.navigation_artist) {
                    naviId = R.id.navigation_artist
                    navController!!.navigate(R.id.navigation_artist)
                }
            }
        }

        clickEventSlide()
        return true
    }

    fun openUrl(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_dashBoard)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setUpObserver() {

    }
    fun deletCardDialog() {
        DeleteDialog.newInstance(DialogType.LOG_OUT).also {
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
            it.addSuccessListerner { type ->
                if (type == "logOut") {
//                    dViewModel.logOut()
                    getactivity!!.launchActivity<SplashActivity> {
                        putExtra("type", 1)
                    }
                }

            }
        }
    }
    private fun setUpClick() {
        binding.apply {
            btClose.setOnClickListener {
                clickEventSlide()
            }
            btLogOut.setOnClickListener {
                deletCardDialog()
            }
            investBt.setOnClickListener {
                openUrl(urls.invest)
            }
        }
    }


}