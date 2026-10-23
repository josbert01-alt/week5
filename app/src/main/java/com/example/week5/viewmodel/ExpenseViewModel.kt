package com.example.week5.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5.model.Expense

class ExpenseViewModel : ViewModel() {

    val expenses = mutableStateListOf<Expense>()

    var nameInput by mutableStateOf("")
    var amountInput by mutableStateOf("")

    var nameError by mutableStateOf("")
    var amountError by mutableStateOf("")

    var showEditDialog by mutableStateOf(false)
    var editIndex by mutableStateOf(-1)
    var editAmountInput by mutableStateOf("")
    var editError by mutableStateOf("")

    private var nextId = 1

    fun onNameChange(text: String) {
        nameInput = text
        nameError = ""
    }

    fun onAmountChange(text: String) {
        amountInput = text
        amountError = ""
    }

    fun addExpense() {
        val amount = amountInput.toLongOrNull()
        var valid = true

        if (nameInput.trim() == "") {
            nameError = "Name cannot be empty"
            valid = false
        }

        if (amount == null) {
            amountError = "Amount must be a number"
            valid = false
        } else if (amount <= 0) {
            amountError = "Amount must be greater than 0"
            valid = false
        }

        if (valid == false) {
            return
        }

        expenses.add(Expense(nextId, nameInput.trim(), amount!!))
        nextId = nextId + 1

        nameInput = ""
        amountInput = ""
    }

    fun deleteExpense(expense: Expense) {
        expenses.remove(expense)
    }

    fun getTotal(): Long {
        var total: Long = 0
        for (expense in expenses) {
            total = total + expense.amount
        }
        return total
    }

    fun openEditDialog(index: Int) {
        editIndex = index
        editAmountInput = expenses[index].amount.toString()
        editError = ""
        showEditDialog = true
    }

    fun onEditAmountChange(text: String) {
        editAmountInput = text
        editError = ""
    }

    fun saveEdit() {
        val newAmount = editAmountInput.toLongOrNull()

        if (newAmount == null) {
            editError = "Amount must be a number"
            return
        }
        if (newAmount <= 0) {
            editError = "Amount must be greater than 0"
            return
        }

        val oldExpense = expenses[editIndex]
        expenses[editIndex] = Expense(oldExpense.id, oldExpense.name, newAmount)

        closeEditDialog()
    }

    fun closeEditDialog() {
        showEditDialog = false
        editIndex = -1
        editAmountInput = ""
        editError = ""
    }
}
