package com.example.course.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.core.data.repository.MainRepository
import com.example.course.core.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _courseIdFlow = MutableStateFlow<Int?>(null)
    val courseState: StateFlow<Course?> = _courseIdFlow
        .filterNotNull()
        .flatMapLatest { id ->
            repository.getCourseById(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun setCourse(id: Int) {
        _courseIdFlow.value = id
    }

    fun setFavorite() {
        val id = _courseIdFlow.value ?: return
        viewModelScope.launch {
            repository.setFavorite(id)
        }
    }
}