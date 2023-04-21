package com.rivibi.mooviku.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.rivibi.mooviku.R

class RoundedCornersImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val clipPath = Path()
    private val rectF = RectF()
    private var cornerRadius = 0f

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedCornersImageView,
            0, 0,
        ).apply {
            try {
                cornerRadius = getDimension(R.styleable.RoundedCornersImageView_cornerRadius, 0f)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        clipPath.reset()
        clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        canvas.clipPath(clipPath)

        super.onDraw(canvas)
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }
}