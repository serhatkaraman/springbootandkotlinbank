package com.serhat.learningspring.springboot.model

data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int)