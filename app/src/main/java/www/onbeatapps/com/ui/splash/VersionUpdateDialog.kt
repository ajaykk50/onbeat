package www.onbeatapps.com.ui.splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.databinding.VersionUpdateDialogBinding
import www.onbeatapps.com.ui.base.BaseDialogFragment

@AndroidEntryPoint
class VersionUpdateDialog : BaseDialogFragment() {

    private var _binding: VersionUpdateDialogBinding? = null
    private val binding get() = _binding!!

    override fun setup() {
        bindUI()
        setupClickListerner()
    }

    private fun setupClickListerner() {

        binding.apply {
            btUpdate?.setOnClickListener {
                openApplication()
            }

            btCancel?.setOnClickListener {
                requireActivity()?.finishAffinity()
            }
        }

    }

    private fun openApplication() {
        var packageName = requireActivity()?.getPackageName()
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun bindUI() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = VersionUpdateDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): VersionUpdateDialog {
            return VersionUpdateDialog()
        }
    }
}