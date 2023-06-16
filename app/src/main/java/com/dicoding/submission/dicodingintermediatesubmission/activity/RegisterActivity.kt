package com.dicoding.submission.dicodingintermediatesubmission.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.submission.dicodingintermediatesubmission.R
import com.dicoding.submission.dicodingintermediatesubmission.databinding.ActivityRegisterBinding
import com.dicoding.submission.dicodingintermediatesubmission.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        registerViewModel.message.observe(this) {
            checkRegisterUser(it, registerViewModel.isError)
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()

        binding.btnRegis.setOnClickListener {
            if (binding.edRegisterEmail.isValidEmail && binding.edRegisterPassword.isValidPass) {
                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val pass = binding.edRegisterPassword.text.toString()

                if (name.isEmpty()) {
                    binding.edRegisterName.error = getString(R.string.input_name)
                    return@setOnClickListener
                }

                registerViewModel.getRegisterUser(name, email, pass)
            }
        }
    }

    private fun checkRegisterUser(message: String, isError: Boolean) {
        if (!isError) {
            Toast.makeText(this, getString(R.string.user_create), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            when (message) {
                "Bad Request" -> {
                    Toast.makeText(this, getString(R.string.email_taken), Toast.LENGTH_SHORT).show()
                    binding.edRegisterEmail.apply {
                        setText("")
                        requestFocus()
                    }
                }
                "timeout" -> {
                    Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(
                        this,
                        "${getString(R.string.error)} $message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.regisText,View.TRANSLATION_X,-60f,20f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}
