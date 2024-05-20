package com.ahon.iaccounts.repository

import com.ahon.iaccounts.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmployeeRepository : JpaRepository<Employee, Int> {

    fun findByUsernameAndPassword(username: String, password: String): Optional<Employee>
}