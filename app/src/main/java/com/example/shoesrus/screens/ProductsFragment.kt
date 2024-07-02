package com.example.shoesrus.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesrus.R
import com.example.shoesrus.adapter.ProductAdapter
import com.example.shoesrus.databinding.FragmentProductsBinding
import com.example.shoesrus.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private val productViewModel: ProductViewModel by activityViewModels()

    private lateinit var productAdapter: ProductAdapter

    private lateinit var binding: FragmentProductsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        productAdapter = ProductAdapter(productViewModel)

        binding.productsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }

        productViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.differ.submitList(products)

        }
    }


}