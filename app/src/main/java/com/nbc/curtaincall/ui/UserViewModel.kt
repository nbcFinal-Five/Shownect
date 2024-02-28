package com.nbc.curtaincall.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbc.curtaincall.Supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class UserViewModel : ViewModel() {
	private var _userInfo = MutableLiveData<UserInfo?>(Supabase.client.auth.currentUserOrNull())
	val userInfo: LiveData<UserInfo?> = _userInfo

	// sign in
	private val _isSignInLoading = MutableLiveData(false)
	val isSignInLoading: LiveData<Boolean> get() = _isSignInLoading

	private val _signInResult = MutableLiveData<Boolean?>(null)
	val signInResult: LiveData<Boolean?> get() = _signInResult

	// sign up
	private val _isSignUpLoading = MutableLiveData(false)
	val isSignUpLoading: LiveData<Boolean> get() = _isSignUpLoading

	private val _signUpResult = MutableLiveData<Boolean?>(null)
	val signUpResult: LiveData<Boolean?> get() = _signUpResult

	private var _signUpErrorMessage = MutableLiveData<String?>(null)
	val signUpErrorMessage: LiveData<String?> = _signUpErrorMessage

	fun signIn(inputEmail: String, inputPassword: String) {
		_isSignInLoading.value = true

		CoroutineScope(Dispatchers.IO).launch {
			try {
				Supabase.client.auth.signInWith(Email) {
					email = inputEmail
					password = inputPassword
				}

				withContext(Dispatchers.Main) {
					_userInfo.value = Supabase.client.auth.currentUserOrNull()
					_signInResult.value = true
				}
			} catch (e: RestException) {
				Log.d("sign in", e.error)
				withContext(Dispatchers.Main) {
					_signInResult.value = false
				}
			} finally {
				withContext(Dispatchers.Main) {
					_isSignInLoading.value = false
				}
			}
		}
	}

	fun signOut() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				Supabase.client.auth.signOut()
			} catch (e: RestException) {
				Log.d("sign out", e.error)
			} finally {
				withContext(Dispatchers.Main) {
					_userInfo.value = null
					_signInResult.value = null
					_signUpResult.value = null
					_signUpErrorMessage.value = null
				}
			}
		}
	}

	fun signUp(
		inputEmail: String,
		inputPassword: String,
		name: String,
		gender: String,
		age: String,
	) {
		_isSignUpLoading.value = true

		CoroutineScope(Dispatchers.IO).launch {
			try {
				Supabase.client.auth.signUpWith(Email) {
					email = inputEmail
					password = inputPassword
					data = buildJsonObject {
						put("name", name)
						put("gender", gender)
						put("age", age)
					}
				}

				withContext(Dispatchers.Main) {
					_userInfo.value = Supabase.client.auth.currentUserOrNull()
					_signUpResult.value = true
					_signUpErrorMessage.value = null
				}
			} catch (e: RestException) {
				Log.d("sign up", e.error)
				withContext(Dispatchers.Main) {
					_signUpResult.value = false
					_signUpErrorMessage.value = e.error
				}
			} finally {
				withContext(Dispatchers.Main) {
					_isSignUpLoading.value = false
				}
			}
		}
	}

	fun setUser() {
		_userInfo.value = Supabase.client.auth.currentUserOrNull()
	}
}