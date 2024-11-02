package com.example.adminfoodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodorderapp.Adapter.DeliveryAdapter
import com.example.adminfoodorderapp.Adapter.PendingOrderAdapter
import com.example.adminfoodorderapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodorderapp.databinding.PendingOrdersItemBinding
import com.example.adminfoodorderapp.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked{

    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }

    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFristFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseeOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //initialization of database
        database = FirebaseDatabase.getInstance()
        //initialization of database reference
        databaseeOrderDetails = database.reference.child("OrderDetails")

        getOrdersDetails()

        binding.backButton.setOnClickListener {
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getOrdersDetails() {
        //retrieve order details from Firebase database
        databaseeOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataTOListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataTOListForRecyclerView() {
        for(orderItem in listOfOrderItem){
            //add data to respective list for population the recycler view
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach{
                listOfImageFristFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImageFristFoodOrder,this)
        binding.pendingOrderRecyclerView.adapter = adapter

    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails: OrderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        //handle item acceptance and update database
        val childItemPushKey: String = listOfOrderItem[position].itemPushKey?:""
        val clickItemOrderReference = childItemPushKey.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)

    }


    override fun onItemDispatchClickListener(position: Int) {
        //handle item dispatch and update database
        val dispatchedItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference = database.reference.child("CompletedOrder").child(dispatchedItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchedItemPushKey)
            }
    }

    private fun deleteThisItemFromOrderDetails(dispatchedItemPushKey: String) {
        val orderDetailsItemsReference = database.reference.child("OrderDetails").child(dispatchedItemPushKey)
        orderDetailsItemsReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this,"Order is Dispatched",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Order is not Dispatched",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateOrderAcceptStatus(position: Int) {
            //update order acceptance in user's buyhistory and orderdetails
            val userIdOfCLickedItem = listOfOrderItem[position].userUid
            val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
            val buyHistoryReference = database.reference.child("user").child(userIdOfCLickedItem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
            buyHistoryReference.child("orderAccepted").setValue(true)
            databaseeOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)
    }

}