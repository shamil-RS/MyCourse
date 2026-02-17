package com.example.course.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.core.data.repository.MainRepository
import com.example.course.core.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    val uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    private val courseFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val course: StateFlow<List<Course>> = courseFetchingIndex
        .flatMapLatest {
            repository.getAllCoursesFlow()
                .onStart { refreshData() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    private val _isSortByDate = MutableStateFlow(false)

    val courseList: StateFlow<List<Course>> = combine(course, _isSortByDate) { courses, isSort ->
        if (isSort) courses.sortedByDescending { it.publishDate }
        else courses
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    private fun refreshData() {
        viewModelScope.launch {
            uiState.value = HomeUiState.Loading
            try {
                repository.getCourses(
                    title = "",
                    price = 0.0,
                    rate = 0.0,
                    startDate = "",
                    hasLike = false,
                    publishDate = "",
                ).collect {
                    uiState.value = HomeUiState.Idle
                }
            } catch (e: Exception) {
                uiState.value = HomeUiState.Error(e.message)
            }
        }
    }

    fun setSortedDate() {
        _isSortByDate.value = !_isSortByDate.value
    }

    fun setFavorite(course: Course) {
        viewModelScope.launch {
            repository.setFavorite(course.id)
        }
    }
}

@Stable
sealed interface HomeUiState {

    data object Idle : HomeUiState

    data object Loading : HomeUiState

    data class Error(val message: String?) : HomeUiState
}