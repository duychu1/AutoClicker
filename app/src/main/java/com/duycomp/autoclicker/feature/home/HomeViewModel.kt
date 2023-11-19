package com.duycomp.autoclicker.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.autoclicker.data.UserDataRepositoryImpl
import com.duycomp.autoclicker.datastore.PreferencesDataSource
import com.duycomp.autoclicker.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepositoryImpl
): ViewModel() {
    fun onDurationClickChange(value: Long) = viewModelScope.launch {
        userDataRepository.setDurationClick(value)
    }

    fun onIntervalClickChange(value: Long) = viewModelScope.launch {
        userDataRepository.setIntervalClick(value)
    }

    fun onInfinityLoopChange(value: Boolean) = viewModelScope.launch {
        userDataRepository.setInfinityLoop(value)
    }

    fun onLoopChange(value: Int) = viewModelScope.launch {
        userDataRepository.setLoop(value)
    }

    val userDataUiState: StateFlow<UserDataUiState> =
        userDataRepository.userData.map { userData ->
            UserDataUiState.Success (
                userData = userData
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserDataUiState.Loading,
    )

}

sealed interface UserDataUiState {
    data object Loading : UserDataUiState

    data class Success(
        val userData: UserData,
    ) : UserDataUiState

}