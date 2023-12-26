package www.onbeatapps.com.ui.lineUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import www.onbeatapps.com.data.model.api.response.ArtistDetailsRESPONSE
import www.onbeatapps.com.data.model.api.response.ArtistListRESPONSE
import www.onbeatapps.com.data.model.api.response.LineUpRESPONSE
import www.onbeatapps.com.data.remote.coroutine.NetworkResponse
import www.onbeatapps.com.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LineUpViewModel @Inject constructor(
    private val api: www.onbeatapps.com.data.remote.ApiInterface,
    private val appPreferences: www.onbeatapps.com.data.local.prefs.AppPreferences
) : BaseViewModel() {

    init {
        launchApiCall {
//           val voucher = appDbHelper.getAllStudent()

        }
    }

    private val _lineUpList = MutableLiveData<List<LineUpRESPONSE.Data>>()
    val lineUpList: MutableLiveData<List<LineUpRESPONSE.Data>>
        get() = _lineUpList
    fun getLineUp() {
        launchApiCall {
        viewModelScope.launch {
            appPreferences.apply {
                when (val response = api.getLineUp()) {
                    is NetworkResponse.Success -> {
                        val data = response.body
                        if (response.body.isSuccess()) {

                            _lineUpList.value = data.data

                        } else {

                            showToast(data.message)
                        }
                    }
                    is NetworkResponse.Error -> {
                        showToast(response.body?.message)
                        setExceptionUtil(response)
                    }
                }
            }
        }
    }
    }

    private val _artistList = MutableLiveData<List<ArtistListRESPONSE.Data>>()
    val artistList: MutableLiveData<List<ArtistListRESPONSE.Data>>
        get() = _artistList
    fun getArtist() {
        launchApiCall {
        viewModelScope.launch {
            appPreferences.apply {
                when (val response = api.getArtist()) {
                    is NetworkResponse.Success -> {
                        val data = response.body
                        if (response.body.isSuccess()) {

                            _artistList.value = data.data

                        } else {

                            showToast(data.message)
                        }
                    }
                    is NetworkResponse.Error -> {
                        showToast(response.body?.message)
                        setExceptionUtil(response)
                    }
                }
            }
        }
    }
    }


    private val _artistDetails = MutableLiveData<List<ArtistDetailsRESPONSE.Data>>()
    val artistDetails: MutableLiveData<List<ArtistDetailsRESPONSE.Data>>
        get() = _artistDetails

    private val _details = MutableLiveData<Boolean>()
    val details: MutableLiveData<Boolean>
    get() = _details

    fun getArtistDetails(code:String) {
        launchApiCall {
        viewModelScope.launch {
            appPreferences.apply {
                when (val response = api.getArtistDetails(code)) {
                    is NetworkResponse.Success -> {
                        val data = response.body
                        if (response.body.isSuccess()) {
                            _artistDetails.value = data.data
                            _details.value = true
                        } else {
                            _details.value = false
                            showToast(data.message)
                        }
                    }
                    is NetworkResponse.Error -> {
                        showToast(response.body?.message)
                        setExceptionUtil(response)
                    }
                }
            }
        }
    }
    }
}