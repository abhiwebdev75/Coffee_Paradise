package com.example.coffeeparadise.utils

import com.example.coffeeparadise.data.CartItem
import com.example.coffeeparadise.data.Coffee

object CartManager {
    private val _cartItems = mutableListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems // Public immutable view

    fun addItem(coffee: Coffee, quantity: Int) {
        val existingItem = _cartItems.find { it.coffee.id == coffee.id }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            // Only add if quantity is positive
            if (quantity > 0) {
                _cartItems.add(CartItem(coffee, quantity))
            }
        }
    }

    fun removeItem(coffeeId: String) {
        _cartItems.removeAll { it.coffee.id == coffeeId }
    }

    fun clearCart() {
        _cartItems.clear()
    }

    fun getCartSubtotal(): Double {
        return _cartItems.sumOf { it.getSubtotal() }
    }

    fun getTaxAmount(taxRate: Double = 0.05): Double { // 5% GST example
        return getCartSubtotal() * taxRate
    }

    fun getTotalAmount(): Double {
        val subtotal = getCartSubtotal()
        val tax = getTaxAmount()
        return subtotal + tax
    }
}