package com.example.pkaart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkaart.databinding.LayoutCartFragmentBinding
import com.example.pkaart.roomDb.ProductModel

class cartAdapter(val context: Context, val list: List<ProductModel>) :
    RecyclerView.Adapter<cartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: LayoutCartFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartFragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)

        holder.binding.productName.text = list[position].productName
        holder.binding.sellingPrize.text = list[position].productSp

    }

    override fun getItemCount(): Int {

        return list.size
    }


}