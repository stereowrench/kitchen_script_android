package com.stereowrench.kitchenscript

import android.R
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.chip.Chip


@Preview(showBackground = true)
@Composable
fun TokenizedTextFieldPreview() {
    TokenizedTextField(context = LocalContext.current)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun TokenizedTextField(context: Context) {
    var text by remember { mutableStateOf("") }
    val tokens = remember { mutableStateListOf<String>() }
    val suggestions = listOf("apple", "banana", "cherry", "date", "elderberry")
    var filteredSuggestions by remember { mutableStateOf(suggestions) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // AutoCompleteTextView in Compose
        var textFieldValue by remember { mutableStateOf("") }
        val autoCompleteAdapter = remember {
            ArrayAdapter<String>(
                context,
                R.layout.simple_dropdown_item_1line,
                mutableListOf()
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AutoCompleteTextView(context).apply {
                    setAdapter(autoCompleteAdapter)
                    addTextChangedListener(object : TextWatcher {
                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            textFieldValue = s.toString()
                            if (textFieldValue.endsWith(" ") && textFieldValue.startsWith("@")) {
                                val tokenText =
                                    textFieldValue.substring(1, textFieldValue.length - 1)
                                tokens.add(tokenText)
                                textFieldValue = ""
                                this@apply.setText("")
                            }
                            filteredSuggestions = suggestions.filter { suggestion ->
                                suggestion.startsWith(textFieldValue.substringAfter("@", ""))
                            }
                            autoCompleteAdapter.clear()
                            autoCompleteAdapter.addAll(filteredSuggestions)
                            autoCompleteAdapter.notifyDataSetChanged()
                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {}
                    })
                }
            },
            update = { autoCompleteTextView ->
                autoCompleteTextView.setText(textFieldValue)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tokens)
            { token ->
                AssistChip(
                    onClick = { /* Handle chip click */ },
                    label = { Text(token) }
                )
            }
        }
//        TextField(
//            value = text,
//            onValueChange = {
//                text = it
//                if (it.endsWith(" ") && it.startsWith("@")) {
//                    val tokenText = it.substring(1, it.length - 1)
//                    tokens.add(tokenText)
//                    text = ""
//                }
//                filteredSuggestions = suggestions.filter { suggestion ->
//                    suggestion.startsWith(text.substringAfter("@", ""))
//                }
//            },
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text("Enter text") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            )
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(tokens) { token ->
//                AssistChip (
//                    onClick = { /* Handle chip click */ },
//                    label = { Text(token) }
//                )
//            }
//        }
    }
}