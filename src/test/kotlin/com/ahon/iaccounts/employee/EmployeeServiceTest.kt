package com.ahon.iaccounts.employee

import com.ahon.iaccounts.entity.Employee
import com.ahon.iaccounts.repository.EmployeeRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class EmployeeServiceTest {

    private lateinit var repository: EmployeeRepository
    private lateinit var service: EmployeeService

    @BeforeEach
    fun setUp() {
        repository = mockk()
        service = EmployeeService(repository)
    }

    @Test
    fun `load users empty list`() {

        val usernames = emptyList<String>()
        val users = emptyList<Employee>()

        every {
            repository.saveAll(users)
        } returns users

        assertEquals(service.loadData(usernames), users)
    }

    @Test
    fun `load users multiple items`() {

        val loaders = EmployeeRobot.loader()
        val users = EmployeeRobot.multiple()

        every {
            repository.saveAll(loaders)
        } returns users

        assertEquals(service.loadData(EmployeeRobot.usernames()), users)
    }

    @Test
    fun `login empty parameters`() {

        val employee = Employee()

        every {
            repository.findByUsernameAndPassword(employee.username, employee.password)
        } returns Optional.empty()

        assertFalse(service.login(employee))
    }

    @Test
    fun `login credentials not found`() {

        val employee = EmployeeRobot.single()

        every {
            repository.findByUsernameAndPassword(employee.username, employee.password)
        } returns Optional.empty()

        assertFalse(service.login(employee))
    }

    @Test
    fun `login credentials found`() {

        val employee = EmployeeRobot.single()

        every {
            repository.findByUsernameAndPassword(employee.username, employee.password)
        } returns Optional.of(employee)

        assertTrue(service.login(employee))
    }

    @Test
    fun `findAll found empty`() {

        every {
            repository.findAll()
        } returns emptyList()

        assertEquals(service.findAll(), emptyList<Employee>())
    }

    @Test
    fun `findAll found records`() {

        val employees = EmployeeRobot.multiple()

        every {
            repository.findAll()
        } returns employees

        assertEquals(service.findAll(), employees)
    }

    @Test
    fun `findById found nothing`() {

        val employee = Employee()

        every {
            repository.findById(1)
        } returns Optional.empty()

        assertEquals(service.findById(1), Optional.empty<Employee>())
    }

    @Test
    fun `findById found record`() {

        val employee = EmployeeRobot.single()

        every {
            repository.findById(1)
        } returns Optional.of(employee)

        assertEquals(service.findById(1), Optional.of(employee))
    }
}