package www.onbeatapps.com.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Created by PKB on 03-06-2021.
 * PKB@gmail.com
 */
const val UNAUTHORIZED = 401

abstract class BaseFragment<T : BaseViewModel> : Fragment(), CommonUtils {

    lateinit var baseActivity: BaseActivity<*>
    lateinit var mContext: Context
    private lateinit var mViewModel: T

    abstract fun getViewModel(): T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = getViewModel()
        setUpLoadingObservers()
        setup()
    }

    abstract fun setup()

    private fun setUpLoadingObservers() {
        mViewModel.loading.observe(viewLifecycleOwner, {
            if (it)
                baseActivity.showLoading()
            else
                baseActivity.hideLoading()
        })
        mViewModel.exceptionEvent.observe(viewLifecycleOwner, {
            when (it) {
                is ExceptionEvent.Throw -> {
                    if (it.errorCode == UNAUTHORIZED) {
//                        mViewModel.tokenRefresh()
                        baseActivity.goToLogin()
                    }
                    else baseActivity.handleError(it.errorBody, it.throwable)
                    mViewModel.settleExceptionEvent()
                }
                ExceptionEvent.Finish -> {
                }
            }.exhaustive
        })
        mViewModel.toastEvent.observe(viewLifecycleOwner, {
            when (it) {
                is Event.Data -> {
                    toast(it.id)
                    mViewModel.settleToastEvent()
                }
                Event.Finish -> {
                }
            }.exhaustive
        })
        mViewModel.backEvent.observe(viewLifecycleOwner, {
            when (it) {
                is Event.Data -> {
                    mViewModel.settlebackEvent()
                    findNavController().popBackStack()
                }
                Event.Finish -> {
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        baseActivity = context as BaseActivity<*>
    }

    override fun onInternetConnectionFailure() =
        baseActivity.onInternetConnectionFailure()

    override fun onApiFailure() = baseActivity.onApiFailure()
    internal fun popBack() {
        findNavController().popBackStack()
    }

}