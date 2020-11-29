package com.somethingfun.jay.peanut

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.NonNull


@NonNull
fun makePaint(@ColorInt colorInt: Int,
              strokeWidth: Float = 10F,
              style: Paint.Style = Paint.Style.FILL
) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    this.style = style
    this.color = colorInt
    this.strokeWidth = strokeWidth
    this.strokeCap = Paint.Cap.ROUND
    this.isAntiAlias = true
}

fun getDeviceWidth(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()

    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    //val height = displayMetrics.heightPixels
    return displayMetrics.widthPixels
}

fun dpToPx(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}