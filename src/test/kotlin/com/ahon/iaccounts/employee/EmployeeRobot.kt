package com.ahon.iaccounts.employee

import com.ahon.iaccounts.entity.Employee

object EmployeeRobot {

    fun jwtToken() = "so/vJkw4zsSi3arx8Cw9K/o5XH70ANT768KMHunZFcg="

    fun usernames() = listOf("elvis", "eminem", "metallica", "nirvana")

    fun single() = Employee(1, "first", "First Employee")

    fun multiple() = listOf(
        Employee(1, "first", "First Employee"),
        Employee(2, "middle", "Middle Employee"),
        Employee(3, "last", "Last Employee")
    )

    fun loader() = listOf(
        Employee(0, "elvis", "password"),
        Employee(0, "eminem", "password"),
        Employee(0, "metallica", "password"),
        Employee(0, "nirvana", "password"),
    )
}