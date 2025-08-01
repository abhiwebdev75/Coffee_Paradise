package com.example.coffeeparadise.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffeeparadise.data.CartItem
import com.example.coffeeparadise.databinding.ItemCartCoffeeBinding // Generated binding
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(context: Context, private val cartItems: MutableList<CartItem>) :
    ArrayAdapter<CartItem>(context, 0, cartItems) {

    var onRemoveItemClickListener: ((CartItem) -> Unit)? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentCartItem = getItem(position) ?: return View(context)

        val binding: ItemCartCoffeeBinding

        if (convertView == null) {
            binding = ItemCartCoffeeBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemCartCoffeeBinding
        }

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en","IN"))

        binding.cartItemImage.setImageResource(currentCartItem.coffee.imageResId)
        binding.cartItemName.text = currentCartItem.coffee.name
        binding.cartItemQuantityAndPrice.text =
            "Qty: ${currentCartItem.quantity} x ${currencyFormat.format(currentCartItem.coffee.price)}"
        binding.cartItemSubtotal.text = currencyFormat.format(currentCartItem.getSubtotal())

        binding.buttonRemoveFromCart.setOnClickListener {
            onRemoveItemClickListener?.invoke(currentCartItem)
        }

        return binding.root
    }
}