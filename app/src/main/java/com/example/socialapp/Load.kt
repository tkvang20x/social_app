package com.example.socialapp

import android.app.Dialog
import android.content.Context
import android.view.Window
import java.lang.Exception

object Load {
    private  var loading: Dialog? =null

    fun showLoading(context: Context?) {
        if (loading == null) {
            loading = Dialog(context!!)
            loading?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loading?.setContentView(R.layout.layout_loading)
            loading?.setCancelable(false)
            loading?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            loading?.show()
        }
    }

    fun hideLoading() {
        try {
            loading!!.dismiss()
           loading = null
        } catch (e: Exception) {
        }
    }
}