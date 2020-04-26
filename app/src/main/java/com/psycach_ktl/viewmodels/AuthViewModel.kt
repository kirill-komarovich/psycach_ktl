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
import com.psycach_ktl.managers.UserManager
import com.psycach_ktl.repositories.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    private val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(application, googleSignInOptions)

    private val _authenticationType = MutableLiveData<AuthenticationTypes>()
    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState>
        get() = _authenticationState

    private val _currentUser = MutableLiveData<FirebaseUser>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser>
        get() = _currentUser

    init {
        _authenticationState.value = if (currentUser.value != null) AuthenticationState.AUTHENTICATED
                                        else AuthenticationState.UNAUTHENTICATED

        auth.addAuthStateListener { firebaseAuth ->
            viewModelScope.launch {
                val user = firebaseAuth.currentUser

                _currentUser.value = user
                user?.let {
                    UserManager.currentUserProfile = userRepository.findProfile(it.uid)
                    _authenticationState.value = AuthenticationState.AUTHENTICATED
                } ?: run {
                    _authenticationState.value = AuthenticationState.UNAUTHENTICATED
                    _authenticationType.value = null
                }
            }
        }
    }

    fun signInUser(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOutUser() {
        viewModelScope.launch {
            socialSignOut()
            auth.signOut()
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

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        try {
            auth.signInWithCredential(credential).await()
            _authenticationType.value = AuthenticationTypes.GOOGLE
        } catch (e: FirebaseAuthException) {
            Log.w("AuthViewModel", "signInWithCredential:failure", e)
        }
    }

    enum class AuthenticationTypes {
        GOOGLE
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(application) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
