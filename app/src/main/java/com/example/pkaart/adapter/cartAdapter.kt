package com.example.pkaart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkaart.activity.ProductDetailActivity
import com.example.pkaart.databinding.LayoutCartFragmentBinding
import com.example.pkaart.roomDb.AppDataBase
import com.example.pkaart.roomDb.ProductModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class cartAdapter(val context: Context, val list: List<ProductModel>) :
    RecyclerView.Adapter<cartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: LayoutCartFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartFragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)

        holder.binding.productName.text = list[position].productName
        holder.binding.sellingPrize.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }


        val dao = AppDataBase.getInstance(context).productDao()
        holder.binding.imageDelete.setOnClickListener {

            GlobalScope.launch {
                Dispatchers.IO

                dao.deleteProduct(
                    ProductModel(
                        list[position].productId,
                        list[position].productName,
                        list[position].productImage,
                        list[position].productSp
                    )
                )
            }
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }


}