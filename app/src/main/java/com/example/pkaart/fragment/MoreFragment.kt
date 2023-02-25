package com.example.pkaart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pkaart.adapter.AllOrderAdaapter
import com.example.pkaart.databinding.FragmentMoreBinding
import com.example.pkaart.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var list: ArrayList<AllOrderModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        list = ArrayList()

        val preferances =
            requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrder")
            .whereEqualTo("userId", preferances.getString("number", "")!!).get()
            .addOnSuccessListener {


                list.clear()

                for (doc in it) {
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter = AllOrderAdaapter(list, requireContext())

            }
        return binding.root

    }

}