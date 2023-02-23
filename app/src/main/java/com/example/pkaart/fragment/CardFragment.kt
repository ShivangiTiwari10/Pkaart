package com.example.pkaart.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pkaart.activity.AddressActivity
import com.example.pkaart.adapter.cartAdapter
import com.example.pkaart.databinding.FragmentCardBinding
import com.example.pkaart.roomDb.AppDataBase
import com.example.pkaart.roomDb.ProductModel


class CardFragment : Fragment() {

    private lateinit var binding: FragmentCardBinding
    private lateinit var list: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(layoutInflater)

        val preferancece =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferancece.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDataBase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()) {
            binding.cartRecyclerView.adapter = cartAdapter(requireContext(), it)

            list.clear()
            for (data in it) {
                list.add(data.productId)
            }

            totalCost(it)
        }

        return (binding.root)
    }

    @SuppressLint("SetTextI18n")
    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSp!!.toInt()
        }

        binding.textTotal.text = "Total Item In Cart Is ${data.size}"
        binding.textCost.text = "Total Cost:${total}"

        binding.checkOut.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtra("totalCost", total.toString())
            intent.putExtra("productIds",list)
            startActivity(intent)

        }
    }


}