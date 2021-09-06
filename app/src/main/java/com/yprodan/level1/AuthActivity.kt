package com.yprodan.level1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.yprodan.level1.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            if ("""(\w)+(.)(\w)+(@)(\w)+[.][a-z]{2,3}""".toRegex()
                    .containsMatchIn(binding.emailInputEditText.text.toString())
                && !binding.passwordEditText.text.isNullOrEmpty()
            ) {
                Log.d("auth", "ok")
                startActivity(Intent(this, MainActivity::class.java).apply {
                    this.putExtra(Constants.EMAIL_TAG, binding.emailInputEditText.text.toString())
                })
                finish()
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {


        return super.onCreateView(name, context, attrs)
    }
}