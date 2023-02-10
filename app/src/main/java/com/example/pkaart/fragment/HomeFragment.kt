package com.example.pkaart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pkaart.adapter.CategoryAdapter
import com.example.pkaart.databinding.FragmentHomeBinding
import com.example.pkaart.model.AddProductModel
import com.example.pkaart.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)



        getCategories()
        getProducts()
        return binding.root
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

                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(),list)
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

//                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(),list)
            }

    }



}