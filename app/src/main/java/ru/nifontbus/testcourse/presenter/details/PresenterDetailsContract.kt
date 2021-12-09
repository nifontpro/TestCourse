package ru.nifontbus.testcourse.presenter.details

import ru.nifontbus.testcourse.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}
