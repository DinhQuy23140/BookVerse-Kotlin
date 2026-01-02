package com.example.readbookkotlin.ui.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.readbookkotlin.R
import com.example.readbookkotlin.databinding.ActivityLoginBinding
import com.example.readbookkotlin.util.extensions.gone
import com.example.readbookkotlin.util.extensions.visible

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.loginViewForotPass.setOnClickListener {
            binding.layoutForgotPass.visible()
            binding.forgotClose.visible()
            binding.layoutLogin.gone()
        }

        binding.loginViewSignup.setOnClickListener {
            binding.layoutSignup.visible()
            binding.layoutLogin.gone()
        }

        binding.signupBtnViewLogin.setOnClickListener {
            binding.layoutLogin.visible()
            binding.layoutSignup.gone()
        }

        binding.forgotClose.setOnClickListener {
            binding.layoutForgotPass.gone()
            binding.layoutLogin.visible()
            binding.forgotClose.gone()
        }
    }
}