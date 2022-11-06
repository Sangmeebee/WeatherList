package com.sangmeebee.weatherlist.util

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.weatherlist.model.WeatherViewType

class WeatherListDividerDecoration(heightDp: Int) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()
    private val mHeightDp: Int

    init {
        mPaint.color = Color.argb((255 / 5), 0, 0, 0)
        mHeightDp = (heightDp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    // Divider 높이 만큼 여백을 준다.
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position < parent.childCount) {
            val viewType = parent.adapter!!.getItemViewType(position)
            val nextViewType = parent.adapter!!.getItemViewType(position + 1)
            if (viewType == WeatherViewType.TITLE.ordinal) {
                outRect.set(0, 0, 0, mHeightDp)
            } else {
                if (nextViewType == WeatherViewType.TITLE.ordinal) {
                    outRect.set(0, 0, 0, mHeightDp)
                } else {
                    outRect.set(0, 0, 0, mHeightDp / 2)
                }
            }
        }
    }

    // 현재 뷰 밑에 Divider 를 그린다.
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            if (i < parent.childCount - 1) {
                val view: View = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(view)
                val viewType = parent.adapter!!.getItemViewType(position)
                val nextViewType = parent.adapter!!.getItemViewType(position + 1)
                if (viewType == WeatherViewType.TITLE.ordinal) {
                    c.drawRect(view.left.toFloat(), view.bottom.toFloat(), view.right.toFloat(), (view.bottom + mHeightDp).toFloat(), mPaint)
                } else {
                    if (nextViewType == WeatherViewType.TITLE.ordinal) {
                        c.drawRect(view.left.toFloat(), view.bottom.toFloat(), view.right.toFloat(), (view.bottom + mHeightDp).toFloat(), mPaint)
                    } else {
                        c.drawRect(view.left.toFloat(), view.bottom.toFloat(), view.right.toFloat(), (view.bottom + mHeightDp / 2).toFloat(), mPaint)
                    }
                }
            }
        }
    }
}
