package com.example.adminfoodorderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodorderapp.databinding.ActivityLoginBinding
import com.example.adminfoodorderapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

//    private var userName:String? = null
//    private var nameOfRestuarent:String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    //private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize firebase
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        binding.logInButton.setOnClickListener {
            //get data from the field
            email = binding.signInEmail.text.toString().trim()
            password = binding.password.text.toString().trim()

//            Log.d("sanjoy", "User UID: $email")
//            Log.d("sanjoy", "User Email: $password")

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {

                checkAuthority()
            }
        }
        binding.donotButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

//    private fun createUserAccount(email: String, password: String) {
//        val user: FirebaseUser? = auth.currentUser
//        //Log.d("sanjoy", "Login successful!")
//        Log.d("sanjoy", "User UID: ${user?.uid}")
//        Log.d("sanjoy", "User Email: ${user?.email}")
//
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Login successful
////                val user: FirebaseUser? = auth.currentUser
////                Log.d("sanjoy", "Login successful!")
////                Log.d("sanjoy", "User UID: ${user?.uid}")
////                Log.d("sanjoy", "User Email: ${user?.email}")
//                Log.d("sanjoy","createAuthentication: Authentication succeeded",task.exception)
//                updateUI(user)
//            } else {
//                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
//                    if(task.isSuccessful){
//                        val user:FirebaseUser? = auth.currentUser
//                        saveUserData()
//                        updateUI(user)
//                    }
//                    else{
//                        Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
//                        Log.d("sanjoy","createAuthentication: Authentication Failed",task.exception)
//                    }
//                }
//            }
//        }
//    }

    private fun checkAuthority() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                val user: FirebaseUser? = auth.currentUser
                updateUi(user)
            }else{
                Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    private fun saveUserData() {
//
//        email = binding.signInEmail.text.toString().trim()
//        password = binding.password.text.toString().trim()
//
//        val user = UserModel(userName,nameOfRestuarent,email,password)
//        val userId:String?  = FirebaseAuth.getInstance().currentUser?.uid
//        userId?.let{
//            database.child("user").child(it).setValue(user)
//        }
//    }

    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

