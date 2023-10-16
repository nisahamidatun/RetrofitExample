package com.learning.retrofitexample.data.repo

import com.learning.retrofitexample.data.retrofit.ProductService
import com.learning.retrofitexample.model.ProductsResponse
import com.learning.retrofitexample.utils.ResultWrapper
import com.learning.retrofitexample.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class ProductRepositoryImpl(
    private val apiService: ProductService
) : ProductRepository {
    override suspend fun getProducts(): Flow<ResultWrapper<ProductsResponse>> {
        return proceedFlow { apiService.getProducts() }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }
}

interface ProductRepository {
    suspend fun getProducts(): Flow<ResultWrapper<ProductsResponse>>
}