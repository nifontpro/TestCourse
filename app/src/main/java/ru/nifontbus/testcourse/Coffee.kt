package ru.nifontbus.testcourse

import kotlin.random.Random

class Coffee {

    var id: Int = -1

    fun getCoffeeName(type: Int): String =
        when (type) {
            1 -> "Черный кофе"
            2 -> "Кофе с молоком"
            3 -> "Капучино"
            else -> "?"
        }
}