package com.serhat.learningspring.springboot.datasource

import com.serhat.learningspring.springboot.model.Bank

interface BankDataSource {

    fun retreiveBanks(): Collection<Bank>
    fun retreiveBank(accuntNumber: String): Bank
    fun createBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun removeBank(accountNumber: String): Unit
}