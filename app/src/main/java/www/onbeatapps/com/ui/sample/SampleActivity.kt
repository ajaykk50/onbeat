package www.onbeatapps.com.ui.sample

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.SampleActivityBinding
import www.onbeatapps.com.ui.base.BaseActivity

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@AndroidEntryPoint
class SampleActivity : BaseActivity<ActivityViewModel>() {

    private lateinit var binding: SampleActivityBinding
    private val activityViewModel: ActivityViewModel by viewModels()
    override fun getBinding(): View {
        binding = SampleActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getViewModel() = activityViewModel
    override fun setup(savedInstanceState: Bundle?) {
    }

}