package ru.nifontbus.testcourse.view.details

import ru.nifontbus.testcourse.view.ViewContract

internal interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}
