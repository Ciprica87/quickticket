package com.ciprian.ticketmanagementsystem.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciprian.ticketmanagementsystem.data.models.UserDTO
import com.ciprian.ticketmanagementsystem.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository?) : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String>?> = _loginResult

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    fun login(username: String, password: String) {
        if (userRepository == null) {
            _loginResult.value = Result.failure(Exception("Repository is not available"))
            return
        }
        viewModelScope.launch {
            val userDTO = UserDTO(username = username, password = password)
            val response = userRepository.loginUser(userDTO)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    if (apiResponse.success) {
                        _loginResult.value = Result.success(apiResponse.message)
                        _username.value = username // Store the username
                    } else {
                        _loginResult.value = Result.failure(Exception(apiResponse.message))
                    }
                } else {
                    _loginResult.value = Result.failure(Exception("Empty response"))
                }
            } else {
                _loginResult.value = Result.failure(Exception("Failed with status code ${response.code()}"))
            }
        }
    }
}
