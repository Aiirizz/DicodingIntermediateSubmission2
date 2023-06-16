package com.dicoding.submission.dicodingintermediatesubmission.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.submission.dicodingintermediatesubmission.api.ApiService
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

class StoryPagingSource (private val apiService: ApiService, private val token : String) : PagingSource<Int, ListStoryResponse> (){
    override fun getRefreshKey(state: PagingState<Int, ListStoryResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStory("Bearer $token", position, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if(responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}