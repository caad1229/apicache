package com.caad1229.apicache.presentation.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.caad1229.apicache.databinding.ListItemQiitaItemBinding
import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.viewmodel.QiitaItemViewModel

class QiitaUserItemsAdapter : RecyclerView.Adapter<QiitaUserItemsAdapter.ViewHolder>() {

    var data: List<QiitaItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemQiitaItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(QiitaItemViewModel(holder.itemView.context, data[position]))
    }

    override fun getItemCount(): Int = data.size

    fun updateData(list: List<QiitaItem>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListItemQiitaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: QiitaItemViewModel) {
            binding.viewModel = viewModel
        }
    }
}