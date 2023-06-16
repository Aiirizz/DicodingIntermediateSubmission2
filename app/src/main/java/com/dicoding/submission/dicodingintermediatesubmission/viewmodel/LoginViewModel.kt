package com.dicoding.submission.dicodingintermediatesubmission.viewmodel

import androidx.lifecycle.*
import com.dicoding.submission.dicodingintermediatesubmission.api.ApiConfig
import com.dicoding.submission.dicodingintermediatesubmission.data.local.Preference
import com.dicoding.submission.dicodingintermediatesubmission.response.LoginResponse
import com.dicoding.submission.dicodingintermediatesubmission.response.LoginResultResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val preference: Preference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _userLogin = MutableLiveData<LoginResultResponse>()
    val userlogin: LiveData<LoginResultResponse> = _userLogin

    private val client = ApiConfig.getApiService()

    fun getLoginUser(email: String, password: String) {
        _isLoading.value = true
        client.userLogin(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userLogin.value = response.body()?.loginResult
                    _message.value = response.body()?.message
                    viewModelScope.launch {
                        preference.saveKey(response.body()?.loginResult?.token.toString())
                    }
                }else{
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
    fun stateData(): LiveData<String> {
        return preference.getKeyMap().asLiveData()
    }
}
