package com.nbc.curtaincall.ui.search.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbc.curtaincall.databinding.SearchBottomsheetDialogGenreBinding
import com.nbc.curtaincall.ui.search.SearchViewModel

class SearchGenreBottomSheet : BottomSheetDialogFragment() {

    private var _binding: SearchBottomsheetDialogGenreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchViewModel  by activityViewModels<SearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = SearchBottomsheetDialogGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenreCheck.setOnClickListener {
            dismiss()
        }


    }

    companion object {
        const val TAG = "SearchGenreBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}