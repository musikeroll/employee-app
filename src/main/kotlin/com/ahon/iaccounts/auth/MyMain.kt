package com.ahon.iaccounts.auth

fun main() {

    val string = "The Best Romantic Movies of All Time"

    var longest = ""
    string.split(" ")
        .forEach {
            if (it.length > longest.length) {
                longest = it
            }
        }

    println("The Best Romantic Movies of All Time")
    println("Longest Word: $longest")

    longest = string.split(" ")
        .maxBy { it.length }

    println("Longest Word.v2: $longest")

    val maxValue = 20
    val setOne = Runnable {
        val initialValue1 = 2
        for (index: Int in 1..maxValue) {
            println("${Thread.currentThread().name} - $initialValue1 x $index = ${initialValue1.times(index)}")
        }
    }

    val setTwo = Runnable {
        val initialValue2: Int = 3
        for (index: Int in 1..maxValue) {
            println("${Thread.currentThread().name} - $initialValue2 x $index = ${initialValue2.times(index)}")
        }
    }

    Thread(setOne, "John").start()
    Thread(setTwo, "Wick").start()

    val array = arrayOf(52311, 52311, 55125, 55125, 52128, 52130, 52128)
    println("RoRR ${array.distinct()}")
}