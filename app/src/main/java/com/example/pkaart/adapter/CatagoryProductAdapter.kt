package com.example.pkaart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkaart.activity.ProductDetailActivity
import com.example.pkaart.databinding.ItemCategoryProductLayoutBinding
import com.example.pkaart.model.AddProductModel

class CatagoryProductAdapter(val context: Context, val list: ArrayList<AddProductModel>) :
    RecyclerView.Adapter<CatagoryProductAdapter.categoryProductViewHolder>() {

    inner class categoryProductViewHolder(val binding: ItemCategoryProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryProductViewHolder {
        val binding =
            ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return categoryProductViewHolder(binding)
    }


    override fun onBindViewHolder(holder: categoryProductViewHolder, position: Int) {

        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView3)

        holder.binding.textView6.text = list[position].productName

        holder.binding.textView5.text = list[position].productSp

        holder.itemView.setOnClickListener {

            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return list.size

    }


}