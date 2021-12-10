package ru.nifontbus.testcourse.repository

import retrofit2.Response
import ru.nifontbus.testcourse.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}
