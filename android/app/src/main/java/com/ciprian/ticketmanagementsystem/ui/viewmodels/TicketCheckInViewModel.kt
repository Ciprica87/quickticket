package com.ciprian.ticketmanagementsystem.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciprian.ticketmanagementsystem.data.models.TicketCheckInDTO
import com.ciprian.ticketmanagementsystem.data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ScanState {
    object Scanning : ScanState()
    object Stopped : ScanState()
}

@HiltViewModel
class TicketCheckInViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _checkInResult = MutableStateFlow<Result<TicketCheckInDTO>?>(null)
    val checkInResult: StateFlow<Result<TicketCheckInDTO>?> = _checkInResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun validateTicket(ticketId: String) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch

            try {
                _isLoading.value = true
                val response = ticketRepository.validateTicket(ticketId)
                Log.d("TicketCheckInViewModel", "Full response body: ${response.body().toString()}")
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("TicketCheckInViewModel", "Parsed response: ${apiResponse}")
                    if (apiResponse != null && apiResponse.success) {
                        Log.d("TicketCheckInViewModel", "Ticket is valid: ${apiResponse.data?.isValid}")
                        _checkInResult.value = Result.success(apiResponse.data!!)
                    } else {
                        Log.d("TicketCheckInViewModel", "Ticket is invalid or API response failed")
                        _checkInResult.value = Result.failure(Exception(apiResponse?.message ?: "Unknown error"))
                    }
                } else {
                    Log.e("TicketCheckInViewModel", "Failed response with code: ${response.code()}")
                    _checkInResult.value = Result.failure(Exception("Failed with status code ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("TicketCheckInViewModel", "Exception: ${e.message}")
                _checkInResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _checkInResult.value = null
        _isLoading.value = false
    }
}

