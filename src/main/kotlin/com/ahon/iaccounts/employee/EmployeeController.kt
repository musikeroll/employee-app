package com.ahon.iaccounts.employee

import com.ahon.iaccounts.entity.Employee
import com.ahon.iaccounts.util.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employee")
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var jwtService: JwtService

    @GetMapping("/hello")
    fun helloWorld() = "Hello World"

    @PostMapping("/login")
    fun login(@RequestBody employee: Employee): ResponseEntity<String> {
        return if (employeeService.login(employee)) {

            ResponseEntity(
                jwtService.bearerHeader(employee.username),
                HttpStatus.OK
            )
        } else ResponseEntity("",HttpStatus.OK)
    }

    @GetMapping("/all")
    fun findAll(
        @RequestHeader("Authorization")
        bearerToken: String
    ): ResponseEntity<List<Employee>> =
        if (jwtService.validate(bearerToken)) {
            ResponseEntity(employeeService.findAll(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

    @GetMapping("/{id}")
    fun findAll(
        @PathVariable
        id: Int,

        @RequestHeader("Authorization")
        bearerToken: String
    ): ResponseEntity<Employee> =
        if (jwtService.validate(bearerToken)) {
            val employee = employeeService.findById(id)
            if (employee.isPresent) {
                ResponseEntity(employee.get(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.OK)
            }
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
}