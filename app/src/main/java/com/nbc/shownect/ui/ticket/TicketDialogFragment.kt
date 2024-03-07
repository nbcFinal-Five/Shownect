package com.nbc.shownect.ui.ticket

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbc.shownect.R
import com.nbc.shownect.databinding.SimpleInfoBottomsheetDialogBinding
import com.nbc.shownect.fetch.network.retrofit.RetrofitClient.fetch
import com.nbc.shownect.fetch.repository.impl.FetchRepositoryImpl
import com.nbc.shownect.ui.detail_activity.DetailActivity
import com.nbc.shownect.ui.main.MainViewModel
import com.nbc.shownect.ui.main.MainViewModelFactory
import com.nbc.shownect.util.Constants

class TicketDialogFragment : BottomSheetDialogFragment() {
    private var _binding: com.nbc.shownect.databinding.SimpleInfoBottomsheetDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainViewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            fetchRemoteRepository = FetchRepositoryImpl(fetch = fetch)
        )
    }
    private var ticketId = ""
    private var facilityId = ""

    override fun onStart() {
        super.onStart()
        val bottomSheetDialog = dialog as? BottomSheetDialog
        val bottomSheet =
            bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val displayMetrics = Resources.getSystem().displayMetrics
            // 화면 높이의 90%를 계산
            val desiredHeight = (displayMetrics.heightPixels * 0.9).toInt()

            val layoutParams = it.layoutParams
            layoutParams.height = desiredHeight
            it.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED // Bottom Sheet를 확장 상태로 시작
            behavior.peekHeight = desiredHeight // 필요한 경우 높이 제한 설정
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SimpleInfoBottomsheetDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(sharedViewModel) {
            //HomeFragment에서 id를 보내주면 fetchShowDetail() 호출
            showId.observe(viewLifecycleOwner) { id ->
                sharedViewModel.fetchShowDetail(id)
                ticketId = id
            }
            showDetailInfo.observe(viewLifecycleOwner) {
                val showDetail = it.first()
                with(binding) {
                    tvSimpleShowNameSub.text = showDetail.prfnm
                    ivSimplePosterImage.load(showDetail.poster)
                    tvSimpleAge.text = showDetail.prfage
                    tvSimpleRuntimeSub.text = showDetail.prfruntime
                    tvSimplePlaceSub.text = showDetail.dtguidance
                    tvSimplePriceSub.text = showDetail.pcseguidance
                    tvSimplePlaceSub.text = showDetail.fcltynm
                    tvSimpleGenre.text = showDetail.genrenm
                    tvSimpleShowState.text = showDetail.prfstate
                    tvSimpleCastSub.text =
                        if (showDetail.prfcast.isNullOrBlank()) "미상" else showDetail.prfcast
                    tvSimpleProductSub.text =
                        if (showDetail.entrpsnm.isNullOrBlank()) "미상" else showDetail.entrpsnm
                }
                facilityId = showDetail.mt20id.toString()
            }
        }
        //Swipe Gesture
        binding.layoutSimpleScrollview.setOnTouchListener(object :
            OnSwipeTouchListener(requireContext()) {
            override fun onSwipeTop() {
                super.onSwipeTop()
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(Constants.SHOW_ID, ticketId)
                    putExtra(Constants.FACILITY_ID,facilityId)
                }
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
