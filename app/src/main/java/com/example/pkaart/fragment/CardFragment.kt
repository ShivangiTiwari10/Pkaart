package com.example.pkaart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pkaart.databinding.FragmentCardBinding


class CardFragment : Fragment() {

    private lateinit var binding: FragmentCardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(layoutInflater)

        val preferancece = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferancece.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        return (binding.root)
    }


}