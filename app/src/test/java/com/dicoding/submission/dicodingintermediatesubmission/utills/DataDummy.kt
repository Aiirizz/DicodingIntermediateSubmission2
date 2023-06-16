package com.dicoding.submission.dicodingintermediatesubmission.utills

import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

object DataDummy {
    fun generateDummyStories(): List<ListStoryResponse> {
        val listStory = ArrayList<ListStoryResponse>()
        for (i in 1..20) {
            val story = ListStoryResponse(
                photoUrl = "0",
                createdAt = "",
                name = "Name $i",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
            )
            listStory.add(story)
        }

        return listStory
    }
}