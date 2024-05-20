package com.ahon.iaccounts.employee

import com.ahon.iaccounts.entity.Employee
import com.ahon.iaccounts.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    fun loadData() {
        employeeRepository.save(Employee(username = "elvis", password = "password"))
        employeeRepository.save(Employee(username = "eminem", password = "password"))
        employeeRepository.save(Employee(username = "metallica", password = "password"))
        employeeRepository.save(Employee(username = "nirvana", password = "password"))
    }

    fun login(employee: Employee): Boolean =
        employeeRepository.findByUsernameAndPassword(
            employee.username, employee.password
        ).isPresent

    fun findAll(): List<Employee> = employeeRepository.findAll()

    fun findById(id: Int): Optional<Employee> = employeeRepository.findById(id)
}