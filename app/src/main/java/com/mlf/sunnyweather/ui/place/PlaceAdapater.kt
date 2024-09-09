package com.mlf.sunnyweather.ui.place

import android.util.Log
import com.mlf.sunnyweather.BR
import com.mlf.sunnyweather.R
import com.mlf.sunnyweather.databinding.PlaceFragmentBinding
import com.mlf.sunnyweather.logic.model.Place
import com.mlf.wanandroid.base.CommonAdapter
import com.mlf.wanandroid.base.CommonViewHolder

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/9 14:06
 * @version: 1.0
 */
class PlaceAdapter(data: List<Place>):
    CommonAdapter<Place, PlaceFragmentBinding>(data,object :OnMoreBindDataListener<Place>{
    override fun onBindViewHolder(
        model: Place,
        viewHolder: CommonViewHolder,
        type: Int,
        position: Int
    ) {
        Log.i("PlaceFragment", "onBindViewHolder: $model")
        viewHolder.getBinding()?.setVariable(BR.place, model)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getLayoutId(type: Int): Int {
        return R.layout.place_item
    }
}) {
}