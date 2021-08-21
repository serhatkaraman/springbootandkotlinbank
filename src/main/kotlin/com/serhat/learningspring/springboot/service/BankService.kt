package com.serhat.learningspring.springboot.service

import com.serhat.learningspring.springboot.datasource.BankDataSource
import com.serhat.learningspring.springboot.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService (private val dataSource: BankDataSource){

    fun getBanks(): Collection<Bank> = dataSource.retreiveBanks()
    fun getBank(accountNumber: String) = dataSource.retreiveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)
    fun updateBank(bank: Bank): Bank =dataSource.updateBank(bank)
    fun deleteBank(accountNumber: String): Unit = dataSource.removeBank(accountNumber)


}