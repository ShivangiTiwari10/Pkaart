package com.example.pkaart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pkaart.MainActivity
import com.example.pkaart.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_yz21RD9mwEV4GD")


        try {
            val options = JSONObject()

            options.put("name", "PKart")
            options.put("description", "Best Ecommerce app")
            options.put("image", "https://i.pravatar.cc/300")
            options.put("theme.color", "#8F42BE")
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "deepanshutiwari191@gmail.com")
            options.put("prefill.contact", "8269390991")

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d("Success", "$p0")

        Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show()

        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayExtra("productIds")

        for (currentId in id!!) {
            fetchData(currentId)

        }
    }

    private fun fetchData(productId: String?) {

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {
                saveData(
                    it.getString("productName"),
                    it.getString("productSp"),
                    productId
                )
            }
    }

    private fun saveData(name: String?, price: String?, productId: String) {

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()

        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number", "93..")!!

        val fireStore = Firebase.firestore.collection("AllOrders")
        val key = fireStore.document().id

        data["orderId"] = key

        fireStore.add(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("Error", "$p0,$p1")
        Toast.makeText(this, "Payment error", Toast.LENGTH_SHORT).show()
    }
}

