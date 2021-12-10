package ru.nifontbus.testcourse.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nifontbus.testcourse.model.SearchResponse
import ru.nifontbus.testcourse.presenter.RepositoryContract

internal class GitHubRepository(private val gitHubApi: GitHubApi): RepositoryContract{

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        val call = gitHubApi.searchGithub(query)
        call?.enqueue(object : Callback<SearchResponse?> {

            override fun onResponse(
                call: Call<SearchResponse?>,
                response: Response<SearchResponse?>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<SearchResponse?>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<SearchResponse?>?)
        fun handleGitHubError()
    }
}
