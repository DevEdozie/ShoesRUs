package com.example.shoesrus.model

data class Product(
    val product_id: Int,
    val product_image: Int,
    val product_title: String,
    val product_price: Int,
    var isChecked: Boolean
)
