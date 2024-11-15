package com.example.myapplicationservicepage.ui.main

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplicationservicepage.R
import com.example.myapplicationservicepage.adapter.InfiniteLoopAdapter
import com.example.myapplicationservicepage.databinding.FragmentMainBinding
import kotlin.math.abs

class MainFragment : Fragment() {

    private lateinit var window: Window
    private lateinit var adapter: InfiniteLoopAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

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

        val binding = FragmentMainBinding.inflate(layoutInflater)

        with(binding){
            toolbar.setPadding(0, requireActivity().getStatusBarHeight(), 0, 0)

            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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

            nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val maxScrollDistance = abs(cardView.marginTop)
                val cardViewPositionY = cardView.y.toInt() - toolbar.height + cardView.marginTop
                val scrollFraction = ((scrollY - cardViewPositionY).coerceIn(0, maxScrollDistance)).toFloat() / maxScrollDistance

                val interpolatedTitleColor = requireContext().interpolateColor(R.color.white, R.color.black, scrollFraction)
                val interpolatedBackgroundColor = requireContext().interpolateColor(R.color.transparent, R.color.white, scrollFraction)

                titleToolbarTv.setTextColor(interpolatedTitleColor)
                toolbar.setBackgroundColor(interpolatedBackgroundColor)
                recyclerView.alpha = 1 - scrollFraction
            }
        }

        return binding.root
    }
}

fun Context.interpolateColor(
    colorStartResId: Int,
    colorEndResId: Int,
    fraction: Float
): Int {
    val colorStart = ContextCompat.getColor(this, colorStartResId)
    val colorEnd = ContextCompat.getColor(this, colorEndResId)

    return ColorUtils.blendARGB(colorStart, colorEnd, fraction)
}

fun Window.onFullscreen() {
    this.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.statusBarColor = Color.TRANSPARENT
}


fun Activity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}