package www.onbeatapps.com.ui.account

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.addCallback
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_email_edit.*
import kotlinx.android.synthetic.main.fragment_phone_edit.*
import org.greenrobot.eventbus.EventBus
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.UserDetailsRESPONSE
import www.onbeatapps.com.databinding.FragmentAccountBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity.Companion.clickEventSlide
import www.onbeatapps.com.ui.homeFragment.HIDE_COUNT
import java.util.concurrent.Executor


@AndroidEntryPoint
class AccountFragment : BaseFragment<AccountViewModel>() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val accountViewModel: AccountViewModel by viewModels()

    override fun setup() {
        DashBoardActivity.naviId = R.id.nav_account
        keyboardOkClick()
        setUpClick()
        setUpObserver()
        accountViewModel.setPage("home")
//        chancekfaceID()
    }

    private fun setUpObserver() {
        accountViewModel.updateStatus.observe(viewLifecycleOwner){
            if (it){
                binding.apply {
                    when (accountViewModel.editType) {
                        1 -> {
                            if (txtUserName.text.toString() == "")
                                accountViewModel.showToast("Name removed successfully")
                            else
                            accountViewModel.showToast("Name saved successfully")
                        }
                        2 -> {
                            if (txtEmail.text.toString() == "")
                                accountViewModel.showToast("Email removed successfully")
                            else
                                accountViewModel.showToast("Email saved successfully")
                        }
                        3 -> {
                            if (txtPhone.text.toString() == "")
                                accountViewModel.showToast("Phone number removed successfully")
                            else
                                accountViewModel.showToast("Phone number saved successfully")
                        }
                    }
                    accountViewModel.updateStatus.value = false
                }
            }
        }
        accountViewModel.emailStatus.observe(viewLifecycleOwner) { it ->
            if (it) {
                binding.txtEmail.setText("")
                Toast.makeText(
                    mContext,
                    "Email entered is already registered with us! Try again with a different email.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        accountViewModel.deleteCall.observe(viewLifecycleOwner) {
            if (it) {
//                if (accountViewModel.callType == 0)
//                accountViewModel.cardDelet(0)
//                else findNavController().navigate(R.id.navigation_save_card)
                accountViewModel.cardDelet(accountViewModel.callType)
                accountViewModel.deleteCall.value = false
            }
        }
        accountViewModel.editStatus.observe(viewLifecycleOwner) {
            accountViewModel.getUserDt()
        }
        accountViewModel.userDt.observe(viewLifecycleOwner) {
            updatData(it)
        }
        accountViewModel.settilCall.observe(viewLifecycleOwner) {
            if (it) {
                accountViewModel.settle(accountViewModel.callType)
                accountViewModel.settilCall.value = false
            }
        }
        accountViewModel.userDtCall.observe(viewLifecycleOwner) {
            if (it) {
                if (accountViewModel.callType == 0)
                    accountViewModel.getUserDt()
                else findNavController().navigate(R.id.navigation_save_card)
                accountViewModel.userDtCall.value = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatData(userDetailsRESPONSE: UserDetailsRESPONSE) {
        binding.apply {
            if (userDetailsRESPONSE.card.isNotEmpty()) {
                txtCardText.gravity = Gravity.START or Gravity.CENTER
                txtCardText.setTextColor(Color.parseColor("#000000"))
                txtCardText.text =
                    "${userDetailsRESPONSE.card[0].cardType} **** **** **** ${userDetailsRESPONSE.card[0].lastFour}"
                btCard.setBackgroundResource(R.drawable.bg_hash_box_fill)
                btAddNewCard.visibility = View.VISIBLE
                accountViewModel.cardStatus(true)
                btDelet.visibility = View.VISIBLE
            } else {
                txtCardText.setTextColor(Color.parseColor("#FFFFFF"))
                txtCardText.gravity = Gravity.CENTER
                btCard.setBackgroundResource(R.drawable.bg_gray_button)
                txtCardText.text = resources.getString(R.string.add_card1)
                btAddNewCard.visibility = View.GONE
                accountViewModel.cardStatus(false)
                btDelet.visibility = View.GONE
            }
            if (userDetailsRESPONSE.firstName != null && userDetailsRESPONSE.firstName != "")
                txtUserName.setText(
                    "${userDetailsRESPONSE.firstName} ${userDetailsRESPONSE.lastName}"
                )
            if (userDetailsRESPONSE.phone != null)
                txtPhone.setText("${userDetailsRESPONSE.phone}")
            if (userDetailsRESPONSE.email != null)
                txtEmail.setText("${userDetailsRESPONSE.email}")
//            btPhone.isVisible = accountViewModel.getLoginType() != 1
            txtPhone.isEnabled = accountViewModel.regType == "social media"
            txtEmail.isEnabled = accountViewModel.regType == "phone"

            if (accountViewModel.gTagList.isNotEmpty()
            ) {
                tvTicketNumber.text = accountViewModel.getTicket()
                tvTicketType.text = accountViewModel.ticketType
                if (accountViewModel.getBansStatus().isNotEmpty())
                    containBand.visibility = View.VISIBLE
                else containBand.visibility = View.GONE
                contTicket.visibility = View.VISIBLE
                btTicket.visibility = View.GONE
                tvBandNumber.text = accountViewModel.getBand()
            } else {
                containBand.visibility = View.GONE
                contTicket.visibility = View.VISIBLE
                tvTicketNumber.text = accountViewModel.getTicket()
                tvTicketType.text = accountViewModel.ticketType
            }

            if (accountViewModel.getTouch()) switTouchId.isChecked = true
        }
    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                clickEventSlide()
            }
            btEditName.setOnClickListener {
                var name: List<String>? = null
                if (txtUserName.text.toString() != "") {
                    name = txtUserName.text.toString().split(" ")
                    findNavController().navigate(
                        R.id.nav_edit_name,
                        bundleOf("f_name" to name[0], "l_name" to name[1])
                    )
                } else findNavController().navigate(R.id.nav_edit_name)

            }
            btEditEmail.setOnClickListener {
                if (txtUserName.text.toString() != "")
                    findNavController().navigate(
                        R.id.nav_edit_email,
                        bundleOf("email" to txtEmail.text.toString())
                    )
                else findNavController().navigate(R.id.nav_edit_email)
            }
            btPhone.setOnClickListener {
                findNavController().navigate(
                    R.id.nav_edit_phone,
                    bundleOf("phone" to txtPhone.text.toString())
                )
            }
            btBandClick.setOnClickListener {
//                if (accountViewModel.getBansStatus() != null && accountViewModel.getBansStatus()
//                        .isNotEmpty()
//                ) {
                findNavController().navigate(R.id.nav_lost_and)
//                } else {
//                    findNavController().navigate(
//                        R.id.navigation_add_wrist_band, bundleOf(
//                            HIDE_COUNT to true, PAGE_BACK to true
//                        )
//                    )
//                }
            }
            btTicket.setOnClickListener {
                findNavController().navigate(R.id.action_nav_account_to_navigation_unlink)
//                accountViewModel.unlinkTicket()
            }
            btCard.setOnClickListener {
                if (!accountViewModel.getCardStatus()) {
                    findNavController().navigate(R.id.navigation_save_card)
                }
            }
            btAddNewCard.setOnClickListener {
                deletCardDialog(DialogType.REPLACE)

            }
            btPassword.setOnClickListener {
                findNavController().navigate(R.id.nav_edit_pin)
            }
            btDelet.setOnClickListener {
                deletCardDialog(DialogType.DELETE)
            }
            switTouchId.setOnCheckedChangeListener { buttonView, isChecked ->
                if (accountViewModel.getTouchSetUP()) {
                    accountViewModel.setTouch(isChecked)
                } else {
                    AuthFacIDCheck(isChecked)
                }
            }


        }
    }

    fun keyboardOkClick() {
        binding.apply {
            txtUserName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                v.clearFocus()
                var names = txtUserName.text.toString().split(" ")
                var secondName = ""
                    for (i in names.indices) {
                        if (i != 0) {
                            secondName += " ${names[i]}"
                        }
                    }
                if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    val paramObject = JsonObject()
                    paramObject.addProperty("first_name", names[0].trim())
                    paramObject.addProperty("last_name", secondName.trim())
                    accountViewModel.editData(paramObject,1)
                }
                false
            })
            txtEmail.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                v.clearFocus()
                if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (txtEmail.text.toString()!="") {
                        if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.text.toString()).matches()) {
                            val paramObject = JsonObject()
                            paramObject.addProperty("email", txtEmail.text.toString())
                            accountViewModel.editData(paramObject,2)
                        } else {
                            Toast.makeText(mContext, "Enter valid email", Toast.LENGTH_SHORT).show()
                            txtEmail.setText("")
                        }
                    }else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("email", "")
                        accountViewModel.editData(paramObject,2)
                    }
                }
                false
            })
            txtPhone.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                v.clearFocus()
                if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (txtPhone.text.toString()!="") {
                        if (txtPhone.text.toString().length>=10) {
                            val paramObject = JsonObject()
                            paramObject.addProperty("phone", txtPhone.text.toString())
                            accountViewModel.editData(paramObject, 3)
                        }else accountViewModel.showToast("Enter valid phone number")
                    }else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("phone", txtPhone.text.toString())
                        accountViewModel.editData(paramObject, 3)
                    }
                }
                false
            })

            // editText FocusChange Update
            txtUserName.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (txtUserName.text.toString() != "") {
//                        val paramObject = JsonObject()
//                        paramObject.addProperty("first_name", txtUserName.text.toString())
//                        accountViewModel.editData(paramObject)

                        var names = txtUserName.text.toString().split(" ")
                        var secondName = ""
                        for (i in names.indices) {
                            if (i != 0) {
                                secondName += " ${names[i]}"
                            }
                        }
                            val paramObject = JsonObject()
                            paramObject.addProperty("first_name", names[0].trim())
                            paramObject.addProperty("last_name", secondName.trim())
                            accountViewModel.editData(paramObject,1)
                    }
                }
            }
            txtEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (txtEmail.text.toString()!="") {
                        if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.text.toString()).matches()) {
                            val paramObject = JsonObject()
                            paramObject.addProperty("email", txtEmail.text.toString())
                            accountViewModel.editData(paramObject,2)
                        } else {
                            Toast.makeText(mContext, "Enter valid email", Toast.LENGTH_SHORT).show()
                            txtEmail.setText("")
                        }
                    }else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("email", "")
                        accountViewModel.editData(paramObject,2)
                    }
                }
            }
            txtPhone.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (txtPhone.text.toString()!="") {
                        if (txtPhone.text.toString().length>=10) {
                            val paramObject = JsonObject()
                            paramObject.addProperty("phone", txtPhone.text.toString())
                            accountViewModel.editData(paramObject, 3)
                        }else accountViewModel.showToast("Enter valid phone number")
                    }else {
                        val paramObject = JsonObject()
                        paramObject.addProperty("phone", txtPhone.text.toString())
                        accountViewModel.editData(paramObject, 3)
                    }
                }
            }
        }

    }

    fun chancekfaceID() {
        val biometricManager = BiometricManager.from(mContext)
        when (biometricManager!!.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Handler().postDelayed({
                    binding.switTouchId.visibility = View.VISIBLE
                }, 800)
            }
            // App can authenticate using biometrics
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                binding.switTouchId.visibility = View.GONE
            }
            // No biometric features available on this device
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                binding.switTouchId.visibility = View.GONE
            }
            // Biometric features are currently unavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                binding.switTouchId.visibility = View.GONE
            }
        }
    }

    private fun AuthFacIDCheck(status: Boolean) {

        executor = ContextCompat.getMainExecutor(mContext)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        mContext,
                        "Authentication error",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication error
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    accountViewModel.setUpFacId()
                    accountViewModel.setTouch(status)

                    Toast.makeText(
                        mContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication succeeded!
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        mContext,
                        "Authentication failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Authentication failed
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate with TouchId")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(true)
            .build()
        biometricPrompt.authenticate(promptInfo)

    }

    fun deletCardDialog(DialogType: DialogType) {
        DeleteDialog.newInstance(DialogType).also {
            it.isCancelable = false
            it.show(childFragmentManager, it.tag)
            it.addSuccessListerner { type ->
                if (type == "delete") accountViewModel.cardDelet(0)
                else if (type == "proceed") accountViewModel.cardDelet(1)
                else if (type == "unlink") accountViewModel.unlinkTicket()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.navigation_home)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        accountViewModel.getUserDt()
    }

    override fun getViewModel() = accountViewModel

}