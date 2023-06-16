package com.dicoding.submission.dicodingintermediatesubmission.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission.dicodingintermediatesubmission.UserPreference
import com.dicoding.submission.dicodingintermediatesubmission.data.local.Preference
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ActivityLoginBinding
import com.dicoding.submission.dicodingintermediatesubmission.response.LoginResultResponse
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.LoginViewModel
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelFactory(Preference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()

        binding.btnLoginReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLoginSigin.setOnClickListener {
            val email = binding.inpLoginEmail.text.toString()
            val pass = binding.inpLoginPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (pass.length >= 8) {
                    viewModel.getLoginUser(
                        email, pass
                    )
                    viewModel.userlogin.observe(this){data ->
                        saveKey(data)
                    }
                }
            } else {
                toastMessage("Invalid Form")
            }
        }

        viewModel.message.observe(this) { message ->
            toastMessage(message)
            if (message == "success") {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toastMessage(message: String) {
        val changeMessage: String
        if (message == "Bad Request" || message == "Unauthorized") {
            changeMessage = "Email or Password Invalid"
            Toast.makeText(this, changeMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveKey(data: LoginResultResponse?) {
        val pref = UserPreference(applicationContext)
        pref.setToken(data?.token.toString())
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.loginText,View.TRANSLATION_X,-60f,20f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}