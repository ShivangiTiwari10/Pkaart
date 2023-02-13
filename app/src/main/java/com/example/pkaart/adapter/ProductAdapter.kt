package com.example.pkaart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkaart.activity.ProductDetailActivity
import com.example.pkaart.databinding.LayoutProductItemBinding
import com.example.pkaart.model.AddProductModel

class ProductAdapter(val context: Context, val list: ArrayList<AddProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView2.text = data.productName
        holder.binding.textView3.text = data.productCategory
        holder.binding.textView4.text = data.productMrp

        holder.binding.buttonA.text = data.productSp

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