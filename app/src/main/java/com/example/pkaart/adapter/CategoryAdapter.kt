package com.example.pkaart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkaart.R
import com.example.pkaart.activity.CategoryActivity
import com.example.pkaart.databinding.LayoutCategoryItemBinding
import com.example.pkaart.model.CategoryModel

class CategoryAdapter(var context: Context, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.categoryViewHolder>() {

    inner class categoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        return categoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {

        holder.binding.textCate.text = list[position].cate
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat", list[position].cate)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}