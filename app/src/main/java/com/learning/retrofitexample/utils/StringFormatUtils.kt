package com.learning.retrofitexample.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency

fun Long.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("USD")
    return format.format(this)
}