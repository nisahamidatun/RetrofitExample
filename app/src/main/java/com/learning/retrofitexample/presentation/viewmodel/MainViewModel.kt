package com.learning.retrofitexample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.retrofitexample.model.ProductsResponse
import com.learning.retrofitexample.data.repo.ProductRepository
import com.learning.retrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _responseLiveData = MutableLiveData<ResultWrapper<ProductsResponse>>()
    val responseLiveData: MutableLiveData<ResultWrapper<ProductsResponse>>
        get() = _responseLiveData

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getProducts().collect {
                    _responseLiveData.postValue(it)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}