package com.mapstest

import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.mapstest.data.RetrofitStuff
import com.mapstest.data.RetrofitStuff.RetrofitHelper.Result
import com.mapstest.data.RetrofitStuff.RetrofitHelper.Artist

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Result())
    val uiState: StateFlow<Result> = _uiState.asStateFlow()
    //called on login and read when map canges
    fun updateUserData(updatedUserData: Result){
        _uiState.update { currentState ->
            currentState.copy(
                updatedUserData.artists,
                updatedUserData.user
            )
        }
    }
    //TESTING: adding artists via subscription
    fun addArtist(addedArtist: Artist){
        _uiState.update{ it.copy(
            it.artists.apply {
                it.artists.add(addedArtist)
            },
            it.user)
        }

    }
}