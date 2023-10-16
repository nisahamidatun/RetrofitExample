package com.learning.retrofitexample.presentation.viewmodel

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.catnip.retrofitexample.databinding.ItemProductBinding
import com.learning.retrofitexample.core.ViewHolderBinder
import com.learning.retrofitexample.model.Product
import com.learning.retrofitexample.utils.toCurrencyFormat
import com.squareup.picasso.Picasso

class ProductItemViewHolder(private val binding: ItemProductBinding
): ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        with(binding){
            Picasso.get().load(item.images[0]).into(binding.ivImage)
            tvTitle.text = item.title
            tvDescription.text = item.desc
            tvPrice.text = item.price.toCurrencyFormat()
        }
    }
}