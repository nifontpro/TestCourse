package ru.nifontbus.testcourse.presenter

import ru.nifontbus.testcourse.repository.RepositoryCallback


internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
