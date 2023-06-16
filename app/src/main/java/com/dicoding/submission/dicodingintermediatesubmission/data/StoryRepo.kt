package com.dicoding.submission.dicodingintermediatesubmission.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.submission.dicodingintermediatesubmission.UserPreference
import com.dicoding.submission.dicodingintermediatesubmission.api.ApiService
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

class StoryRepo (private val apiService: ApiService, private val pref : UserPreference) {

    val token = pref.getToken()
    fun getStoryList(): LiveData<PagingData<ListStoryResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token.toString())
            }
        ).liveData
    }
}