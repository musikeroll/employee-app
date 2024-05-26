package com.ahon.iaccounts.employee

import com.ahon.iaccounts.util.JwtService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var service: EmployeeService

    @MockkBean
    private lateinit var jwtService: JwtService

    @Test
    fun `login with credentials not found - 200 OK`() {

        every {
            service.login(EmployeeRobot.single())
        } returns false

        mockMvc.perform(
            post("/employee/login")
                .content(objectMapper.writeValueAsString(EmployeeRobot.single()))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `login with credentials found, header created - 200 OK`() {

        every {
            service.login(EmployeeRobot.single())
        } returns true

        every {
            jwtService.generateToken(EmployeeRobot.single().username)
        } returns EmployeeRobot.jwtToken()

        mockMvc.perform(
            post("/employee/login")
                .content(objectMapper.writeValueAsString(EmployeeRobot.single()))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer ${EmployeeRobot.jwtToken()}"))
    }

    @Test
    fun `findAll with invalid token - 401 UNAUTHORIZED`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns false

        mockMvc.perform(
            get("/employee/all")
                .headers(httpHeaders)
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `findAll with empty result - 200 OK`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns true

        every {
            service.findAll()
        } returns emptyList()

        mockMvc.perform(
            get("/employee/all")
                .headers(httpHeaders)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `findAll with result contents - 200 OK`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns true

        every {
            service.findAll()
        } returns EmployeeRobot.multiple()

        mockMvc.perform(
            get("/employee/all")
                .headers(httpHeaders)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `findById with invalid token - 401 UNAUTHORIZED`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns false

        mockMvc.perform(
            get("/employee/1")
                .headers(httpHeaders)
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `findById with empty result - 200 OK`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns true

        every {
            service.findById(1)
        } returns Optional.empty()

        mockMvc.perform(
            get("/employee/1")
                .headers(httpHeaders)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `findById with result contents - 200 OK`() {

        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(
            EmployeeRobot.jwtToken()
        )

        every {
            jwtService.validate("Bearer ${EmployeeRobot.jwtToken()}")
        } returns true

        every {
            service.findById(1)
        } returns Optional.of(EmployeeRobot.single())

        mockMvc.perform(
            get("/employee/1")
                .headers(httpHeaders)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}