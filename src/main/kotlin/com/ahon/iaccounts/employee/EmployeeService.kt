package com.ahon.iaccounts.employee

import com.ahon.iaccounts.entity.Employee
import com.ahon.iaccounts.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(
    @Autowired private val employeeRepository: EmployeeRepository
) {

    fun loadData(usernames: List<String>): List<Employee> =
        employeeRepository.saveAll(
            usernames.map {
                Employee(username = it, password = "password")
            }
        )

    fun login(employee: Employee): Boolean =
        employeeRepository.findByUsernameAndPassword(
            employee.username, employee.password
        ).isPresent

    fun findAll(): List<Employee> = employeeRepository.findAll()

    fun findById(id: Int): Optional<Employee> = employeeRepository.findById(id)
}