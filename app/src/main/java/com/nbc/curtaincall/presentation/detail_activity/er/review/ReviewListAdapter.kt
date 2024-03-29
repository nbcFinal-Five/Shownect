package com.nbc.curtaincall.ui.detail_activity.er.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbc.curtaincall.R
import com.nbc.curtaincall.databinding.ReviewItemBinding
import com.nbc.curtaincall.supabase.model.GetReviewModel
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ReviewListAdapter :
	ListAdapter<GetReviewModel, ReviewListAdapter.ViewHolder>(ReviewDiffCallback()) {
	inner class ViewHolder(private val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: GetReviewModel) = with(binding) {
			tvName.text = item.profile.name
			tvComment.text = item.comment

			// 출력 형식 지정
			val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
			val offsetDateTime = OffsetDateTime.parse(item.createdAt).format(outputFormatter)

			tvCreatedAt.text = offsetDateTime.toString()

			listOf(
				ivPoint1,
				ivPoint2,
				ivPoint3,
				ivPoint4,
				ivPoint5
			).forEachIndexed { index, pointView ->
				pointView.setImageResource(if (index <= item.point - 1) R.drawable.full_star else R.drawable.empty_star)
			}
		}
	}

	override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
		return ViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(group.context), group, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(getItem(position))
	}
}

class ReviewDiffCallback : DiffUtil.ItemCallback<GetReviewModel>() {
	override fun areItemsTheSame(oldItem: GetReviewModel, newItem: GetReviewModel): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: GetReviewModel, newItem: GetReviewModel): Boolean {
		return oldItem.id == newItem.id
	}
}
