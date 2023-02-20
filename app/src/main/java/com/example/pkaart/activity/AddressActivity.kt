package com.example.pkaart.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pkaart.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding

    private lateinit var preferances: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferances = this.getSharedPreferences("user", MODE_PRIVATE)
        loadUserInfo()

        binding.btnProceed.setOnClickListener {

            validateData(
                binding.userName.text.toString(),
                binding.userNumber.text.toString(),
                binding.userVillage.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.pinCode.text.toString(),
            )
        }
    }

    private fun validateData(
        name: String,
        number: String,
        village: String,
        city: String,
        state: String,
        pinCode: String
    ) {
        if (name.isEmpty() || number.isEmpty() || village.isEmpty() ||
            city.isEmpty() || state.isEmpty() || pinCode.isEmpty()
        ) {

            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show()
        } else {
            storeData(village, city, state, pinCode)
        }
    }

    private fun storeData(
        village: String,
        city: String,
        state: String,
        pinCode: String
    ) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["city"] = city
        map["state"] = state
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferances.getString("number", "ABCD")!!)

            .update(map).addOnSuccessListener {

                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtra("productIds", intent.getStringArrayExtra("productIds"))
                startActivity(intent)


            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferances.getString("number", "ABCD")!!)

            .get().addOnSuccessListener {

                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userState.setText(it.getString("state"))
                binding.userCity.setText(it.getString("city"))
                binding.pinCode.setText(it.getString("pinCode"))
            }
    }
}