package com.example.amphibians_compose_coroutines_di.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians_compose_coroutines_di.AmphibiansApplication
import com.example.amphibians_compose_coroutines_di.data.AmphibiansRepository
import com.example.amphibians_compose_coroutines_di.network.Amphibians
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY



sealed interface AmphibiansUiState {
    data class Success(val amphibiansInfo: List<Amphibians>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}


class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository): ViewModel(){

    /** The mutable State that stores the status of the most recent request */
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getAmphibiansInfo()
    }

    /**
     * Gets Amphibians information from the API Retrofit
     */
    fun getAmphibiansInfo() {
        viewModelScope.launch {
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibiousInfo())
            } catch (e: IOException) {
                AmphibiansUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiousRepository = application.container.amphibiousRepository
                AmphibiansViewModel(amphibiansRepository = amphibiousRepository)
            }
        }
    }
}