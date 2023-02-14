package com.example.pkaart.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pkaart.MainActivity
import com.example.pkaart.databinding.ActivityProductDetailBinding
import com.example.pkaart.roomDb.AppDataBase
import com.example.pkaart.roomDb.ProductDao
import com.example.pkaart.roomDb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                val productSp = it.getString("productSp")
                val productDisc = it.getString("productDescription")

                binding.textTitle.text = name
                binding.textPrice.text = productSp
                binding.textView8.text = productDisc

                val slideList = ArrayList<SlideModel>()
                for (data in list) {

                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                cartAction(proId, name, productSp, it.getString("productCoverImg"))

                Log.d("cartAction","$list")

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    @SuppressLint("SetTextI18n")
    private fun cartAction(proId: String, name: String?, productSp: String?, coverImg: String?) {


        val productDao = AppDataBase.getInstance(this).productDao()

        Log.d("ProductDao","$productDao")

        if (productDao.isExist(proId) != null) {
            binding.textAdd.text = "Go to Cart"

            Log.d("coverImg","$coverImg")
        } else {

            binding.textAdd.text = "Add to Cart"
        }


        binding.textAdd.setOnClickListener {
            if (productDao.isExist(proId) != null) {
                openCart()
            } else {
                addToCart(productDao,proId, name, productSp, coverImg)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun addToCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverImg: String?
    ) {

        val data = ProductModel(proId, name, coverImg, productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textAdd.text = "Go to Cart"

        }
    }

    private fun openCart() {

        val preferancece = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preferancece.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}