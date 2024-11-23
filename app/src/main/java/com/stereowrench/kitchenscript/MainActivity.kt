package com.stereowrench.kitchenscript

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    private lateinit var chipGroup: ChipGroup
    private lateinit var editText: AutoCompleteTextView
    private val suggestions = arrayOf("apple", "banana", "cherry", "date", "elderberry") // Sample suggestions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Assuming you have the layout set up

        chipGroup = findViewById(R.id.chip_group)
        editText = findViewById(R.id.edit_text)

        // Set up AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        editText.setAdapter(adapter)

        editText.addTextChangedListener { editable ->
            val text = editable.toString()
            if (text.endsWith(" ") && text.startsWith("@")) {
                val tokenText = text.substring(1, text.length - 1) // Extract token (remove "@" and space)
                addToken(tokenText)
                editText.text.clear()
            }

            // Filter suggestions based on input
            val filteredSuggestions = suggestions.filter { it.startsWith(text.substringAfter("@", "")) }
            adapter.clear()
            adapter.addAll(filteredSuggestions)
            adapter.notifyDataSetChanged()
        }
    }

    private fun addToken(tokenText: String) {
        val chip = Chip(this)
        chip.text = tokenText

        // Customize chip appearance
        chip.chipBackgroundColor = getColorStateList(R.color.chip_background) // Use a color resource
        chip.setTextColor(Color.WHITE)
        chip.chipStrokeWidth = 2f
        chip.chipStrokeColor = getColorStateList(R.color.chip_stroke)

        chipGroup.addView(chip)
    }
}