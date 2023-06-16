package com.dicoding.submission.dicodingintermediatesubmission.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission.dicodingintermediatesubmission.data.local.Preference
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ActivitySplashBinding
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.LoginViewModel
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this@SplashActivity,
            ViewModelFactory(Preference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.stateData().observe(this) {
            token = it
        }


        Handler(Looper.getMainLooper()).postDelayed({
          if (token.isNullOrBlank()) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }, 3000)
        
        splashAnimation()
    }
    private fun splashAnimation() {
        ObjectAnimator.ofFloat(binding.logoSplash, View.TRANSLATION_Y, 0f, 0f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logoSplash, View.ALPHA, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logoSplash, View.SCALE_X, 0f, 1f).apply {
            duration = 1500
        }.start()

        ObjectAnimator.ofFloat(binding.logoSplash, View.SCALE_Y, 0f, 1f).apply {
            duration = 1500
        }.start()
    }
}