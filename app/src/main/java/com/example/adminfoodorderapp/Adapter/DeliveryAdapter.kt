package com.example.adminfoodorderapp.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodorderapp.databinding.DeliveryItemBinding
import com.example.adminfoodorderapp.databinding.ItemItemBinding
import kotlinx.coroutines.Delay

class DeliveryAdapter(private val customerNames:MutableList<String>,private val paymentStatus:MutableList<Boolean>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                if(paymentStatus[position]==true){
                    moneyStatus.text = "Received"
                }else{
                    moneyStatus.text = "Not Received"
                }


                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                moneyStatus.setTextColor(colorMap[paymentStatus[position]]?:Color.BLACK)
                status.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.BLACK)
            }
        }

    }
}