package ru.nifontbus.testcourse.view.search

import ru.nifontbus.testcourse.model.SearchResult

internal interface ViewSearchContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
