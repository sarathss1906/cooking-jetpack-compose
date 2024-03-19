package com.example.cooking.utils

import android.content.Context
import android.widget.Toast

// Function to show a toast message
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}