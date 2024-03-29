package com.nbc.curtaincall.presentation.detail_activity.er.expectation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbc.curtaincall.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.nbc.curtaincall.databinding.ExpectationItemBinding
import com.nbc.curtaincall.supabase.model.GetExpectationModel
import java.time.OffsetDateTime

class ExpectationListAdapter(
	private val context: Context
) :
	ListAdapter<GetExpectationModel, ExpectationListAdapter.ViewHolder>(ExpectationDiffCallback()) {
	inner class ViewHolder(private val binding: ExpectationItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: GetExpectationModel) = with(binding) {
			tvName.text = item.profile.name
			tvComment.text = item.comment

			if (item.isExpect) {
				ivIsExpect.setImageResource(R.drawable.curtain_open)
				tvIsExpect.text = context.getString(R.string.good)
			} else {
				ivIsExpect.setImageResource(R.drawable.curtain_close)
				tvIsExpect.text = context.getString(R.string.bad)
			}

			val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
			val offsetDateTime = OffsetDateTime.parse(item.createdAt).format(outputFormatter)

			tvCreatedAt.text = offsetDateTime.toString()
		}
	}

	override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
		return ViewHolder(ExpectationItemBinding.inflate(LayoutInflater.from(group.context), group, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(getItem(position))
	}
}

class ExpectationDiffCallback : DiffUtil.ItemCallback<GetExpectationModel>() {
	override fun areItemsTheSame(oldItem: GetExpectationModel, newItem: GetExpectationModel): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: GetExpectationModel, newItem: GetExpectationModel): Boolean {
		return oldItem.id == newItem.id
	}
}
