package com.example.childmanager.arch.view.ktx

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.childmanager.arch.ktx.getString
import com.example.childmanager.ui.view.TextProvider

@BindingAdapter("setText")
fun TextView.setText(textProvider: TextProvider?) {
    textProvider?.let { text = textProvider.getString(context) }
}