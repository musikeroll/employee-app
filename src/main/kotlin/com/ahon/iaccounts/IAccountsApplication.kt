package com.ahon.iaccounts

import com.ahon.iaccounts.employee.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IAccountsApplication : CommandLineRunner {

    @Autowired
    private lateinit var employeeService: EmployeeService

    override fun run(vararg args: String?) {
        employeeService.loadData()
    }
}

fun main(args: Array<String>) {
    runApplication<IAccountsApplication>(*args)
}