// MainActivity.kt
package com.example.coffeeparadise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeparadise.adapter.CoffeeAdapter
import com.example.coffeeparadise.data.Coffee
import com.example.coffeeparadise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var coffeeAdapter: CoffeeAdapter
    private val coffeeItems = mutableListOf<Coffee>() // Your list of coffee items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupCoffeeList()
    }

    private fun setupCoffeeList() {
        // Populate with some dummy data including image resource IDs
        // IMPORTANT: Make sure these drawables exist in your res/drawable/ folder!
        coffeeItems.add(Coffee("1", "Espresso", "Finely ground coffee, hot water", 350, R.drawable.coffee_espresso))
        coffeeItems.add(Coffee("2", "Latte", "Espresso, steamed milk, thin layer of foam", 475, R.drawable.coffee_latte))
        coffeeItems.add(Coffee("3", "Cappuccino", "Espresso, steamed milk, thick layer of foam", 490, R.drawable.coffee_cappuccino))
        coffeeItems.add(Coffee("4", "Americano", "Espresso, hot water", 380, R.drawable.coffee_americano))
        coffeeItems.add(Coffee("5", "Macchiato", "Espresso, dash of foamed milk", 420, R.drawable.coffee_machita))
        coffeeItems.add(Coffee("6", "Mocha", "Espresso, chocolate syrup, steamed milk, whipped cream", 550, R.drawable.coffee_mocha))
        coffeeItems.add(Coffee("7", "Cold Brew", "Cold water, coarsely ground coffee, long steeping time", 400, R.drawable.coffee_coldbrew))
        coffeeItems.add(Coffee("8", "Flat White", "Espresso, velvety microfoam milk", 480, R.drawable.coffee_flatwhite))


        coffeeAdapter = CoffeeAdapter(this, coffeeItems)

        // Set the callback for add to cart button clicks
        coffeeAdapter.onAddToCartClickListener = { coffee, quantity ->
            Toast.makeText(this, "Added $quantity x ${coffee.name} to cart!", Toast.LENGTH_SHORT).show()
            // Here, you would implement your actual cart logic
            // E.g., add to a global MutableList<CartItem>, update a database, etc.
            // Example: YourCartManager.addItem(coffee, quantity)
        }

        binding.coffeeListView.adapter = coffeeAdapter

        // Optional: You might still want a click listener for the whole item, separate from the button
        binding.coffeeListView.setOnItemClickListener { parent, view, position, id ->
            val clickedCoffee = coffeeItems[position]
            // This toast now reflects the *initial* quantity if not updated via buttons
            Toast.makeText(this, "Clicked on ${clickedCoffee.name} (Current Qty: ${clickedCoffee.quantity})", Toast.LENGTH_SHORT).show()
        }
    }
}