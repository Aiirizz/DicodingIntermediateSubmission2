package com.dicoding.submission.dicodingintermediatesubmission.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.dicodingintermediatesubmission.R
import com.dicoding.submission.dicodingintermediatesubmission.adapter.StoryAdapter
import com.dicoding.submission.dicodingintermediatesubmission.UserPreference
import com.dicoding.submission.dicodingintermediatesubmission.adapter.LoadingStateAdapter
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ActivityMainBinding
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var preference: UserPreference
    private val viewModel : MainViewModel by viewModels {
        MainViewModel.ViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.rVListStory.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rVListStory.addItemDecoration(itemDecoration)

        preference = UserPreference(this)
        val token: String = intent.getStringExtra("TOKEN").toString()
        getUserStory(token)

        viewModel.loading.observe(this) {
            showLoading(it)
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
                lifecycleScope.launch {
                    preference.clearToken()
                }
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            R.id.language_menu-> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.map_menu -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            else -> true

        }
    }

    private fun getUserStory(story: String) {
        val adapter = StoryAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rVListStory.layoutManager = layoutManager
        binding.rVListStory.adapter = adapter
        binding.rVListStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.stories.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}