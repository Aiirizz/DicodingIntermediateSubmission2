package com.dicoding.submission.dicodingintermediatesubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ActivityDetailBinding
import com.dicoding.submission.dicodingintermediatesubmission.response.ListStoryResponse

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<ListStoryResponse>("STORY")
        binding.apply {
            detailName.text = story?.name
            detailDesc.text = story?.description
        }
        Glide.with(this)
            .load(story?.photoUrl)
            .into(binding.detailImg)
    }
}