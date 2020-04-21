package com.psycach_ktl.viewmodels

import androidx.lifecycle.*
import com.psycach_ktl.entities.Form
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.enums.MethodologyTypes

class FormViewModel(form: Form) : ViewModel() {
    private var _form = MutableLiveData<Form>()
    val form: LiveData<Form>
        get() = _form

    private var _formItems = MutableLiveData<List<FormItem>>()
    val formItems: LiveData<List<FormItem>>
        get() = _formItems

    init {
        _form.value = form
        _formItems.value = form.items
    }


    class Factory(val methodologyType: MethodologyTypes) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
                val form = Form.build(methodologyType)

                return FormViewModel(form) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}
