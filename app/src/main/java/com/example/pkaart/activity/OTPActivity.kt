package com.example.pkaart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pkaart.MainActivity
import com.example.pkaart.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userOtp.setOnClickListener {

        }

        binding.button.setOnClickListener {

            if (binding.userOtp.text!!.isEmpty())
                Toast.makeText(this, "Please fill OTP", Toast.LENGTH_SHORT).show()
            else {
                verifyUser(binding.userOtp.text.toString())
            }
        }
    }


    private fun verifyUser(otp: String) {
        val credential =
            PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, otp)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preferances = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferances.edit()

                    editor.putString("number", intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

                }
            }
    }
}
