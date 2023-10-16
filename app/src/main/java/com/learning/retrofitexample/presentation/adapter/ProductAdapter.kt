package com.learning.retrofitexample.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catnip.retrofitexample.databinding.ItemProductBinding
import com.learning.retrofitexample.model.Product
import com.learning.retrofitexample.presentation.viewmodel.ProductItemViewHolder

class ProductAdapter : RecyclerView.Adapter<ProductItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    })

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun submitData(data: List<Product>) = dataDiffer.submitList(data)
}