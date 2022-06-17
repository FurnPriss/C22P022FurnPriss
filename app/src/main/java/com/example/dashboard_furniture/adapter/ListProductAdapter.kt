package com.example.dashboard_furniture.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.ItemRowProductBinding
import com.example.dashboard_furniture.response.ProductItem

class ListProductAdapter : RecyclerView.Adapter<ListProductAdapter.ListProductViewHolder>() {

    private var oldListStory = emptyList<ProductItem>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClicked(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setListProduct(newListStory: List<ProductItem>){
        val diffUtil = DiffUtilsAdapter(oldListStory, newListStory)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldListStory = newListStory
        diffResult.dispatchUpdatesTo(this)


    }

    inner class ListProductViewHolder(private val binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductItem) {
            binding.root.setOnClickListener{
                onItemClickCallBack?.onItemClicked(data)
            }
            binding.apply {
                tvItemName.setText("ID : "+data.idProduct)
                tvCategoryProduct.setText("Category : "+data.category)
                if (data.category == "Beds"){
                    photoProduct.setImageResource(R.drawable.ic_bed)
                }else if (data.category == "Tables & Desks"){
                    photoProduct.setImageResource(R.drawable.ic_table)
                }else if (data.category == "Wardrobes"){
                    photoProduct.setImageResource(R.drawable.ic_drawer)
                }else if (data.category == "Chairs"){
                    photoProduct.setImageResource(R.drawable.ic_chair)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductViewHolder {
        val view = ItemRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListProductViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        holder.bind(oldListStory[position])
    }

    override fun getItemCount(): Int = oldListStory.size


    interface OnItemClickCallBack{
        fun onItemClicked(data: ProductItem)
    }
}