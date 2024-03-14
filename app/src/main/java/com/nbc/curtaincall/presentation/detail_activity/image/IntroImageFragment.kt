package com.nbc.curtaincall.ui.detail_activity.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.nbc.curtaincall.databinding.FramgentDetailIntroImageBinding
import com.nbc.curtaincall.ui.detail_activity.DetailViewModel


class IntroImageFragment : Fragment() {
    private var _binding: FramgentDetailIntroImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FramgentDetailIntroImageBinding.inflate(inflater, container, false)
        viewModel.detailInfoList.observe(viewLifecycleOwner) {
            val firstShowDetail = it!!.first()
            Glide.with(requireContext()).load(firstShowDetail.styurls?.styurlList?.get(0)).override(Target.SIZE_ORIGINAL).into(binding.ivDetailIntroPoster)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}