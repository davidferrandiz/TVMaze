package com.tvmaze.davidferrandiz.vm

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.domain.usecases.GetShowByIdUseCase
import com.davidferrandiz.domain.usecases.GetShowsUseCase
import com.davidferrandiz.presentation.uidata.ShowDetailUI
import com.davidferrandiz.presentation.uidata.utils.FeedbackCreator
import com.davidferrandiz.presentation.uidata.utils.FeedbackType
import com.davidferrandiz.presentation.uidata.utils.Resource
import com.davidferrandiz.presentation.vm.ShowsViewModel
import com.tvmaze.davidferrandiz.fakeEntityPaging
import com.tvmaze.davidferrandiz.fakeShows
import com.tvmaze.davidferrandiz.fakeUIPaging
import com.tvmaze.davidferrandiz.fakeUIShows
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShowsViewModelTest {

    private lateinit var viewModel: ShowsViewModel
    private val getShowsUseCase: GetShowsUseCase = mockk()
    private val getShowByIdUseCase: GetShowByIdUseCase = mockk()
    private val mockFeedbackCreator: FeedbackCreator = mockk()

    @Before
    fun setup() {
        viewModel =
            ShowsViewModel(
                getShowsUseCase,
                getShowByIdUseCase,
                mockFeedbackCreator
            )
    }

    @Test
    fun `getShows - returning ui list item when usecase is success`() {
        runBlockingTest {
            coEvery {
                getShowsUseCase()
            } returns fakeEntityPaging

            viewModel.getShows()

            val job = launch {
                viewModel.showsStateFlow.first()

                Assert.assertEquals(viewModel.showsStateFlow.first(), fakeUIPaging.first())
            }

            job.cancel()
        }
    }


    @Test
    fun `verify getShowById use case emits result with data`() {
        runBlockingTest {

            coEvery {
                getShowByIdUseCase(0)
            } returns flow { fakeShows[0] }

            val job = launch {
                viewModel.goToShowDetails(0)

                val realResult = viewModel.showDetailsFlow.single()
                val expectedResult = Resource.Success(fakeUIShows[0])

                Assert.assertEquals(expectedResult, realResult)
            }

            job.cancel()
        }
    }

    @Test
    fun `verify getShowById use case return an error`() {
        runBlockingTest {

            val fakeFeedback = "Error"

            coEvery {
                getShowByIdUseCase(0)
            } throws Exception()

            coEvery {
                mockFeedbackCreator.create(FeedbackType.GENERIC_EMPTY_VIEW)
            } returns fakeFeedback

            val job = launch {
                viewModel.goToShowDetails(0)

                val realResult = viewModel.showDetailsFlow.single()
                val expectedResult = Resource.Error<ShowDetailUI>(fakeFeedback)

                Assert.assertEquals(expectedResult, realResult)
            }

            job.cancel()
        }
    }
}