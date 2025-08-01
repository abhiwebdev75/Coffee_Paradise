package com.example.coffeeparadise

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeparadise.adapter.CartAdapter
import com.example.coffeeparadise.databinding.ActivityCartBinding // Correct binding for activity_cart.xml
import com.example.coffeeparadise.utils.CartManager
import java.text.NumberFormat
import java.util.Locale

class cart : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartTitle.text = "Your Cart" // Set custom title

        setupCartList()
        updateBillDetails()

        binding.paymentButton.setOnClickListener {
            if (CartManager.cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty! Add items to proceed.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Processing payment for ${String.format(Locale("en","IN"), "%.2f", CartManager.getTotalAmount())}...", Toast.LENGTH_LONG).show()
                // --- INTEGRATE YOUR PAYMENT GATEWAY HERE ---
                // This is where you would typically call a payment SDK or send details to your backend.

                // Simulate successful payment
                CartManager.clearCart()
                Toast.makeText(this, "Payment successful! Your order has been placed.", Toast.LENGTH_LONG).show()
                // After successful payment, you might navigate to an order confirmation screen
                // Or simply finish this activity and go back to the main menu
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh cart display every time the activity comes to foreground
        // This is important if items can be added/removed from other screens
        // We need to re-create the adapter to reflect changes from CartManager
        setupCartList() // Call setupCartList to re-initialize adapter and list
        updateBillDetails()
    }

    private fun setupCartList() {
        // Create a new adapter instance with the current state of CartManager.cartItems
        // This is crucial for ListView to reflect changes after items are added/removed
        cartAdapter = CartAdapter(this, CartManager.cartItems.toMutableList())
        binding.cartListView.adapter = cartAdapter

        cartAdapter.onRemoveItemClickListener = { cartItem ->
            CartManager.removeItem(cartItem.coffee.id)
            // After removal, update the adapter's data and notify
            // Re-calling setupCartList() is a simple way to refresh the entire list
            setupCartList()
            Toast.makeText(this, "${cartItem.coffee.name} removed from cart.", Toast.LENGTH_SHORT).show()
            updateBillDetails()
        }
    }

    private fun updateBillDetails() {
        val subtotal = CartManager.getCartSubtotal()
        val gst = CartManager.getTaxAmount()
        val total = CartManager.getTotalAmount()

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en","IN"))

        binding.textSubtotal.text = currencyFormat.format(subtotal)
        binding.textTax.text = currencyFormat.format(gst)
        binding.textTotal.text = currencyFormat.format(total)

        if (CartManager.cartItems.isEmpty()) {
            binding.textCartEmpty.visibility = View.VISIBLE
            binding.billDetailsCard.visibility = View.GONE
            binding.paymentButton.isEnabled = false
        } else {
            binding.textCartEmpty.visibility = View.GONE
            binding.billDetailsCard.visibility = View.VISIBLE
            binding.paymentButton.isEnabled = true
        }
    }
}