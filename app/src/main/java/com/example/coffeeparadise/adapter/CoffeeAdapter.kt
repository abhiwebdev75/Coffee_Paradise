package com.example.coffeeparadise.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast // For demonstration, you might move this to MainActivity
import com.example.coffeeparadise.data.Coffee
import com.example.coffeeparadise.databinding.ItemCoffeeBinding // Import the generated binding class!

class CoffeeAdapter(context: Context, private val coffeeList: List<Coffee>) :
    ArrayAdapter<Coffee>(context, 0, coffeeList) {

    // Callback for when "Add" button is clicked for a specific quantity
    var onAddToCartClickListener: ((Coffee, Int) -> Unit)? = null

    // We'll use the generated binding class directly within the ViewHolder
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the current coffee item
        val currentCoffee = getItem(position) ?: return View(context) // Handle null case, though unlikely

        val binding: ItemCoffeeBinding // Declare a variable for the binding object

        // Check if a recycled view (convertView) is available
        if (convertView == null) {
            // If no recycled view, inflate a new one and create a new binding
            binding = ItemCoffeeBinding.inflate(LayoutInflater.from(context), parent, false)
            // Store the binding object in the view's tag for later reuse
            binding.root.tag = binding
        } else {
            // If a recycled view exists, retrieve its stored binding object
            binding = convertView.tag as ItemCoffeeBinding
        }

        // Now, use the 'binding' object to access views directly and safely
        binding.imageCoffee.setImageResource(currentCoffee.imageResId)
        binding.textCoffeeName.text = currentCoffee.name
        binding.textIngredients.text = currentCoffee.ingredients
        binding.textPrice.text = String.format("$%.2f", currentCoffee.price)
        binding.textQuantity.text = currentCoffee.quantity.toString()

        // Set up click listeners for quantity controls and Add button
        binding.buttonMinus.setOnClickListener {
            if (currentCoffee.quantity > 1) { // Decrease, but not below 1
                currentCoffee.quantity--
                notifyDataSetChanged() // Refresh the ListView item to show new quantity
            }
        }

        binding.buttonPlus.setOnClickListener {
            if (currentCoffee.quantity < 10) { // Increase, but not above 10
                currentCoffee.quantity++
                notifyDataSetChanged() // Refresh the ListView item to show new quantity
            }
        }

        binding.buttonAddToCart.setOnClickListener { _ ->
            // Pass the coffee object and its current quantity via the callback
            onAddToCartClickListener?.invoke(currentCoffee, currentCoffee.quantity)
            // Optionally, you might want to reset the quantity to 1 after adding to cart
            // currentCoffee.quantity = 1
            // notifyDataSetChanged()
        }

        return binding.root // Return the root view from the binding object
    }
    // With View Binding, you no longer need the private ViewHolder class
    // as the binding object itself provides direct access to the views.
}