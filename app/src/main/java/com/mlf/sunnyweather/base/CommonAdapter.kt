package com.mlf.wanandroid.base

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @description: TODO RecylerView万能适配器
 * @author: mlf
 * @date: 2024/9/6 11:21
 * @version: 1.0
 */
open class CommonAdapter<T,V : ViewDataBinding> : RecyclerView.Adapter<CommonViewHolder> {
    private var onItemClick:OnItemClick<T>?=null

    interface OnItemClick<U> {
         fun onItemClick(position:Int, view: View, data:U)
    }

    //构造方法 在这里传入 实体类与接口
    constructor(data: List<T>, listener: OnBindDataListener<T>){
        this.mData = data
        this.onBindDataListener = listener
    }
    //对需要分类加载布局的列表 使用OnMoreBindDataListener 这个方法提供了我们要设置的type
    constructor(data: List<T>, listener: OnMoreBindDataListener<T>){
        this.mData = data
        this.onBindDataListener=listener
        this.onMoreBindDataListener = listener
    }

    //数据
    private lateinit var mData: List<T>
    //接口
    private  var onBindDataListener: OnBindDataListener<T>?=null
    private  var onMoreBindDataListener: OnMoreBindDataListener<T>?=null
    //这里是 向viewholder中传入要加载的布局id
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CommonViewHolder {
        val inflate = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            onBindDataListener!!.getLayoutId(viewType),
            parent,
            false
        )
        Log.i("CommonAdapter", "onCreateViewHolder")
        return CommonViewHolder.getViewHolder(inflate)

    }
    fun setOnItemClick(onItemClick:OnItemClick<T>){
        this.onItemClick=onItemClick
    }
    //加载的个数
    override fun getItemCount(): Int {
        return mData.size
    }
    //这里是 实体类与布局进行绑定的方法
    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        //这个是自定义的
        if (onItemClick!=null){
            holder.itemView.setOnClickListener {
                onItemClick?.onItemClick(position,holder.itemView,mData[position])
            }
        }
        onBindDataListener?.onBindViewHolder(mData[position],holder,getItemViewType(position),position)
    }

    override fun getItemViewType(position: Int): Int {
        if (onMoreBindDataListener!=null){
            return onMoreBindDataListener!!.getItemViewType(position)
        }
        return 0
    }
    interface OnBindDataListener<T> {
        fun onBindViewHolder(model:T,viewHolder:CommonViewHolder,type:Int,position:Int)
        fun getLayoutId(type:Int):Int
    }
    interface OnMoreBindDataListener<T>:OnBindDataListener<T> {
        fun getItemViewType(position:Int):Int
    }


}