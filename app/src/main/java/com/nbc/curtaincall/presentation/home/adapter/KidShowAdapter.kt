package com.nbc.curtaincall.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nbc.curtaincall.databinding.ItemKidShowBinding
import com.nbc.curtaincall.fetch.model.DbResponse

class KidShowAdapter(private val listener: PosterClickListener? = null) :
    ListAdapter<DbResponse, KidShowAdapter.KidShowViewHolder>(object :
        DiffUtil.ItemCallback<DbResponse>() {
        override fun areItemsTheSame(oldItem: DbResponse, newItem: DbResponse): Boolean {
            return oldItem.mt20id == newItem.mt20id
        }

        override fun areContentsTheSame(oldItem: DbResponse, newItem: DbResponse): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class KidShowViewHolder(private val binding: ItemKidShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DbResponse) {
            with(binding) {
                Glide.with(itemView).load(item.poster).into(ivKidShow)
                tvKidPeriod.text = "~ ${item.prfpdto}"
                tvKidGenre.text = item.genrenm
                tvKidShowingState.text = item.prfstate
                tvKidPerformanceTitle.text = item.prfnm
                tvKidShowPlaceName.text = item.fcltynm
                ivKidShow.setOnClickListener { item.mt20id?.let { id -> listener?.posterClicked(id) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KidShowViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemKidShowBinding.inflate(inflater, parent, false)
        return KidShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KidShowViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}