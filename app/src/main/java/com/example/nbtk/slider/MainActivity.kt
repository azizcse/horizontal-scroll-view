package com.example.nbtk.slider

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.nbtk.slider.com.ScreenUtil
import com.example.nbtk.slider.com.SliderLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var rvHorizontalPicker: RecyclerView
    private lateinit var tvSelectedItem: TextView

    private val data : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data.add("Hello")
        data.add("Many")
        data.add("Aziz")
        data.add("Faisal")
        data.add("Nothing")
        data.add("Dahaka")
        data.add("Hello")
        setTvSelectedItem()
        setHorizontalPicker()
    }

    private fun setTvSelectedItem() {
        tvSelectedItem = findViewById(R.id.tv_selected_item)
    }

    private fun setHorizontalPicker() {
        rvHorizontalPicker = findViewById(R.id.rv_horizontal_picker)

        // Setting the padding such that the items will appear in the middle of the screen
        val padding: Int = ScreenUtil.getScreenWidth(this)/2 - ScreenUtil.dpToPx(this, 40)
        rvHorizontalPicker.setPadding(padding, 0, padding, 0)

        // Setting layout manager

        val sliderLayoutManager = SliderLayoutManager(this)
        rvHorizontalPicker.layoutManager = sliderLayoutManager/*.apply {
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    tvSelectedItem.setText(data[layoutPosition])
                }
            }
        }*/

        sliderLayoutManager.initListener(object : SliderLayoutManager.OnItemSelectedListener{
            override fun onItemSelected(position: Int) {
                tvSelectedItem.setText(data[position])
            }

        })


        // Setting Adapter
        rvHorizontalPicker.adapter = SliderAdapter().apply {
            setData(data)
            callback = object : SliderAdapter.Callback {
                override fun onItemClicked(view: View) {
                    rvHorizontalPicker.smoothScrollToPosition(rvHorizontalPicker.getChildLayoutPosition(view))
                }
            }
        }
    }
}
