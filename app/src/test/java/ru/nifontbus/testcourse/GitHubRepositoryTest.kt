package ru.nifontbus.testcourse

import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nifontbus.testcourse.model.SearchResponse
import ru.nifontbus.testcourse.repository.GitHubApi
import ru.nifontbus.testcourse.repository.GitHubRepository

class GitHubRepositoryTest {
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchRepos() {
        val searchQuery = "some query"
        val call = mock(Call::class.java) as Call<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)
        repository.searchGithub(searchQuery, mock(GitHubRepository.GitHubRepositoryCallback::class.java))
        verify(gitHubApi, times(1)).searchGithub(searchQuery)
    }

    @Test
    fun searchGithub_TestCallback() {
        val searchQuery = "some query"
        val response = mock(Response::class.java) as Response<SearchResponse?>
        val gitHubRepositoryCallBack = mock(GitHubRepository.GitHubRepositoryCallback::class.java)

        val call = object : Call<SearchResponse?> {
            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        }

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)
        repository.searchGithub(searchQuery, gitHubRepositoryCallBack)

        verify(gitHubRepositoryCallBack, times(1)).handleGitHubResponse(response)
        verify(gitHubRepositoryCallBack, times(1)).handleGitHubError()
    }

    @Test // Не проходит
    fun searchGithub_TestCallback_WithMock() {
        val searchQuery = "some query"
        val call = mock(Call::class.java) as Call<SearchResponse?>
        val callBack = mock(Callback::class.java) as Callback<SearchResponse?>
        val gitHubRepositoryCallBack = mock(GitHubRepository.GitHubRepositoryCallback::class.java)
        val response = mock(Response::class.java) as Response<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)
        `when`(call.enqueue(callBack)).then {
            callBack.onResponse(any(), any())
        }
        `when`(callBack.onResponse(any(), any())).then {
            gitHubRepositoryCallBack.handleGitHubResponse(response)
        }

        repository.searchGithub(searchQuery, gitHubRepositoryCallBack)

        verify(gitHubRepositoryCallBack, times(1)).handleGitHubResponse(response)
    }

}