package com.davidferrandiz.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidferrandiz.domain.usecases.GetShowByIdUseCase
import com.davidferrandiz.domain.usecases.GetShowsUseCase
import com.davidferrandiz.presentation.exceptions.mapper.handleExceptions
import com.davidferrandiz.presentation.toDetailUI
import com.davidferrandiz.presentation.toUI
import com.davidferrandiz.presentation.uidata.ShowDetailUI
import com.davidferrandiz.presentation.uidata.ShowListItem
import com.davidferrandiz.presentation.uidata.utils.FeedbackCreator
import com.davidferrandiz.presentation.uidata.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val getShowsUseCase: GetShowsUseCase,
    private val getShowByIdUseCase: GetShowByIdUseCase,
    private val feedbackCreator: FeedbackCreator,
) : ViewModel() {

    private val _showsStateFlow =
        MutableSharedFlow<PagingData<ShowListItem>>()
    val showsStateFlow = _showsStateFlow.asSharedFlow()

    private val _showDetailsFlow = MutableSharedFlow<Resource<ShowDetailUI>>()
    val showDetailsFlow = _showDetailsFlow.asSharedFlow()

    fun getShows() {
        viewModelScope.launch {
            getShowsUseCase()
                .cachedIn(viewModelScope)
                .map {
                    it.map { value -> value.toUI() }
                }.collect {
                    _showsStateFlow.emit(it)
                }
        }
    }

    fun goToShowDetails(id: Int) {
        viewModelScope.launch {
            _showDetailsFlow.emit(Resource.Loading())
            getShowByIdUseCase(id).catch { cause: Throwable ->
                _showDetailsFlow.emit(Resource.Error(getErrorMessage(cause as Exception)))
            }.collect {
                _showDetailsFlow.emit(Resource.Success(it.toDetailUI()))
            }
        }
    }

    fun getErrorMessage(exception: Exception): String {
        val errorType = handleExceptions(exception)
        return feedbackCreator.create(errorType)
    }
}