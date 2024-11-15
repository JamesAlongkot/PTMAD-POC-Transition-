package com.example.myapplicationservicepage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplicationservicepage.R

class InfiniteLoopPagerAdapter(private val items: List<Int>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_layout, container, false)
        val imageView = view.findViewById<ImageView>(R.id.item_icon)
        imageView.setImageResource(items[position % items.size])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int = Int.MAX_VALUE // For infinite scrolling

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
}
