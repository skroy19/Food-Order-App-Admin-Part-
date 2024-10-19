package com.example.adminfoodorderapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodorderapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.namee.isEnabled = false
        binding.addresss.isEnabled = false
        binding.phonee.isEnabled = false
        binding.emaill.isEnabled = false
        binding.passwordd.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable = ! isEnable

            binding.namee.isEnabled = isEnable
            binding.addresss.isEnabled = isEnable
            binding.phonee.isEnabled = isEnable
            binding.emaill.isEnabled = isEnable
            binding.passwordd.isEnabled = isEnable

            if(isEnable){
                binding.namee.requestFocus()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}