package com.yprodan.level1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.addTextChangedListener
import com.yprodan.level1.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserDataIfExist()

        binding.loginButton.setOnClickListener {
            if (isValidInputData()) {
                if(isCheckedRememberMe()) {
                    rememberUser()
                } else {
                    forgotUser()
                }
                startActivity(Intent(this, MainActivity::class.java).apply {
                    this.putExtra(getString(R.string.email_tag), binding.emailInputEditText.text.toString())
                }, ActivityOptionsCompat.makeCustomAnimation(applicationContext, R.anim.swipe_left, R.anim.fade_out).toBundle())
                finish()
            }
        }

        binding.passwordEditText.addTextChangedListener {
            binding.passwordInputLayout.error = null
        }
        binding.emailInputEditText.addTextChangedListener {
            binding.emailInputLayout.error = null
        }
    }

    private fun getUserDataIfExist(){
        getSharedPreferences(getString(R.string.shared_preferences_name_tag), Context.MODE_PRIVATE).apply {
            binding.emailInputEditText.setText(getString(getString(R.string.email_tag), ""))
            binding.passwordEditText.setText(getString(getString(R.string.password_tag), ""))
            binding.checkBox.isChecked = getBoolean(getString(R.string.check_box_state_tag), false)
        }
    }

    private fun forgotUser(){
        getSharedPreferences(getString(R.string.shared_preferences_name_tag), Context.MODE_PRIVATE).edit()
            .putString(getString(R.string.email_tag), "")
            .putString(getString(R.string.password_tag),"")
            .putBoolean(getString(R.string.check_box_state_tag), false).apply()
    }

    private fun rememberUser() {
        getSharedPreferences(getString(R.string.shared_preferences_name_tag), Context.MODE_PRIVATE).edit()
            .putString(getString(R.string.email_tag), binding.emailInputEditText.text.toString())
            .putString(getString(R.string.password_tag),binding.passwordEditText.text.toString())
            .putBoolean(getString(R.string.check_box_state_tag), true).apply()
    }

    private fun isCheckedRememberMe(): Boolean {
        return binding.checkBox.isChecked
    }

    private fun isValidInputData(): Boolean {
        with(binding) {
            var result = true
            if (emailInputEditText.text.isNullOrEmpty()) {
                emailInputLayout.error = getString(R.string.missing_email_error_msg)
                result = false
            } else
                if (!"""(\w)+(.)(\w)+(@)(\w)+[.][a-z]{2,3}""".toRegex()
                        .containsMatchIn(emailInputEditText.text.toString())
                ) {
                    emailInputLayout.error = getString(R.string.incorrect_email_error_msg)
                    result = false
                }

            if (passwordEditText.text.isNullOrEmpty()) {
                passwordInputLayout.error = getString(R.string.missing_password_error_msg)
                result = false
            }
            Log.d("auth", "ok")
            return result
        }
    }
}