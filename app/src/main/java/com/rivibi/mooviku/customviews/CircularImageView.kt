package com.rivibi.mooviku.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView : AppCompatImageView {

    private val clipPath = Path()
    private val rectF = RectF()

    init {
        clipPath.fillType = Path.FillType.EVEN_ODD
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        clipPath.reset()
        clipPath.addOval(rectF, Path.Direction.CW)
        canvas.clipPath(clipPath)

        super.onDraw(canvas)
    }
}