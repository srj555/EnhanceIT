package com.sr.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sr.myapplication.R
import com.sr.myapplication.databinding.ItemListContentBinding
import com.sr.myapplication.model.DataModel

class CardListAdapter :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {
    private var mValues: List<DataModel?>? = null
    fun setList(list: ArrayList<DataModel?>) {
        mValues = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemListContentBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.item_list_content,
                parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.data = mValues!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mValues == null) 0 else mValues!!.size
    }

    inner class ViewHolder(val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root)
}