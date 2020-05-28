package com.dzw.gallery

import android.content.Context

/**
 * @author zhangwei on 2020/5/28.
 */
object DisplayUtils {
    /**
     * convert px to its equivalent dp
     *
     * 将px转换为与之相等的dp
     */
    fun px2dp(context: Context, pxValue: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    fun dp2px(context: Context, dipValue: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    fun px2sp(context: Context, pxValue: Int): Int {
        val fontScale: Float = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    fun sp2px(context: Context, spValue: Int): Int {
        val fontScale: Float = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}