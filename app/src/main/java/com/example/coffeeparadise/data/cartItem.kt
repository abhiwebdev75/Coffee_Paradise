package com.example.coffeeparadise.data

data class CartItem(
    val coffee: Coffee,
    var quantity: Int
) {
    fun getSubtotal(): Double {
        return coffee.price * quantity
    }
}