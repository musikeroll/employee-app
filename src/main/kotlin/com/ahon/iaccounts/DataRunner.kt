package com.ahon.iaccounts

import com.ahon.iaccounts.employee.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(value = ["!test"])
@Component
class DataRunner : CommandLineRunner {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @Value("\${employee.usernames}")
    private lateinit var usernames: String

    override fun run(vararg args: String?) {
        employeeService.loadData(
            usernames.split(",").toList()
        )
    }
}