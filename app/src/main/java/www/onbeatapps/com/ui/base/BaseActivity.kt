package www.onbeatapps.com.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import www.onbeatapps.com.R
import www.onbeatapps.com.ui.splash.SplashActivity
import www.onbeatapps.com.utils.network.NoInternetException


/**
 * Created by PKB on 03-05-2021.
 * PKB@gmail.com
 */
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(), CommonUtils {

    private lateinit var mViewModel: T

    abstract fun getViewModel(): T

    private val loadingDialog = LoadingDialog.newInstance()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getBinding())
        mViewModel = getViewModel()
        setUpLoadingObservers()
        setup(savedInstanceState)
    }

    private fun setUpLoadingObservers() {
        mViewModel.loading.observe(this, {
            if (it) showLoading()
            else hideLoading()
        })
        mViewModel.exceptionEvent.observe(this, {
            when (it) {
                is ExceptionEvent.Throw -> {
                    if (it.errorCode == UNAUTHORIZED) goToLogin()
                    else handleError(it.errorBody, it.throwable)
                    mViewModel.settleExceptionEvent()
                }
                ExceptionEvent.Finish -> {
                }
            }.exhaustive
        })
        mViewModel.toastEvent.observe(this, {
            when (it) {
                is Event.Data -> {
                    toast(it.id)
                    mViewModel.settleToastEvent()
                }
                Event.Finish -> {
                }
            }.exhaustive
        })
    }



    fun goToLogin() {
        if (this.javaClass.simpleName != SplashActivity::class.java.simpleName) {
            launchActivity<SplashActivity> { }
            finish()
        }
    }

    fun hideLoading() {
        if (isLoading) {
            loadingDialog.dismiss()
            isLoading = false
        }
    }

    fun showLoading() {
        if (!isLoading) {
            loadingDialog.show(supportFragmentManager, loadingDialog.tag)
            isLoading = true
        }
    }

    abstract fun getBinding(): View
    abstract fun setup(savedInstanceState: Bundle?)

    fun handleError(body: www.onbeatapps.com.data.ErrorBody?, throwable: Throwable?) {
        body?.message?.let { message ->
            if (message.isNotBlank())
                toast(message)
        } ?: kotlin.run {
            throwable?.let { t ->
                if (t is NoInternetException) {
                    toast(t.message)
                } else toast(R.string.common_text_api_failure_message)
            }
        }
    }

    override fun onInternetConnectionFailure() =
        toast(R.string.common_text_no_internet_connection_message)

    override fun onApiFailure() = toast(R.string.common_text_api_failure_message)

//    fun clearAllTask() {
//        val i = Intent(this, MainActivity::class.java)
//        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(i)
//    }
}