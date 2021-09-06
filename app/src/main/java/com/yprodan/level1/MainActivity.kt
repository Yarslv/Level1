package com.yprodan.level1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.yprodan.level1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.apply {
            with(extras?.get(Constants.EMAIL_TAG).toString()){
                var firstName = this.subSequence(0, this.indexOf('.')).toString()
                firstName = firstName[0].uppercase().plus(if(firstName.length > 1) firstName.subSequence(1, firstName.length) else "")
                var lastName = this.subSequence(this.indexOf('.')+1, this.indexOf('@')).toString()
                lastName = lastName[0].uppercase().plus(if(lastName.length > 1) lastName.subSequence(1, lastName.length) else "")
                binding.nameTextView.text =  firstName.plus(" ").plus(lastName)//todo in strings
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
               return super.onCreateView(name, context, attrs)
    }

}