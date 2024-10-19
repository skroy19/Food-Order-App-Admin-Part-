package com.example.adminfoodorderapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderapp.Adapter.AddItemAdapter
import com.example.adminfoodorderapp.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {

    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val menuFoodName = listOf("Burger","Sandwich","Momo","Biriyani","Chawmin")
        val menuFoodPrice = listOf("$5","$15","$25","$15","$5")
        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu2,
            R.drawable.menu3
        )
        val adapter = AddItemAdapter(ArrayList(menuFoodName),ArrayList(menuFoodPrice),
            ArrayList(menuImage)
        )
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}