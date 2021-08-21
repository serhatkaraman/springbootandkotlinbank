package com.serhat.learningspring.springboot.datasource.mock


import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private val mockDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`(){
        //given

        //when
        val banks = mockDataSource.retreiveBanks()
        //then
        assertThat(banks).isNotEmpty

    }

    @Test
    fun `should provide some mock data`(){
        //when
        val banks = mockDataSource.retreiveBanks()
        //then
        assertThat(banks).allSatisfy { it.accountNumber.isNotBlank() }
    }

}