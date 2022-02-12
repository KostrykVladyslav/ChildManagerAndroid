package com.example.childmanager.arch.ktx

import android.content.Context
import com.example.childmanager.ui.view.TextProvider

fun TextProvider.getString(context: Context) = when (this) {
    is TextProvider.Text -> text
    is TextProvider.ResText -> if (text == 0) "" else context.getString(text)
    is TextProvider.FormatResText -> if (res == 0) "" else context.getString(res, text)
}

fun String.toProvider(): TextProvider {
    return TextProvider.Text(text = this)
}