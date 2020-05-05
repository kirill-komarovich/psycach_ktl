package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.managers.UserManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.psycach_ktl.entities.UserProfile
import com.psycach_ktl.repositories.UserRepository
import kotlinx.coroutines.launch

class ClientsViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _listQuery = MutableLiveData<Query>()
    val listQuery: LiveData<Query>
        get() = _listQuery

    fun initialQuery(): Query {
        val userId = UserManager.currentUser!!.uid
        _listQuery.value = userRepository.buildClientsListQuery(userId)
        return _listQuery.value!!
    }

    fun buildPagingOptions(query: Query, lifecycleOwner: LifecycleOwner? = null): FirestoreRecyclerOptions<UserProfile> {
        val builder = FirestoreRecyclerOptions.Builder<UserProfile>()
        lifecycleOwner?.let { builder.setLifecycleOwner(it) }
        builder.setQuery(query, UserProfile::class.java)

        return builder.build()
    }
}
