package mas.ca.humanprofiler.features.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase
import mas.ca.humanprofiler.utils.launchIO

class InputViewModel(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InputScreenState())
    val uiState: Flow<InputScreenState> = _uiState

    fun onNameSubmit(name: Name) {
        viewModelScope.launchIO {
            resetState()
            val result = getProfileUseCase.execute(GetProfileUseCase.Request(name))
            when (result) {
                is Result.Failure -> _uiState.update {
                    it.copy(
                        showLoadingIndicator = false,
                        error = result.error
                    )
                }

                is Result.Success -> _uiState.update {
                    it.copy(
                        showLoadingIndicator = false,
                        guessedAge = result.result.age
                    )
                }
            }
        }
    }

    private fun resetState() {
        _uiState.update {
            it.copy(
                guessedAge = null,
                showLoadingIndicator = true,
                error = null
            )
        }
    }

    companion object {
        fun provideFactory(
            getProfileUseCase: GetProfileUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(InputViewModel::class.java)) {
                    return InputViewModel(getProfileUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}