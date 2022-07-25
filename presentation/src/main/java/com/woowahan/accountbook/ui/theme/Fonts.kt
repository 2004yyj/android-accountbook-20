package com.woowahan.accountbook.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.woowahan.accountbook.R

val FontFamily.Companion.KoPubPro: FontFamily
    get() = FontFamily(
        Font(R.font.ko_pub_dotum_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.ko_pub_dotum_medium, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.ko_pub_dotum_light, FontWeight.Light, FontStyle.Normal),
    )