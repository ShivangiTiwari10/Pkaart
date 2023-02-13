package com.example.pkaart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pkaart.databinding.ActivityProductDetailBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)


        getProductDetails(intent.getStringExtra("id"))
        setContentView(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getProductDetails(proId: String?) {

        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>


                val name = it.getString("productName")
                val sP = it.getString("productSp")
                val productDisc = it.getString("productDescription")

                binding.textTitle.text = name
                binding.textPrice.text = sP
                binding.textView8.text = productDisc

                val slideList = ArrayList<SlideModel>()
                for (data in list) {

                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


}