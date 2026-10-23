package com.example.week5.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5.model.Expense
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.viewmodel.ExpenseViewModel
import java.util.Locale

fun formatRupiah(amount: Long): String {
    val text = String.format(Locale.US, "%,d", amount)
    return "Rp " + text.replace(",", ".")
}

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Expenses",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Total Expense : " + formatRupiah(viewModel.getTotal()),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.nameInput,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Expense Name") },
            isError = viewModel.nameError != "",
            supportingText = {
                if (viewModel.nameError != "") {
                    Text(viewModel.nameError)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.Top) {
            OutlinedTextField(
                value = viewModel.amountInput,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount of Expense") },
                isError = viewModel.amountError != "",
                supportingText = {
                    if (viewModel.amountError != "") {
                        Text(viewModel.amountError)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { viewModel.addExpense() },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.expenses.size) { index ->
                val expense = viewModel.expenses[index]
                ExpenseItem(
                    expense = expense,
                    onEdit = { viewModel.openEditDialog(index) },
                    onDelete = { viewModel.deleteExpense(expense) }
                )
            }
        }
    }

    if (viewModel.showEditDialog) {
        EditAmountDialog(viewModel)
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Name : " + expense.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = formatRupiah(expense.amount),
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFFE0A800)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFC62828)
                )
            }
        }
    }
}

@Composable
fun EditAmountDialog(viewModel: ExpenseViewModel) {
    val expense = viewModel.expenses[viewModel.editIndex]

    AlertDialog(
        onDismissRequest = { viewModel.closeEditDialog() },
        title = { Text("Edit " + expense.name) },
        text = {
            OutlinedTextField(
                value = viewModel.editAmountInput,
                onValueChange = { viewModel.onEditAmountChange(it) },
                label = { Text("Amount of Expense") },
                isError = viewModel.editError != "",
                supportingText = {
                    if (viewModel.editError != "") {
                        Text(viewModel.editError)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            TextButton(onClick = { viewModel.saveEdit() }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.closeEditDialog() }) {
                Text("Cancel")
            }
        }
    )
}

fun addSampleExpenses(viewModel: ExpenseViewModel) {
    viewModel.nameInput = "Makan Nasi"
    viewModel.amountInput = "90000"
    viewModel.addExpense()

    viewModel.nameInput = "Investasi"
    viewModel.amountInput = "10000000"
    viewModel.addExpense()

    viewModel.nameInput = "Kopi"
    viewModel.amountInput = "18000"
    viewModel.addExpense()
}

@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    val viewModel = ExpenseViewModel()
    addSampleExpenses(viewModel)

    Week5Theme {
        ExpenseScreen(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseScreenErrorPreview() {
    val viewModel = ExpenseViewModel()
    addSampleExpenses(viewModel)

    viewModel.nameInput = "Error Handling"
    viewModel.amountInput = "0"
    viewModel.addExpense()

    Week5Theme {
        ExpenseScreen(viewModel = viewModel)
    }
}
