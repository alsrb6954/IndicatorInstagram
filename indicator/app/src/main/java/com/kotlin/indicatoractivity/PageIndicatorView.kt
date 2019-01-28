package com.kotlin.indicatoractivity

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

class PageIndicatorView:LinearLayout {

    var mCurrPage: Int = 0
    var mDimen: Int = 0
    var mIndicatorImg: Drawable? = null
    var mIndicatorMediumImg: Drawable? = null
    var mIndicatorSmallImg: Drawable? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle){
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.PageIndicatorView, defStyle, 0)
        val defaultDimen = resources.getDimensionPixelSize(R.dimen.indicator_space)
        mDimen = a.getDimensionPixelOffset(R.styleable.PageIndicatorView_spacing, defaultDimen)
        mIndicatorImg = a.getDrawable(R.styleable.PageIndicatorView_imgSrc)
        if (mIndicatorImg == null) mIndicatorImg = resources.getDrawable(R.drawable.slide_indicator, null)
        if (mIndicatorMediumImg == null) mIndicatorMediumImg = resources.getDrawable(R.drawable.slide_indicator_medium, null)
        if (mIndicatorSmallImg == null) mIndicatorSmallImg = resources.getDrawable(R.drawable.slide_indicator_small, null)
        orientation = HORIZONTAL
        a.recycle()
    }

    fun setTotalPageNumber(totalPageCount:Int){
        if(totalPageCount <= 1){
            visibility = View.INVISIBLE
            return
        }
        visibility = View.VISIBLE
        removeAllViews()
        var imageView:ImageView
        var lp:LayoutParams
        for(index in 0 until totalPageCount) {
            if(index == 5){
                break
            }
            val state = when(index){
                3 ->{
                    mIndicatorMediumImg?.constantState
                }
                4->{
                    mIndicatorSmallImg?.constantState
                }
                else ->{
                    mIndicatorImg?.constantState
                }
            }
            state?.let {
                imageView = ImageView(context)
                lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                if(index != totalPageCount - 1){
                    lp.marginEnd = mDimen
                }
                if(index != 0){
                    lp.marginStart = mDimen
                }
                imageView.layoutParams = lp
                imageView.setImageDrawable(it.newDrawable().mutate())
                addView(imageView)
                setCurrPageNumber(mCurrPage)
            }?:continue
        }
    }

    fun setCurrPageNumber(currPageNumber: Int) {
        val childCount = childCount
        if (currPageNumber >= childCount) {
            return
        }
        getChildAt(mCurrPage).isSelected = false
        mCurrPage = currPageNumber
        getChildAt(mCurrPage).isSelected = true
    }
}