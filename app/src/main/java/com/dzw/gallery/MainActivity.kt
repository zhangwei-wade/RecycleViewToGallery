package com.dzw.gallery

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.properties.Delegates

/*此demo只适合一个屏幕展示三张*/
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GalleryAdapter
    private var mScreenWidth = 0//屏幕宽度

    /*以下参数大小由美工提供*/
    private var mMinWidth by Delegates.notNull<Int>()
    private var mMaxWidth by Delegates.notNull<Int>()
    private var mMinHeight by Delegates.notNull<Int>()
    private var mMaxHeight by Delegates.notNull<Int>()
    private var mMinTopMargin by Delegates.notNull<Int>()
    private var mMaxTopMargin by Delegates.notNull<Int>()
    private var mMinTextSize by Delegates.notNull<Int>()
    private var mMaxTextSize by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mScreenWidth = resources.displayMetrics.widthPixels
        mMinWidth = DisplayUtils.dp2px(this@MainActivity, 102)
        mMaxWidth = DisplayUtils.dp2px(this@MainActivity, 126)
        mMinHeight = DisplayUtils.dp2px(this@MainActivity, 129)
        mMaxHeight = DisplayUtils.dp2px(this@MainActivity, 158)
        mMinTopMargin = DisplayUtils.dp2px(this@MainActivity, 11)
        mMaxTopMargin = DisplayUtils.dp2px(this@MainActivity, 26)
        mMinTextSize = DisplayUtils.sp2px(this@MainActivity, 8)
        mMaxTextSize = DisplayUtils.sp2px(this@MainActivity, 11)
        adapter = GalleryAdapter()
        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        list.layoutManager = layoutManager
        list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.left = DisplayUtils.dp2px(this@MainActivity, 14)
                else {
                    outRect.left = DisplayUtils.dp2px(this@MainActivity, 4)
                }
                if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1) {
                    outRect.right = DisplayUtils.dp2px(this@MainActivity, 14)
                } else {
                    outRect.right = DisplayUtils.dp2px(this@MainActivity, 4)
                }
                outRect.bottom = 0
                outRect.top = 0
            }
        })
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(list)//设置居中回弹
        list.adapter = adapter
        list.addOnScrollListener(listener)
        list.scrollToPosition(adapter.size * 500)
    }

    /*监听list滑动事件*/
    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onChangeView(recyclerView)
        }
    }

    /**中间图片放大处理*/
    private fun onChangeView(recyclerView: RecyclerView) {
        val childCount = recyclerView.childCount
        Log.e(javaClass.name, childCount.toString() + "")
        for (i in 0 until childCount) {
            val child: CardView = recyclerView.getChildAt(i) as CardView
            val lp = child.layoutParams as RecyclerView.LayoutParams
            val left: Int = child.left
            val right: Int = mScreenWidth - child.right
            var percent: Float =
                if (left < 0 || right < 0) 0f else left.coerceAtMost(right) * 1f / left.coerceAtLeast(
                    right
                )
            Log.e(javaClass.name, "percent = $percent")
            if (percent > 0.9) {
                percent = 1.0f
            }
            lp.topMargin = (mMaxTopMargin - abs(percent) * (mMaxTopMargin - mMinTopMargin)).toInt()
            lp.width = (mMinWidth + abs(percent) * (mMaxWidth - mMinWidth)).toInt()
            lp.height = (mMinHeight + abs(percent) * (mMaxHeight - mMinHeight)).toInt()
            child.layoutParams = lp
            child.findViewById<TextView>(R.id.tv_title)
                .setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    mMinTextSize.toFloat() + abs(percent) * (mMaxTextSize.toFloat() - mMinTextSize.toFloat())
                )
//            if (percent > 0.5f) {
//                child.findViewById<TextView>(R.id.tv_title)
//                    .setTextSize(TypedValue.COMPLEX_UNIT_PX, mMaxTextSize.toFloat())
//            } else {
//                child.findViewById<TextView>(R.id.tv_title)
//                    .setTextSize(TypedValue.COMPLEX_UNIT_PX, mMinTextSize.toFloat())
//            }
            if (percent == 1.0f) {
                child.setOnClickListener {
                    Toast.makeText(this@MainActivity, "被点击了", Toast.LENGTH_SHORT).show()
                }
            } else {
                child.setOnClickListener {
                    val clickPosition = recyclerView.getChildLayoutPosition(it)
                    Toast.makeText(this@MainActivity, "$clickPosition", Toast.LENGTH_SHORT).show()
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    when (clickPosition) {
                        layoutManager.findFirstCompletelyVisibleItemPosition() -> {
                            recyclerView.smoothScrollToPosition(clickPosition - 1)
                        }
                        layoutManager.findLastCompletelyVisibleItemPosition() -> {
                            recyclerView.smoothScrollToPosition(clickPosition + 1)
                        }
                    }
                }
            }
        }
    }
}
