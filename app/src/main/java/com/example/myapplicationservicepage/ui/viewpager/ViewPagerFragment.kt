package com.example.myapplicationservicepage.ui.viewpager

import android.content.ContentValues.TAG
import android.content.res.Resources
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationservicepage.R
import com.example.myapplicationservicepage.adapter.InfiniteLoopAdapter
import com.example.myapplicationservicepage.adapter.InfiniteLoopPagerAdapter
import com.example.myapplicationservicepage.databinding.FragmentViewPagerBinding
import com.example.myapplicationservicepage.ui.main.getStatusBarHeight
import com.example.myapplicationservicepage.ui.main.onFullscreen
import kotlin.math.abs

class ViewPagerFragment : Fragment() {

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    private val viewModel: ViewPagerViewModel by viewModels()
    private lateinit var adapter: InfiniteLoopPagerAdapter
    private lateinit var adapterCategory: InfiniteLoopAdapter
    private lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window = requireActivity().window
        window.onFullscreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentViewPagerBinding.inflate(layoutInflater)
        with(binding) {
            toolbar.setPadding(0, requireActivity().getStatusBarHeight(), 0, 0)

            // Initialize ViewPager2
            val items = listOf(
                R.drawable.gother_banner,
                R.drawable.wealth_banner,
                R.drawable.travel_card_banner
            )
            adapter = InfiniteLoopPagerAdapter(items)
            viewPager.adapter = adapter

            // Start in the middle for an infinite scrolling effect
            val startingPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % items.size)
            viewPager.setCurrentItem(startingPosition, false)

            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterCategory = InfiniteLoopAdapter(items)
            recyclerView.adapter = adapterCategory

            recyclerView.scrollToPosition(startingPosition)

            // Set height for toolbar
            val combinedHeight = getHeightToolbar(125f)

            toolbar.layoutParams.height = combinedHeight

            appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                    // Collapsed

                    //setMargin
                    nestedScrollView.setTopMarginView(0)
                } else if (verticalOffset == 0) {
                    // Expanded
                } else {
                    // Somewhere in between

                    //setMargin
                    nestedScrollView.setTopMarginView(-16)
                }

                toolbar.requestLayout()
            }

            nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val stickyThreshold = cardView.top
                val viewStickyTop = cardView.height - 16.toDp()
                if (scrollY >= stickyThreshold) {
                    cardView.translationY = (scrollY - stickyThreshold).toFloat()
                } else {
                    cardView.translationY = 0f
                }

                // If we've scrolled past viewSticky's original position, make it "stick"
                if (scrollY > viewStickyTop) {
                    // Apply translation to keep viewSticky at the top
                    viewSticky.translationY = (scrollY - viewStickyTop).toFloat()
                } else {
                    // Reset translation when above the viewSticky position
                    viewSticky.translationY = 0f
                }

                Log.d(TAG, "scrollY: $scrollY, stickyThreshold: $stickyThreshold, viewStickyTop: $viewStickyTop")
            }
        }

        return binding.root
    }

    private fun getHeightToolbar(fl: Float): Int {
        val statusBarHeight: Int = requireActivity().getStatusBarHeight()
        val toolbarHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            fl,
            resources.displayMetrics
        ).toInt()
        val combinedHeight = statusBarHeight + toolbarHeight
        return combinedHeight
    }

    private fun View.setTopMarginView(i: Int) {
        val params = this.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = i.toDp()
        this.layoutParams = params
    }

    fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}