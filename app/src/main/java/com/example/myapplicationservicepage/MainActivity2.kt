package com.example.myapplicationservicepage

import android.app.Activity
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationservicepage.ui.collapsing.CollapsingFragment
import com.example.myapplicationservicepage.ui.main.MainFragment
import com.example.myapplicationservicepage.ui.viewpager.ViewPagerFragment

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ViewPagerFragment.newInstance())
                .commitNow()
        }
    }


}