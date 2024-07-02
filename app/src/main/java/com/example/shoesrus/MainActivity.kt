package com.example.shoesrus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shoesrus.databinding.ActivityMainBinding
import com.example.shoesrus.databinding.ProductItemLayoutBinding
import com.example.shoesrus.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val productViewModel: ProductViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNavigation()
    }


    private fun setUpBottomNavigation(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)


        // Listen for fragment changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.orderSuccessfulFragment -> hideBottomNavigation()
                else -> showBottomNavigation()
            }
        }
    }


    private fun hideBottomNavigation() {
        binding.bottomNavView.visibility = View.GONE

    }

    private fun showBottomNavigation() {
        binding.bottomNavView.visibility= View.VISIBLE
    }



}