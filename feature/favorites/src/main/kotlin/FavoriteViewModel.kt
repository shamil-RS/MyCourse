package com.example.course.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.course.core.data.repository.MainRepository
import com.example.course.core.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val favoriteCourses: StateFlow<List<Course>> = repository.getFavoriteCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun setFavorite(courseId: Int) {
        viewModelScope.launch {
            repository.setFavorite(courseId)
        }
    }
}