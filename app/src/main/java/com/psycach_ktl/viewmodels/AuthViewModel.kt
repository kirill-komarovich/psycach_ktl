package com.psycach_ktl.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.psycach_ktl.R
import com.psycach_ktl.enums.AuthenticationState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(application, googleSignInOptions)

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    private val _authenticationType = MutableLiveData<AuthenticationTypes>()
    val authenticationState: LiveData<AuthenticationState>
        get() = _authenticationState

    init {
        _authenticationState.value = if (auth.currentUser != null) AuthenticationState.AUTHENTICATED
                                        else AuthenticationState.UNAUTHENTICATED
    }

    fun signInUser(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOutUser() {
        viewModelScope.launch {
            socialSignOut()
            auth.signOut()
            _authenticationState.value = AuthenticationState.UNAUTHENTICATED
            _authenticationType.value = null
        }
    }

    private suspend fun socialSignOut() {
        when (_authenticationType.value) {
            AuthenticationTypes.GOOGLE -> googleSignInClient.signOut().await()
        }
    }

    fun authenticateWithGoogle(data: Intent?) {
        viewModelScope.launch {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Login", "Google sign in failed", e)
                _authenticationState.value = AuthenticationState.UNAUTHENTICATED
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        return try {
            val result = auth.signInWithCredential(credential).await()
            _authenticationState.value = AuthenticationState.AUTHENTICATED
            _authenticationType.value = AuthenticationTypes.GOOGLE
            result.user
        } catch (e: FirebaseAuthException) {
            Log.w("AuthViewModel", "signInWithCredential:failure", e)
            _authenticationState.value = AuthenticationState.UNAUTHENTICATED
            null
        }
    }

    enum class AuthenticationTypes {
        GOOGLE
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(application) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
