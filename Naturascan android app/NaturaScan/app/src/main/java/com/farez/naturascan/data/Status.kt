package com.farez.naturascan.data

sealed class Status<out R> private constructor() {
    data class Success<out T>(val data: T) : Status<T>()
    data class Error(val error: String) : Status<Nothing>()
    object Loading : Status<Nothing>()
}