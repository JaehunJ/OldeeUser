package com.oldeee.user.custom

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("wonText")
fun setWonText(view: TextView, price: Int) {
    val decFormat = DecimalFormat("###,###")
    val str = decFormat.format(price)
    view.text = String.format("%s원", str)
}

@BindingAdapter("boldText")
fun formatTextBold(view: TextView, str: String) {
    val sb = SpannableString(str)
    val end = if (str.length < 3) 2 else 3
    sb.setSpan(StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

    view.text = sb
}