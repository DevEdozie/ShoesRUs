package com.example.shoesrus.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoesrus.R
import com.example.shoesrus.databinding.FragmentOrderSuccessfulBinding
import com.example.shoesrus.viewmodel.ProductViewModel

class OrderSuccessfulFragment : Fragment() {
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderSuccessfulBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderSuccessfulBinding.inflate(inflater, container, false)
        setUpBackToProductsButton()
        return binding.root
    }

    private fun setUpBackToProductsButton() {

        binding.backToProductsButton.setOnClickListener {
            // Reset all products and clear checkout items
            productViewModel.resetAllProducts()
            productViewModel.clearCheckoutItems()
            // Navigate back to ProductsFragment
            findNavController().popBackStack(R.id.productsFragment, false)
        }
    }


}