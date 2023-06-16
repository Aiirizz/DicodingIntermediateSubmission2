package com.dicoding.submission.dicodingintermediatesubmission.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.submission.dicodingintermediatesubmission.api.ApiConfig
import com.dicoding.submission.dicodingintermediatesubmission.data.local.Preference
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse
import com.dicoding.submission.dicodingintermediatesubmission.response.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val preference: Preference) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()

    private val _story = MutableLiveData<ArrayList<ListStoryResponse>>()
    val story: LiveData<ArrayList<ListStoryResponse>> = _story

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val userClient = ApiConfig.getApiService()

    var isError: Boolean = false

    fun dataMap(): LiveData<String> {
        return preference.getKeyMap().asLiveData()
    }

    fun getStoriesLocation(token: String){
        _loading.value = true
        userClient.getStoriesLocation("Bearer $token", 1).enqueue(object : Callback<StoryResponse>{
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _loading.value = false
                if (response.isSuccessful){
                    _story.value = response.body()?.listStory as ArrayList<ListStoryResponse>
                } else {
                    Log.e(TAG,"Map onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
                isError = true
            }
        })
    }

    companion object {
        private const val TAG = "MapViewModel"
    }
}
