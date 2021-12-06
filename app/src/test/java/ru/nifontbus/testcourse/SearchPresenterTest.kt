package ru.nifontbus.testcourse

import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.nifontbus.testcourse.model.SearchResponse
import ru.nifontbus.testcourse.model.SearchResult
import ru.nifontbus.testcourse.presenter.search.SearchPresenter
import ru.nifontbus.testcourse.repository.GitHubRepository
import ru.nifontbus.testcourse.view.search.ViewSearchContract

class SearchPresenterTest {
    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this) // initMocks(this) is Deprecated
        presenter = SearchPresenter(viewContract, repository)
    }

    @Test //Проверим вызов метода searchGitHub() у нашего Репозитория
    fun searchGitHub_Test() {
        val searchQuery = "some query"
        //Запускаем код, функционал которого хотим протестировать
        presenter.searchGitHub(searchQuery)
        //Убеждаемся, что все работает как надо
        verify(repository, Mockito.times(1))
            .searchGithub(searchQuery, presenter)
    }

    @Test //Проверяем работу метода handleGitHubError()
    fun handleGitHubError_Test() {
        //Вызываем у Презентера метод handleGitHubError()
        presenter.handleGitHubError()
        //Проверяем, что у viewContract вызывается метод displayError()
        verify(viewContract, Mockito.times(1)).displayError()
    }

    @Test //Для начала проверим, как приходит ответ сервера
    fun handleGitHubResponse_ResponseUnsuccessful() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?
        val response = mock(Response::class.java) as Response<SearchResponse?>
        //Описываем правило, что при вызове метода isSuccessful должен возвращаться false
        `when`(response.isSuccessful).thenReturn(false)
        //Убеждаемся, что ответ действительно false
        assertFalse(response.isSuccessful)
    }

    @Test //Теперь проверим, как у нас обрабатываются ошибки
    fun handleGitHubResponse_Failure() {
        //Создаем мок ответа сервера с типом Response<SearchResponse?>?
        val response = mock(Response::class.java) as Response<SearchResponse?>
        //Описываем правило, что при вызове метода isSuccessful должен возвращаться false
        //В таком случае должен вызываться метод viewContract.displayError(...)
        `when`(response.isSuccessful).thenReturn(false)

        //Вызывваем у Презентера метод handleGitHubResponse()
        presenter.handleGitHubResponse(response)

        //Убеждаемся, что вызывается верный метод: viewContract.displayError("Response is null or unsuccessful"), и что он вызывается единожды
        verify(viewContract, times(1))
            .displayError("Response is null or unsuccessful")
    }

    @Test //Проверим пустой ответ сервера
    fun handleGitHubResponse_ResponseIsEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.body()).thenReturn(null)
        //Вызываем handleGitHubResponse()
        presenter.handleGitHubResponse(response)
        //Убеждаемся, что body действительно null
        assertNull(response.body())
    }

    @Test //Теперь проверим непустой ответ сервера
    fun handleGitHubResponse_ResponseIsNotEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.body()).thenReturn(mock(SearchResponse::class.java))
        //Вызываем handleGitHubResponse()
        presenter.handleGitHubResponse(response)
        //Убеждаемся, что body действительно null
        assertNotNull(response.body())
    }

    @Test //Проверим как обрабатывается случай, если ответ от сервера пришел пустой
    fun handleGitHubResponse_EmptyResponse() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        //Устанавливаем правило, что ответ успешный
        `when`(response.isSuccessful).thenReturn(true)
        //При этом body ответа == null. В таком случае должен вызываться метод viewContract.displayError(...)
        `when`(response.body()).thenReturn(null)

        //Вызываем handleGitHubResponse()
        presenter.handleGitHubResponse(response)

        //Убеждаемся, что вызывается верный метод: viewContract.displayError("Search results or total count are null"), и что он вызывается единожды
        verify(viewContract, Mockito.times(1))
            .displayError("Search results or total count are null")
    }

    @Test //Пришло время проверить успешный ответ, так как все остальные случаи мы уже покрыли тестами
    fun handleGitHubResponse_Success() {
        //Мокаем ответ
        val response = mock(Response::class.java) as Response<SearchResponse?>
        //Мокаем тело ответа
        val searchResponse = mock(SearchResponse::class.java)
        //Мокаем список
        val searchResults = listOf(mock(SearchResult::class.java))

        //Прописываем правила
        //Когда ответ приходит, возвращаем response.isSuccessful == true и остальные данные в процессе выполнения метода handleGitHubResponse
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(searchResponse)
        `when`(searchResponse.searchResults).thenReturn(searchResults)
        `when`(searchResponse.totalCount).thenReturn(101)

        //Запускаем выполнение метода
        presenter.handleGitHubResponse(response)

        //Убеждаемся, что ответ от сервера обрабатывается корректно
        verify(viewContract, times(1)).displaySearchResults(searchResults, 101)
    }

    @Test //Проверим порядок вызова методов viewContract
    fun handleGitHubResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)

        //Определяем порядок вызова методов какого класса мы хотим проверить
        val inOrder = inOrder(viewContract)
        //Прописываем порядок вызова методов
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError("Response is null or unsuccessful")
    }

}