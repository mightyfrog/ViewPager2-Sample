package org.mightyfrog.android.viewpager2sample

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.roundToInt
import org.mightyfrog.android.viewpager2sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = TabAdapter()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
            // no-op
        }.attach()

        // page margin
        val recyclerViewInstance =
            binding.viewPager.children.first { it is RecyclerView } as RecyclerView
        recyclerViewInstance.apply {
            addItemDecoration(ItemDecoration())
            setPadding(32.toPx(), 0, 32.toPx(), 0)
            clipToPadding = false
        }
    }

    class TabAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int = 10

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_holder, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = position.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.textView)
    }

    class ItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = 16.toPx()
            outRect.left = 16.toPx()
        }
    }
}

private fun Int.toPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)

    return px.roundToInt()
}
