package com.serhat.learningspring.springboot.datasource.mock

import com.serhat.learningspring.springboot.datasource.BankDataSource
import com.serhat.learningspring.springboot.model.Bank
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.NoSuchElementException


@Repository
class MockBankDataSource : BankDataSource{

    var bankList = mutableListOf<Bank>(
        Bank("sd4f56sdf",75.6,6),
        Bank("1234",22.9,7))

    override fun retreiveBanks(): Collection<Bank> = bankList
    override fun retreiveBank(accountNumber: String) = bankList.first{it.accountNumber ==accountNumber}
    override fun createBank(bank: Bank): Bank {
        if(bankList.any{ it.accountNumber == bank.accountNumber})
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} is already exists")
        bankList.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = bankList.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNumber}")
        bankList.remove(currentBank)
        bankList.add(bank)
        return bank
    }

    override fun removeBank(accountNumber: String): Unit {
        val currentBank = bankList.firstOrNull{ it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
        bankList.remove(currentBank)
    }

}