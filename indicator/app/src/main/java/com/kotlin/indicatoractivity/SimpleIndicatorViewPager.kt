package com.kotlin.indicatoractivity

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class SimpleIndicatorViewPager : ViewPager {
    private var mPageIndicatorView: PageIndicatorView? = null
    private var mEnabled: Boolean = false
    private var mShouldConsumeClick: Boolean = false
    private var mIsDown: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet){
        addOnPageChangeListener(object :SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePageIndicatorView(position)
            }
        })
        mEnabled = true
    }

    private fun updatePageIndicatorView(position: Int) {
        mPageIndicatorView?.setCurrPageNumber(position)
    }

    fun setPageIndicatorView(pageIndicatorView: PageIndicatorView) {
        mPageIndicatorView = pageIndicatorView
        setPageIndicatorCnt()
    }
    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        setPageIndicatorCnt()
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
        updatePageIndicatorView(item)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        var result =  super.onTouchEvent(ev) && mEnabled
        ev?.let { motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN ->{
                    mIsDown = true
                }
                MotionEvent.ACTION_OUTSIDE -> {
                    mIsDown = false
                }
                MotionEvent.ACTION_UP -> {
                    if(mShouldConsumeClick){
                        result = true
                    }
                    if(mIsDown){
                        performClick()
                    }
                    mIsDown = false
                }
            }
        }
        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var result = mEnabled && super.onInterceptTouchEvent(ev)
        ev?.let {motionEvent ->
            when(motionEvent.actionMasked){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP ->{
                    if(mShouldConsumeClick){
                        result = true
                    }
                }
            }
        }
        return result
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
        mIsDown = false
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return super.canScrollHorizontally(direction) && mEnabled
    }

    private fun setPageIndicatorCnt() {
        val adapter = adapter
        adapter?.let {
            mPageIndicatorView?.let { pageIndicatorVal ->
                val cnt = it.count
                pageIndicatorVal.setTotalPageNumber(cnt)
                it.registerDataSetObserver(object : DataSetObserver() {
                    override fun onChanged() {
                        super.onChanged()
                        val adapter = this@SimpleIndicatorViewPager.adapter
                        pageIndicatorVal.setTotalPageNumber(adapter?.count?:0)
                    }
                })
            }
        }
    }
}