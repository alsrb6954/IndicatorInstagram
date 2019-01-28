package com.kotlin.indicatoractivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPager.adapter = viewPagerAdapter
//        indicator.mIndicatorImg = resources.getDrawable(R.drawable.slide_indicator, null)
        viewPager.setPageIndicatorView(indicator)
    }

    inner class ViewPagerAdapter(fragment:FragmentManager):FragmentStatePagerAdapter(fragment){
        override fun getItem(position: Int) = com.kotlin.indicatoractivity.Fragment()
        override fun getCount() = 10
    }
}
