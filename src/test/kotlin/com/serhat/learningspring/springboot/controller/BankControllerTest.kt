package com.serhat.learningspring.springboot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.serhat.learningspring.springboot.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
    ){
    val baseUrl = "/api/banks"

    @Test
    fun`should return all banks`(){
        //when/then
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should return the bank with given account number`(){
        //given
        val accountNumber = "1234"
        //when/then
        mockMvc.get("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.trust"){value(22.9)}
                jsonPath("$.transactionFee"){value(7)}
            }

    }

    @Test
    fun `should return NOT FOUND if the given account number does not exist`(){
        //given
        val accountNumber = "does_not_exist"

        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { print() }
            . andExpect { status { isNotFound() } }
    }

    @Test
    fun `should add the new bank`(){
        //given
        val newBank = Bank("test",1.0,1)
        //when/then
        val performPost = mockMvc.post(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newBank)
        }
        //then
        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("test")}
                jsonPath("$.trust") { value("1.0")}
                jsonPath("$.transactionFee") { value("1")}
            }
    }

    @Test
    fun `should return BAD REQUEST if bank with given account number already exists`(){
        //Given
        val invaliBank = Bank("1234",22.9,7)
        //When
        val performPost = mockMvc.post(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invaliBank)
        }
        //Then
        performPost
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
    }

    @Test
    fun`should update an existing bank`(){
        //Given
        val updatedBank = Bank("1234",1.0,1)
        //When
        val performPatchRequest = mockMvc.patch(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedBank)
        }
        //Then
        performPatchRequest
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))
                }
            }
        mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
            .andExpect {
                content { json(objectMapper.writeValueAsString(updatedBank)) }
            }
    }

    @Test
    fun `should return NOT FOUND when the bank with given account number does not exist`(){
        //Given
        val invalidBank = Bank("test",1.0,1)
        //When
        val performPatch = mockMvc.patch(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)
        }
        //Then
        performPatch
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should delete an existing bank`(){
        //Given
        val accountNumber = 1234
        //When
        val performDeleteRequest = mockMvc.delete("$baseUrl/$accountNumber")
        performDeleteRequest
            .andDo { print() }
            .andExpect {
                status { isNoContent() }
            }
        //Then
        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }

}