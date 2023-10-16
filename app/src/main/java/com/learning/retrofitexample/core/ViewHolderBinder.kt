package com.learning.retrofitexample.core

interface ViewHolderBinder<T> {
    fun bind(item : T)
}