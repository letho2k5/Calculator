package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Calculators()
            }
        }
    }
}

@Composable
fun Calculators() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("+") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Nhập số thứ nhất") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Nhập số thứ hai") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown chọn phép toán
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Phép toán: $selectedOperation")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf("+", "-", "×", "÷").forEach { operation ->
                    DropdownMenuItem(
                        text = { Text(operation) },
                        onClick = {
                            selectedOperation = operation
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút tính toán
        Button(
            onClick = {
                val num1 = number1.toDoubleOrNull()
                val num2 = number2.toDoubleOrNull()
                if (num1 == null || num2 == null) {
                    result = "Lỗi: Nhập số hợp lệ!"
                    return@Button
                }

                result = when (selectedOperation) {
                    "+" -> (num1 + num2).toString()
                    "-" -> (num1 - num2).toString()
                    "×" -> (num1 * num2).toString()
                    "÷" -> if (num2 != 0.0) (num1 / num2).toString() else "Lỗi: Chia cho 0!"
                    else -> "Lỗi!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tính toán")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kết quả: $result",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        Calculators()
    }
}
