package com.example.shoesrus.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesrus.R
import com.example.shoesrus.adapter.CheckOutAdapter
import com.example.shoesrus.databinding.FragmentCheckoutBinding
import com.example.shoesrus.model.Product
import com.example.shoesrus.viewmodel.ProductViewModel

class CheckoutFragment : Fragment() {

    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var checkoutAdapter: CheckOutAdapter
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        observeCheckoutItems()
        setUpCheckoutButton()
        observeTotalPrice()
        observeTotalCheckedOutItemsPrice()
        return binding.root
    }

    private fun setUpRecyclerView() {
        checkoutAdapter = CheckOutAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = checkoutAdapter
        }
    }

    private fun setUpCheckoutButton(){
        binding.checkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_orderSuccessfulFragment)
        }
    }

    private fun observeCheckoutItems() {
        productViewModel.checkoutItems.observe(viewLifecycleOwner) { checkoutItems ->
            checkoutAdapter.differ.submitList(checkoutItems)
            updateUI(checkoutItems)
        }
    }

    private fun observeTotalPrice(){
        productViewModel.totalPrice.observe(viewLifecycleOwner){ totalPrice ->
            binding.totalPrice.text = "$${totalPrice}"
        }
    }

    private fun observeTotalCheckedOutItemsPrice(){
        productViewModel.totalCheckedOutItemsPrice.observe(viewLifecycleOwner){ totalCheckedOutItemsPrice ->
            binding.checkoutItemsPrice.text = "$${totalCheckedOutItemsPrice}"
        }
    }

    private fun updateUI(checkoutItems: List<Product>) {
        if (checkoutItems.isEmpty()) {
            binding.emptyChecklistTxt.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.orderSummary.visibility = View.GONE
            binding.checkoutButton.visibility = View.GONE
        } else {
            binding.emptyChecklistTxt.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.orderSummary.visibility = View.VISIBLE
            binding.checkoutButton.visibility = View.VISIBLE
        }
    }
}
