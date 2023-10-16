package com.learning.retrofitexample.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.retrofitexample.R
import com.catnip.retrofitexample.databinding.ActivityMainBinding
import com.learning.retrofitexample.data.repo.ProductRepository
import com.learning.retrofitexample.data.repo.ProductRepositoryImpl
import com.learning.retrofitexample.data.retrofit.ProductService
import com.learning.retrofitexample.presentation.adapter.ProductAdapter
import com.learning.retrofitexample.presentation.viewmodel.MainViewModel
import com.learning.retrofitexample.utils.GenericViewModelFactory
import com.learning.retrofitexample.utils.proceedWhen

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels{
        val pService = ProductService.invoke()
        val pRepo : ProductRepository = ProductRepositoryImpl(pService)
        GenericViewModelFactory.create(MainViewModel(pRepo))
    }

    private val productAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListProduct()
        observeListData()
        viewModel.getProducts()
    }

    private fun observeListData() {
        viewModel.responseLiveData.observe(this){
            it.proceedWhen( doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvProductList.isVisible = false
            }, doOnEmpty = {
                binding.layoutState.root.isVisible = true
                binding.rvProductList.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.empty_product)
                binding.layoutState.pbLoading.isVisible = false
            }, doOnError = {
                    error ->
                binding.layoutState.root.isVisible = true
                binding.rvProductList.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = error.exception?.message.orEmpty()
            }, doOnSuccess = {
                    success ->
                binding.rvProductList.isVisible = true
                success.payload?.let { productAdapter.submitData(it.products) }
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
            })
        }
    }

    private fun setListProduct() {
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.adapter = productAdapter
    }
}
