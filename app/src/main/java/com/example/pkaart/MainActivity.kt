package com.example.pkaart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.pkaart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)

        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->


            title = when (destination.id) {
                R.id.cardFragment -> "MyCart"
                R.id.moreFragment -> "MyDashBoard"
                else -> " p-Kart"
            }
        }
    }
}
