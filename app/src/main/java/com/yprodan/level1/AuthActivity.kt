package com.yprodan.level1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.yprodan.level1.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSharedPreferences("name", Context.MODE_PRIVATE).apply {
            binding.emailInputEditText.setText(getString("email", ""))
            binding.passwordEditText.setText(getString("password", ""))
            binding.checkBox.isChecked = getBoolean("isChecked", false)
        }

        binding.loginButton.setOnClickListener {
            if (isValidInputData()) {
                if(isCheckedRememberMe()) rememberUser() else forgotUser()
                startActivity(Intent(this, MainActivity::class.java).apply {
                    this.putExtra(Constants.EMAIL_TAG, binding.emailInputEditText.text.toString())
                })
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

    private fun forgotUser(){
        getSharedPreferences("name", Context.MODE_PRIVATE).edit()
            .putString("email", "")
            .putString("password","")
            .putBoolean("isChecked", false).apply()
    }

    private fun rememberUser() {
        getSharedPreferences("name", Context.MODE_PRIVATE).edit()
            .putString("email", binding.emailInputEditText.text.toString())
            .putString("password",binding.passwordEditText.text.toString())
            .putBoolean("isChecked", true).apply()
    }

    private fun isCheckedRememberMe(): Boolean {
        return binding.checkBox.isChecked
    }

    private fun isValidInputData(): Boolean {
        var result = true
        if(binding.emailInputEditText.text.isNullOrEmpty()){
            binding.emailInputLayout.error = "missing email"
            result = false
        }else
        if (!"""(\w)+(.)(\w)+(@)(\w)+[.][a-z]{2,3}""".toRegex()
                .containsMatchIn(binding.emailInputEditText.text.toString())
        ) {
            binding.emailInputLayout.error = "incorrect email"
            result = false
        }

        if(binding.passwordEditText.text.isNullOrEmpty()){
            binding.passwordInputLayout.error = "missing password"
            result = false
        }
        Log.d("auth", "ok")
        return result
    }


}