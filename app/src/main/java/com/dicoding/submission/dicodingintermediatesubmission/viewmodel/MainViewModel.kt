package com.dicoding.submission.dicodingintermediatesubmission.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.submission.dicodingintermediatesubmission.Injection
import com.dicoding.submission.dicodingintermediatesubmission.data.StoryRepo
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

class MainViewModel(storyRepo: StoryRepo) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val stories : LiveData<PagingData<ListStoryResponse>> =
        storyRepo.getStoryList().cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(Injection.provideRepo(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}