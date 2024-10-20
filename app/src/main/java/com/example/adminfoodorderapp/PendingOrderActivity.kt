package com.example.adminfoodorderapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderapp.Adapter.DeliveryAdapter
import com.example.adminfoodorderapp.Adapter.PendingOrderAdapter
import com.example.adminfoodorderapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodorderapp.databinding.PendingOrdersItemBinding

class PendingOrderActivity : AppCompatActivity(){

    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }


        val orderedCustomerName = arrayListOf(
            "Sanjoy",
            "Subal",
            "Joy"
        )
        val orderedQuantity = arrayListOf(
            "5",
            "6",
            "2"
        )

        val orderedFoodImage = arrayListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu4
        )
        val adapter = PendingOrderAdapter(orderedCustomerName,orderedQuantity,orderedFoodImage, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}