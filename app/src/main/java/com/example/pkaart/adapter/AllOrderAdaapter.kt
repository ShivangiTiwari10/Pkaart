package com.example.pkaart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pkaart.databinding.AllOrdedrItemLayoutBinding
import com.example.pkaart.model.AllOrderModel


class AllOrderAdaapter(val list: ArrayList<AllOrderModel>, val context: Context) :
    RecyclerView.Adapter<AllOrderAdaapter.AllOrderViewHolder>() {


    inner class AllOrderViewHolder(val binding: AllOrdedrItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {

        return AllOrderViewHolder(
            AllOrdedrItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {

        holder.binding.productName.text = list[position].name
        holder.binding.productPrice.text = list[position].price



        when (list[position].status) {
            "Ordered" -> {

                holder.binding.productStatus.text = "Ordered"


            }

            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"

            }

            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"

            }

            "Canceled" -> {
                holder.binding.productStatus.text = "Canceled"

            }
        }

    }


    override fun getItemCount(): Int {

        return list.size
    }
}