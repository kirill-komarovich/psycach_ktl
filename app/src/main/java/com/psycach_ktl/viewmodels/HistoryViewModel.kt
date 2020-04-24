package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.psycach_ktl.entities.FormResult
import com.psycach_ktl.repositories.FormResultRepository

class HistoryViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val formResultRepository = FormResultRepository()

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setPageSize(PER_PAGE)
        .build()

    private val _listQuery = MutableLiveData<Query>()
    val listQuery: LiveData<Query>
        get() = _listQuery

    fun initialQuery(): Query {
        val userId = auth.currentUser!!.uid
        _listQuery.value = formResultRepository.buildListQuery(listOf(userId))
        return _listQuery.value!!
    }

    fun buildPagingOptions(query: Query, lifecycleOwner: LifecycleOwner? = null): FirestorePagingOptions<FormResult> {
        val builder = FirestorePagingOptions.Builder<FormResult>()
        lifecycleOwner?.let { builder.setLifecycleOwner(it) }
        builder.setQuery(query, pagedListConfig, FormResult::class.java)

        return builder.build()
    }

    companion object {
        private const val PER_PAGE = 15
        private const val PREFETCH_DISTANCE = 2
    }
}
