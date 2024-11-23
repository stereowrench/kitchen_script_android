package com.stereowrench.kitchenscript

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.chip.Chip


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TokenizedTextField() {
    var text by remember { mutableStateOf("") }
    val tokens = remember { mutableStateListOf<String>() }
    val suggestions = listOf("apple", "banana", "cherry", "date", "elderberry")
    var filteredSuggestions by remember { mutableStateOf(suggestions) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                if (it.endsWith(" ") && it.startsWith("@")) {
                    val tokenText = it.substring(1, it.length - 1)
                    tokens.add(tokenText)
                    text = ""
                }
                filteredSuggestions = suggestions.filter { suggestion ->
                    suggestion.startsWith(text.substringAfter("@", ""))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter text") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tokens) { token ->
                AssistChip (
                    onClick = { /* Handle chip click */ },
                    label = { Text(token) }
                )
            }
        }
    }
}