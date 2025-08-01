package com.example.coffeeparadise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeparadise.adapter.CoffeeAdapter
import com.example.coffeeparadise.data.Coffee
import com.example.coffeeparadise.databinding.ActivityMainBinding
import com.example.coffeeparadise.utils.CartManager // Import CartManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var coffeeAdapter: CoffeeAdapter
    private val coffeeItems = mutableListOf<Coffee>() // Your list of coffee items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupCoffeeList()

        // Set up click listener for the new View Cart button
        binding.viewCartButton.setOnClickListener {
            val intent = Intent(this, cart::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // It's good practice to refresh the list or quantities if you navigate back
        // from cart and want to reflect potential changes (e.g., if you reset quantities after adding)
        coffeeAdapter.notifyDataSetChanged() // Refresh quantities shown in MainActivity
    }

    private fun setupCoffeeList() {
        // Populate with some dummy data including image resource IDs
        // IMPORTANT: Make sure these drawables exist in your res/drawable/ folder!
        coffeeItems.add(Coffee("1", "Espresso", "Finely ground coffee, hot water", 335.50, R.drawable.coffee_espresso))
        coffeeItems.add(Coffee("2", "Latte", "Espresso, steamed milk, thin layer of foam", 410.75, R.drawable.coffee_latte))
        coffeeItems.add(Coffee("3", "Cappuccino", "Espresso, steamed milk, thick layer of foam", 450.90, R.drawable.coffee_cappuccino))
        coffeeItems.add(Coffee("4", "Americano", "Espresso, hot water", 330.80, R.drawable.coffee_americano))
        coffeeItems.add(Coffee("5", "Macchiato", "Espresso, dash of foamed milk", 440.20, R.drawable.coffee_machita))
        coffeeItems.add(Coffee("6", "Mocha", "Espresso, chocolate syrup, steamed milk, whipped cream", 500.50, R.drawable.coffee_mocha))
        coffeeItems.add(Coffee("7", "Cold Brew", "Cold water, coarsely ground coffee, long steeping time", 450.00, R.drawable.coffee_coldbrew))
        coffeeItems.add(Coffee("8", "Flat White", "Espresso, velvety microfoam milk", 410.80, R.drawable.coffee_flatwhite))


        coffeeAdapter = CoffeeAdapter(this, coffeeItems)

        coffeeAdapter.onAddToCartClickListener = { coffee, quantity ->
            // Add the selected coffee with its quantity to the CartManager
            CartManager.addItem(coffee, quantity)
            Toast.makeText(this, "Added $quantity x ${coffee.name} to cart!", Toast.LENGTH_SHORT).show()
            // Optionally, reset the quantity in the UI after adding to cart
            // coffee.quantity = 1 // This would reset the quantity on the UI for that item
            // coffeeAdapter.notifyDataSetChanged() // Required if you reset quantity
        }

        binding.coffeeListView.adapter = coffeeAdapter

        binding.coffeeListView.setOnItemClickListener { parent, view, position, id ->
            val clickedCoffee = coffeeItems[position]
            Toast.makeText(this, "Clicked on ${clickedCoffee.name} (Current Qty: ${clickedCoffee.quantity})", Toast.LENGTH_SHORT).show()
        }
    }
}