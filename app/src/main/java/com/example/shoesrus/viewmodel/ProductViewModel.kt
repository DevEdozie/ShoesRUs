package com.example.shoesrus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoesrus.R
import com.example.shoesrus.model.Product

class ProductViewModel : ViewModel() {

    // Sample list of products
    private val _products = MutableLiveData<List<Product>>().apply {
        value = listOf(
            Product(1, R.drawable.don_issue_6, "D.O.N. ISSUE 6", 120, false),
            Product(2, R.drawable.harden_volume_8, "HARDEN VOLUME 8", 160, false),
            Product(3, R.drawable.terrex_free_hiker, "TERREX FREE HIKER", 160, false),
            Product(4, R.drawable.crazy_98, "CRAZY 98", 150, false),
            Product(5, R.drawable.trae_young_3, "TRAE YOUNG 3", 70, false),
            Product(6, R.drawable.adizero_select_2, "ADIZERO SELECT 2.0", 120, false),
            Product(7, R.drawable.dame_certified_2, "DAME CERTIFIED 2", 81, false),
            Product(8, R.drawable.street_ball_3, "STREETBALL III", 63, false),
            Product(9, R.drawable.forum_84_camp, "FORUM 84 CAMP", 125, false),
            Product(10, R.drawable.forum_mid, "FORUM MID", 44, false),
        )
    }
    val products: LiveData<List<Product>> = _products

    // Mutable LiveData for checkout items
    private val _checkoutItems = MutableLiveData<MutableList<Product>>().apply {
        value = mutableListOf()
    }
    val checkoutItems: LiveData<MutableList<Product>> = _checkoutItems

    // Total checked out items price
    private val _totalCheckedOutItemsPrice = MutableLiveData<Int>().apply {
        value = 0
    }
    val totalCheckedOutItemsPrice: LiveData<Int> = _totalCheckedOutItemsPrice

    // Total price LiveData
    private val _totalPrice = MutableLiveData<Int>().apply {
        value = 0
    }
    val totalPrice: LiveData<Int> = _totalPrice

    // Constant delivery charge
    private val deliveryCharge = 40

    // Function to add product to checkout
    fun addToCheckout(product: Product) {
        _checkoutItems.value?.add(product)
        updatePrices()
    }

    // Function to remove product from checkout
    fun removeFromCheckout(product: Product) {
        _checkoutItems.value?.remove(product)
        updatePrices()
    }

    private fun updatePrices() {
        updateTotalPrice()
        updateTotalCheckedOutItemsPrice()
    }

    // Function to update checked out items total price based on checked items
    private fun updateTotalCheckedOutItemsPrice() {
        var totalCheckedOutItemsPrice = 0
        _checkoutItems.value?.forEach { product ->
            if (product.isChecked) {
                totalCheckedOutItemsPrice += product.product_price
            }
        }
        _totalCheckedOutItemsPrice.value = totalCheckedOutItemsPrice
    }

    // Function to update total price based on checked items
    private fun updateTotalPrice() {
        var total = 0
        _checkoutItems.value?.forEach { product ->
            if (product.isChecked) {
                total += product.product_price
            }
        }
        total += deliveryCharge // Adding delivery charge
        _totalPrice.value = total
    }

    // Function to reset all products isChecked to false
    fun resetAllProducts() {
        _products.value = _products.value?.map { it.copy(isChecked = false) }
    }

    // Function to clear checkout items
    fun clearCheckoutItems() {
        _checkoutItems.value?.clear()
        _checkoutItems.postValue(_checkoutItems.value) // Trigger observers
        _totalPrice.value = 0 // Reset total price
    }
}
