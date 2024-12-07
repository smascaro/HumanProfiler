package mas.ca.humanprofiler.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.entities.Sorting
import mas.ca.humanprofiler.domain.use_cases.GetAllProfilesUseCase
import mas.ca.humanprofiler.utils.launchIO

class HistoryViewModel(
    private val getAllProfilesUseCase: GetAllProfilesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryScreenState())
    val uiState: Flow<HistoryScreenState> = _uiState

    init {
        updateProfilesList()
    }

    private fun updateProfilesList() {
        viewModelScope.launchIO {
            _uiState.update { it.copy(showLoadingIndicator = true) }
            val result = getAllProfilesUseCase.execute(GetAllProfilesUseCase.Request(_uiState.value.sortedBy))
            _uiState.update { it.copy(showLoadingIndicator = false) }
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(profiles = result.result) }
                }

                is Result.Failure -> {
                    _uiState.update { it.copy(showError = true) }
                }
            }
        }
    }

    fun onSortingClick() {
        _uiState.update { it.copy(showSortingChooser = true) }
    }

    fun onSortingChange(sortingType: Sorting.Type) {
        if (sortingType == _uiState.value.sortedBy.type) {
            _uiState.update { it.copy(sortedBy = it.sortedBy.copy(direction = it.sortedBy.direction.flip())) }
        } else {
            _uiState.update { it.copy(sortedBy = Sorting(sortingType, _uiState.value.sortedBy.direction)) }
        }
        updateProfilesList()
    }

    fun onSortingSelectorDismissed() {
        _uiState.update { it.copy(showSortingChooser = false) }
    }

    companion object {
        fun provideFactory(
            getAllProfilesUseCase: GetAllProfilesUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                        return HistoryViewModel(getAllProfilesUseCase) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}