package com.ax.screensaver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.Choreographer
import android.view.Choreographer.FrameCallback
import android.widget.TextView

class MarqueeView : TextView {
    companion object {
        private const val MARQUEE_PX_PER_SECOND = 100
        private const val MARQUEE_DELAY:Long = 1200
    }

    private val mChoreographer: Choreographer = Choreographer.getInstance()
    private var mScroll = 0
    private var mMaxScroll = 0
    private var mGhostStart = 0f
    private var mGhostOffset = 0f
    private var mLastAnimationMs = System.currentTimeMillis()
    private var pxPreSecond = MARQUEE_PX_PER_SECOND

    constructor(ctx: Context) : super(ctx) {
        initView()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        initView()
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        mLastAnimationMs = System.currentTimeMillis()
        postDelayed({ start() }, MARQUEE_DELAY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.save()
        // 保证循环滚动可以连接上
        if (mScroll > mGhostStart) {
            canvas.translate(layout.getParagraphDirection(0) * mGhostOffset, 0.0f)
            layout.draw(canvas, Path(), Paint(), 0)
        }
        canvas.restore()
    }

    private val mTickCallback = FrameCallback { tick() }
    private val mRestartCallback = FrameCallback { start() }

    private fun tick() {
        val current = System.currentTimeMillis()
        val mPixelsPerMs = pxPreSecond / 1000f
        // 两帧的时间间隔
        val spend = current - mLastAnimationMs
        mLastAnimationMs = current
        // 两帧的滚动距离
        mScroll += (spend * mPixelsPerMs).toInt()
        scrollTo(mScroll, 0)
        if (scrollX >= mMaxScroll) {
            mScroll = mMaxScroll
            mChoreographer.postFrameCallbackDelayed(mRestartCallback, MARQUEE_DELAY)
        } else {
            mChoreographer.postFrameCallback(mTickCallback)
        }
    }

    private fun start() {
        mScroll = 0
        mLastAnimationMs = System.currentTimeMillis()
        val textWidth = getTextWidth()
        val lineWidth = this.layout.getLineWidth(0)
        val gap = textWidth / 3.0f
        mGhostStart = lineWidth - textWidth + gap
        mGhostOffset = lineWidth + gap
        mMaxScroll = (mGhostStart + textWidth).toInt()

        if (this.context.resources.displayMetrics.widthPixels >= lineWidth) {
            return
        } else {
            textAlignment = TEXT_ALIGNMENT_INHERIT
        }
        mChoreographer.postFrameCallback(mTickCallback)
    }

    private fun getTextWidth(): Int {
        return width - compoundPaddingLeft - compoundPaddingRight
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mChoreographer.removeFrameCallback(mTickCallback)
        mChoreographer.removeFrameCallback(mRestartCallback)
    }

    fun setSpeed(pxPreSecond: Int) {
        this.pxPreSecond = pxPreSecond
    }

}