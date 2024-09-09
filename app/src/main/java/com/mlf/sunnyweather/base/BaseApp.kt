package com.mlf.sunnyweather.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/8 12:42
 * @version: 1.0
 */
class BaseApp:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "yHlg6eNR7eEL54U5"
    }

    override fun onCreate() {
        super.onCreate()
        context =this
    }
    fun getContext(): Context {
        return context
    }
}