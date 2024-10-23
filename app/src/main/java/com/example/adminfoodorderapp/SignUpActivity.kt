package com.example.adminfoodorderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodorderapp.databinding.ActivitySignUpBinding
import com.example.adminfoodorderapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var userName:String
    private lateinit var nameOfRestuarent:String
    private lateinit var database: DatabaseReference

   private val binding: ActivitySignUpBinding by lazy {
       ActivitySignUpBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialization of Firebase Auth
        auth = Firebase.auth
        //initialize firebase database
        database = Firebase.database.reference





        //for giving the locationlist
        var locationList = arrayOf("Kawranbazar","Bashundhara","Mirpur","Motijheel")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)


        binding.createButton.setOnClickListener {
            //get text from editText
            userName = binding.name.text.toString().trim()
            nameOfRestuarent = binding.restaurantName.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.toString().trim()

            if(email.isBlank() || userName.isBlank() || nameOfRestuarent.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
        }

        binding.haveAccountButton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Account creation failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","CreateAccount: Failure",task.exception)
            }
        }
    }


    //save data into database
    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        nameOfRestuarent = binding.restaurantName.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.toString().trim()

        val user = UserModel(userName,nameOfRestuarent,email,password)
        val userId : String = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase database
        database.child("user").child(userId).setValue(user)
    }
}