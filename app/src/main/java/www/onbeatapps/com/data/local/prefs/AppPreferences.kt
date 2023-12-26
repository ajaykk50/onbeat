package www.onbeatapps.com.data.local.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import www.onbeatapps.com.di.PreferenceInfo
import javax.inject.Inject

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
@SuppressLint("HardwareIds")
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context,
    @PreferenceInfo prefFileName: String?
) {

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    companion object {

        private const val KEY_LOW_BALANCE = "balancLow"
        private const val KEY_TEMP_AMOUNT = "temp_amount"
        private const val KEY_PAGES = "pages"
        private const val KEY_PIN_NUMBER = "pin_number"
        private const val KEY_IS_LOGIN = "is_login"
        private const val KEY_WRIST_BAND_STATUS = "wrist_band"
        private const val KEY_WRIST_BAND_BALANCE = "wrist_band_balance"
        private const val KEY_SPEND_LIMIT = "spent_limit"
        private const val KEY_LAST_ADD = "last_add"
        private const val KEY_EVENT_ID = "event_id"
        private const val KEY_EVENT_NAME = "event_name"
        private const val KEY_PHONE = "phone"
        private const val KEY_UUID = "uuid"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_LOGIN_TYPE = "login_type"

        private const val KEY_DEVICE_TYPE = "device_type"
        private const val KEY_TOKEN = "token"
        private const val KEY_PAGE = "page"
        private const val KEY_CLIENT_SECRET = "client_secret"
        private const val KEY_CARD_ADD_STATUS = "card_status"
        private const val KEY_PAYMENT_ID = "payment_id"
        private const val KEY_FIRE_TOKEN = "fire_token"
        private const val KEY_BARRER_TOKEN = "barren_token"
        private const val KEY_FIRSR_NAME = "first_name"

        private const val KEY_ADDED_MONEY = "balance"
        private const val KEY_PER_AUTH_MONEY = "pre_balance"
        private const val KEY_PAYMENT = "payment"
        private const val KEY_TOUCH_ID = "touchId"
        private const val KEY_TOUCH_ID_SET_UP = "touchIdSetUp"
        private const val KEY_TOTAL_SPENT = "total_spend"
        private const val KEY_CURRENCY = "currency"
        private const val KEY_CURRENCY_CODE = "currency_code"
        private const val KEY_TICKET = "ticket"


    }

    init {
    }

    fun getPages(): String {
        return mPrefs.getString(KEY_PAGES, null) ?: ""
    }
     fun setPages(page: String) {
        mPrefs.edit().putString(KEY_PAGES, page).apply()
    }

    fun getLowBalance(): String {
        return mPrefs.getString(KEY_LOW_BALANCE, null) ?: ""
    }
     fun setLowBalance(key: String) {
        mPrefs.edit().putString(KEY_LOW_BALANCE, key).apply()
    }
    fun getTempAmount(): String {
        return mPrefs.getString(KEY_TEMP_AMOUNT, null) ?: ""
    }
     fun setTempAmount(key: String) {
        mPrefs.edit().putString(KEY_TEMP_AMOUNT, key).apply()
    }

    fun getTicket(): String {
        return mPrefs.getString(KEY_TICKET, null) ?: ""
    }

     fun setTicket(name: String) {
        mPrefs.edit().putString(KEY_TICKET, name).apply()
    }
    fun getEventName(): String {
        return mPrefs.getString(KEY_EVENT_NAME, null) ?: ""
    }

     fun setEventName(name: String) {
        mPrefs.edit().putString(KEY_EVENT_NAME, name).apply()
    }
    fun getCurrency(): String {
        return mPrefs.getString(KEY_CURRENCY, null) ?: ""
    }
     fun setCurrency(symbol: String) {
        mPrefs.edit().putString(KEY_CURRENCY, symbol).apply()
    }
    fun getCurrencyCode(): String {
        return mPrefs.getString(KEY_CURRENCY_CODE, null) ?: ""
    }
     fun setCurrencyCode(symbol: String) {
        mPrefs.edit().putString(KEY_CURRENCY_CODE, symbol).apply()
    }
    fun getWristBalance(): String {
        return mPrefs.getString(KEY_WRIST_BAND_BALANCE, null) ?: ""
    }

     fun setWristBalance(amount: String) {
        mPrefs.edit().putString(KEY_WRIST_BAND_BALANCE, amount).apply()
    }
    fun getPinNumber(): String {
        return mPrefs.getString(KEY_PIN_NUMBER, null) ?: ""
    }

     fun setPinNumber(id: String) {
        mPrefs.edit().putString(KEY_PIN_NUMBER, id).apply()
    }

    fun setFirstName(name: String) {
        mPrefs.edit().putString(KEY_FIRSR_NAME, name).apply()
    }

    fun getFirstName(): String {
        return mPrefs.getString(KEY_FIRSR_NAME, null)!!
    }

    fun setPayment(id: String) {
        mPrefs.edit().putString(KEY_PAYMENT, id).apply()
    }

    fun getPayment(): String {
        return mPrefs.getString(KEY_PAYMENT, "pay")!!
    }

    fun setTokeCheck(id: Boolean) {
        mPrefs.edit().putBoolean(KEY_PAGE, id).apply()
    }

    fun getTokeCheck(): Boolean {
        return mPrefs.getBoolean(KEY_PAGE, false)!!
    }

    fun setBalance(id: String) {
        mPrefs.edit().putString(KEY_ADDED_MONEY, id).apply()
    }

    fun getBalance(): String {
        return mPrefs.getString(KEY_ADDED_MONEY, "00.0")!!
    }
    fun setPreauthuMoney(id: String) {
        mPrefs.edit().putString(KEY_PER_AUTH_MONEY, id).apply()
    }

    fun getPreauthuMoney(): String {
        return mPrefs.getString(KEY_PER_AUTH_MONEY, "00.0")!!
    }
    fun setTotalSpent(id: String) {
        mPrefs.edit().putString(KEY_TOTAL_SPENT, id).apply()
    }

    fun getTotalSpent(): String {
        return mPrefs.getString(KEY_TOTAL_SPENT, "00.0")!!
    }

    fun setEventId(id: Int) {
        mPrefs.edit().putInt(KEY_EVENT_ID, id).apply()
    }

    fun getEventId(): Int {
        return mPrefs.getInt(KEY_EVENT_ID, 0)!!
    }

    fun setSpendingLimit(id: String) {
        mPrefs.edit().putString(KEY_SPEND_LIMIT, id).apply()
    }

    fun getSpendingLimit(): String {
        return mPrefs.getString(KEY_SPEND_LIMIT, null)!!
    }
    fun setLastAddAmount(pay: String) {
        mPrefs.edit().putString(KEY_LAST_ADD, pay).apply()
    }

    fun getLastAddAmount(): String {
        return mPrefs.getString(KEY_LAST_ADD, "0.00")!!
    }

    fun setUuId(id: String) {
        mPrefs.edit().putString(KEY_UUID, id).apply()
    }

    fun getUuId(): String {
        return mPrefs.getString(KEY_UUID, null)!!
    }

    fun setLoginType(id: Int) {
        mPrefs.edit().putInt(KEY_LOGIN_TYPE, id).apply()
    }
    // 1 - phone 2 - socialLogin
    fun getLoginType(): Int {
        return mPrefs.getInt(KEY_LOGIN_TYPE, 1)!!
    }

    fun getDeviceType(): String {
        return mPrefs.getString(KEY_DEVICE_TYPE, null)!!
    }

    private fun setDeviceType(type: String) {
        mPrefs.edit().putString(KEY_DEVICE_TYPE, type).apply()
    }

    fun getToken(): String {
        return mPrefs.getString(KEY_TOKEN, null) ?: ""
    }

    fun setToken(token: String) {
        mPrefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun isLogin() = mPrefs.getBoolean(KEY_IS_LOGIN, false)

    fun setLogin(status: Boolean) {
        mPrefs.edit().putBoolean(KEY_IS_LOGIN, status).apply()
    }
    fun getTouchId() = mPrefs.getBoolean(KEY_TOUCH_ID, false)

    fun setTouchId(status: Boolean) {
        mPrefs.edit().putBoolean(KEY_TOUCH_ID, status).apply()
    }
    fun getTouchIdSetUp() = mPrefs.getBoolean(KEY_TOUCH_ID_SET_UP, false)

    fun setTouchIdSetUp(status: Boolean) {
        mPrefs.edit().putBoolean(KEY_TOUCH_ID_SET_UP, status).apply()
    }
    fun setBand(id: String) {
        mPrefs.edit().putString(KEY_WRIST_BAND_STATUS, id).apply()
    }

    fun getBand(): String {
        return mPrefs.getString(KEY_WRIST_BAND_STATUS, null)?: ""
    }
    fun userId(): Int {
        return mPrefs.getInt(KEY_USER_ID, 0) !!
    }

    fun userId(id: Int) {
        mPrefs.edit().putInt(KEY_USER_ID, id).apply()
    }

    fun setClientSecret(email: String) {
        mPrefs.edit().putString(KEY_CLIENT_SECRET, email).apply()
    }

    fun getClientSecret(): String {
        return mPrefs.getString(KEY_CLIENT_SECRET, null) ?: ""
    }

    fun setCardStatus(status: Boolean) {
        mPrefs.edit().putBoolean(KEY_CARD_ADD_STATUS, status).apply()
    }

    fun getCardStatus(): Boolean {
        return mPrefs.getBoolean(KEY_CARD_ADD_STATUS, false)
    }


    fun setPhone(phone: String) {
        mPrefs.edit().putString(KEY_PHONE, phone).apply()
    }

    fun getPhone(): String {
        return mPrefs.getString(KEY_PHONE, null) ?: ""
    }

    fun getPaymentID() = mPrefs.getString(KEY_PAYMENT_ID, "") ?: ""
    fun setPaymentID(id: String) {
        mPrefs.edit().putString(KEY_PAYMENT_ID, id).apply()
    }

    fun fireToken() = mPrefs.getString(KEY_FIRE_TOKEN, null) ?: ""

    fun setFireToken(token: String) {
        mPrefs.edit().putString(KEY_FIRE_TOKEN, token).apply()
    }

    fun setBarrerToken(id: String) {
        mPrefs.edit().putString(KEY_BARRER_TOKEN, id).apply()
    }

    fun getBarrerToken() =
        mPrefs.getString(KEY_BARRER_TOKEN, null)?: ""


}