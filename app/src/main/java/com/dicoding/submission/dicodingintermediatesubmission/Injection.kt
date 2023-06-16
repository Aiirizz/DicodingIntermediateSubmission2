package com.dicoding.submission.dicodingintermediatesubmission

import android.content.Context
import com.dicoding.submission.dicodingintermediatesubmission.UserPreference
import com.dicoding.submission.dicodingintermediatesubmission.api.ApiConfig
import com.dicoding.submission.dicodingintermediatesubmission.data.StoryRepo

object Injection {
    fun provideRepo(context: Context): StoryRepo{
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference(context)
        return StoryRepo(apiService, pref)
    }
}