package com.example.coffeeparadise.data


data class Coffee(
    val id: String,
    val name: String,
    val ingredients: String,
    val price: Int,
    val imageResId: Int,
    var quantity: Int = 1
)