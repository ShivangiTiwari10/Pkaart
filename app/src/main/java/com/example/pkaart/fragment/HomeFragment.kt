package com.example.pkaart.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pkaart.R
import com.example.pkaart.adapter.CategoryAdapter
import com.example.pkaart.adapter.ProductAdapter
import com.example.pkaart.databinding.FragmentHomeBinding
import com.example.pkaart.model.AddProductModel
import com.example.pkaart.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)

        val preferancece = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preferancece.getBoolean("isCart",false))
        findNavController().navigate(R.id.action_homeFragment_to_cardFragment)


        getCategories()

        getSliderImage()

        getProducts()
        return binding.root
    }

    private fun getSliderImage() {

        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {

                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()

        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()

                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                binding.productRecycler.adapter = ProductAdapter(requireContext(), list)
            }
    }

    private fun getCategories() {
        val list = ArrayList<CategoryModel>()

        Firebase.firestore.collection("Categories")
            .get().addOnSuccessListener {
                list.clear()

                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }

                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(), list)
            }
    }
}




