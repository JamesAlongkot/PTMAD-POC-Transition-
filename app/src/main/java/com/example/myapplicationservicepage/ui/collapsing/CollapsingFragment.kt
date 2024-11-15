package com.example.myapplicationservicepage.ui.collapsing

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.myapplicationservicepage.R
import com.example.myapplicationservicepage.adapter.InfiniteLoopAdapter
import com.example.myapplicationservicepage.databinding.FragmentCollapsingBinding
import com.example.myapplicationservicepage.ui.main.getStatusBarHeight
import com.example.myapplicationservicepage.ui.main.onFullscreen
import kotlin.math.abs


class CollapsingFragment : Fragment() {

    companion object {
        fun newInstance() = CollapsingFragment()
    }

    private val viewModel: CollapsingViewModel by viewModels()
    private lateinit var adapter: InfiniteLoopAdapter
    private lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window = requireActivity().window
        window.onFullscreen()
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCollapsingBinding.inflate(layoutInflater)
        with(binding) {
            toolbar.setPadding(0, requireActivity().getStatusBarHeight(), 0, 0)

            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // Prepare data and adapter
            val items = listOf(
                R.drawable.gother_banner,
                R.drawable.wealth_banner,
                R.drawable.travel_card_banner
            )
            adapter = InfiniteLoopAdapter(items)
            recyclerView.adapter = adapter

            val startingPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % items.size)
            recyclerView.scrollToPosition(startingPosition)

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(recyclerView)

            // Calculate the combined height of status bar and toolbar
            val statusBarHeight: Int = requireActivity().getStatusBarHeight()
            val toolbarHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                56f,
                resources.displayMetrics
            ).toInt()
            val combinedHeight = statusBarHeight + toolbarHeight

            toolbar.layoutParams.height = combinedHeight
            appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                    // Collapsed
                    titleToolbarTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                } else if (verticalOffset == 0) {
                    // Expanded
                    titleToolbarTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                } else {
                    // Somewhere in between
                    titleToolbarTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                }

                Log.d(TAG, "verticalOffset: $verticalOffset")

                toolbar.requestLayout();
            }
        }

        return binding.root
    }
}