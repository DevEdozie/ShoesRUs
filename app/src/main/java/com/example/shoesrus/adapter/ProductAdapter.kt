package com.example.shoesrus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesrus.R
import com.example.shoesrus.databinding.ProductItemLayoutBinding
import com.example.shoesrus.model.Product
import com.example.shoesrus.viewmodel.ProductViewModel

class ProductAdapter(
    private val productViewModel: ProductViewModel
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val itemBinding: ProductItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    // DiffUtil callback to efficiently update the list of products
    private val differCallBack = object : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            // Check if the IDs of the items are the same to determine if they represent the same item
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            // Check if the content of the items is the same to determine if they are identical
            return oldItem == newItem
        }
    }

    // AsyncListDiffer to handle list changes asynchronously and efficiently
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = differ.currentList[position]

        holder.itemBinding.apply {
            productImage.setImageResource(currentProduct.product_image)
            productTitle.text = currentProduct.product_title
            productPrice.text = "$${currentProduct.product_price}"

            // Set initial state of the addToCart button
            if (currentProduct.isChecked) {
                addToCart.setImageResource(R.drawable.ic_shopping_cart)
            } else {
                addToCart.setImageResource(R.drawable.ic_add_to_cart_outlined)
            }

            // Handle addToCart button click
            addToCart.setOnClickListener {
                if (currentProduct.isChecked) {
                    currentProduct.isChecked = false
                    productViewModel.removeFromCheckout(currentProduct)
                    addToCart.setImageResource(R.drawable.ic_add_to_cart_outlined)
                } else {
                    currentProduct.isChecked = true
                    productViewModel.addToCheckout(currentProduct)
                    addToCart.setImageResource(R.drawable.ic_shopping_cart)
                }
            }
        }
    }
}
