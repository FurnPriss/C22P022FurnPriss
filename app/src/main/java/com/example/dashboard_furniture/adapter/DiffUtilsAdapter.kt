package com.example.dashboard_furniture.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.dashboard_furniture.response.ProductItem

class DiffUtilsAdapter (private val oldListStory: List<ProductItem>,
                        private val newListStory: List<ProductItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListStory.size
    }

    override fun getNewListSize(): Int {
        return newListStory.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListStory[oldItemPosition].idProduct == newListStory[newItemPosition].idProduct
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldListStory[oldItemPosition].idProduct != newListStory[newItemPosition].idProduct -> {
                false
            }
            oldListStory[oldItemPosition].category != newListStory[newItemPosition].category -> {
                false
            }
            else -> {
                true
            }
        }
    }

}