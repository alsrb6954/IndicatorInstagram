package com.kotlin.indicatoractivity

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout

class PageIndicatorView:LinearLayout {

    var mCurrPage: Int = 0
    var mDimen: Int = 0
    var mIndicatorImg: Drawable? = null
    var mIndicatorMediumImg: Drawable? = null
    var mIndicatorSmallImg: Drawable? = null
    var zeroAnimation: Animation? = null
    var oneAnimation: Animation? = null
    var oneContraryAnimation: Animation? = null

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
        if (zeroAnimation == null) zeroAnimation = AnimationUtils.loadAnimation(context, R.anim.zero_scale)
        if (oneAnimation == null) oneAnimation = AnimationUtils.loadAnimation(context, R.anim.one_scale)
        if (oneContraryAnimation == null) oneContraryAnimation = AnimationUtils.loadAnimation(context, R.anim.one_contrary_scale)
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
        for(i in 0 until totalPageCount) {
            var index = i
            imageView = ImageView(context)
            if (totalPageCount == 4 || totalPageCount == 5) {
                index = 0
            }
            val state = when(index){
                3 ->{
                    imageView.transitionName = "1"
                    mIndicatorMediumImg?.constantState
                }
                4->{
                    imageView.transitionName = "2"
                    mIndicatorSmallImg?.constantState
                }
                else ->{
                    imageView.transitionName = "0"
                    mIndicatorImg?.constantState
                }
            }
            if(i >= 5){
                imageView.visibility = View.GONE
            }
            state?.let {
                lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                if(i != totalPageCount - 1){
                    lp.marginEnd = mDimen
                }
                if(i != 0){
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
        if(mCurrPage<currPageNumber){
            val currentView = getChildAt(currPageNumber) as ImageView
            val nextView = if(currPageNumber + 1 < childCount) { getChildAt(currPageNumber+1) as ImageView } else null
            val nextNextView = if(currPageNumber + 2 < childCount) { getChildAt(currPageNumber+2) as ImageView } else null
            val backView = if(currPageNumber - 3 >= 0) { getChildAt(currPageNumber-3) as ImageView } else null
            val backBackView = if(currPageNumber - 4 >= 0) { getChildAt(currPageNumber-4) as ImageView } else null
            val backBackBackView = if(currPageNumber - 5 >= 0) { getChildAt(currPageNumber-5) as ImageView } else null
            when(currentView.transitionName){
                "1"->{
                    currentView.startAnimation(oneAnimation)
                    currentView.setImageDrawable(mIndicatorImg?.constantState?.newDrawable()?.mutate())
                    currentView.transitionName = "0"
                    nextView?.let {
                        it.startAnimation(oneAnimation)
                        it.setImageDrawable(mIndicatorMediumImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "1"
                    }
                    nextNextView?.let {
                        it.visibility = View.VISIBLE
                        it.startAnimation(zeroAnimation)
                        it.setImageDrawable(mIndicatorSmallImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "2"
                    }
                    backView?.let {
                        it.startAnimation(oneContraryAnimation)
                        it.setImageDrawable(mIndicatorMediumImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "1"
                    }
                    backBackView?.let {
                        it.startAnimation(oneContraryAnimation)
                        it.setImageDrawable(mIndicatorSmallImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "2"
                    }
                    backBackBackView?.let {
                        it.visibility = View.GONE
                    }
                }
            }
        }else{
            val currentView = getChildAt(currPageNumber) as ImageView
            val nextView = if(currPageNumber + 3 < childCount) { getChildAt(currPageNumber+3) as ImageView } else null
            val nextNextView = if(currPageNumber + 4 < childCount) { getChildAt(currPageNumber+4) as ImageView } else null
            val nextNextNextView = if(currPageNumber + 5 < childCount) { getChildAt(currPageNumber+5) as ImageView } else null
            val backView = if(currPageNumber - 1 >= 0) { getChildAt(currPageNumber-1) as ImageView } else null
            val backBackView = if(currPageNumber - 2 >= 0) { getChildAt(currPageNumber-2) as ImageView } else null
            when(currentView.transitionName){
                "1"->{
                    currentView.startAnimation(oneAnimation)
                    currentView.setImageDrawable(mIndicatorImg?.constantState?.newDrawable()?.mutate())
                    currentView.transitionName = "0"
                    nextView?.let {
                        it.startAnimation(oneContraryAnimation)
                        it.setImageDrawable(mIndicatorMediumImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "1"
                    }
                    nextNextView?.let {
                        it.startAnimation(oneContraryAnimation)
                        it.setImageDrawable(mIndicatorSmallImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "2"
                    }
                    nextNextNextView?.let {
                        it.visibility = View.GONE
                    }
                    backView?.let {
                        it.startAnimation(oneAnimation)
                        it.setImageDrawable(mIndicatorMediumImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "1"
                    }
                    backBackView?.let {
                        it.visibility = View.VISIBLE
                        it.startAnimation(zeroAnimation)
                        it.setImageDrawable(mIndicatorSmallImg?.constantState?.newDrawable()?.mutate())
                        it.transitionName = "2"
                    }
                }
            }
        }

        getChildAt(mCurrPage).isSelected = false
        mCurrPage = currPageNumber
        getChildAt(mCurrPage).isSelected = true
    }
}