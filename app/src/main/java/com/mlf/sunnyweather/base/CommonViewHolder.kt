package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/6 11:22
 * @version: 1.0
 */
open class CommonViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var binding: ViewDataBinding? = null
        fun getViewHolder(viewBinding: ViewDataBinding): CommonViewHolder {
            binding=viewBinding
            //在这里进行视图的加载
            val viewHolder = CommonViewHolder(
                viewBinding.root
            )
            return viewHolder
        }
    }
    fun getBinding(): ViewDataBinding? {
        return binding
    }
}