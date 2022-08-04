package com.woowahan.accountbook.ui.theme

import androidx.annotation.DrawableRes
import com.woowahan.accountbook.R

sealed class Icons (@DrawableRes val iconId: Int) {
    object ArrowBottom: Icons(R.drawable.ic_arrow_bottom)
    object ArrowLeft: Icons(R.drawable.ic_arrow_left)
    object ArrowRight: Icons(R.drawable.ic_arrow_right)
    object Back: Icons(R.drawable.ic_back)
    object Calendar: Icons(R.drawable.ic_calendar_month)
    object Check: Icons(R.drawable.ic_check)
    object CheckBox: Icons(R.drawable.ic_checkbox)
    object CheckBoxChecked: Icons(R.drawable.ic_checkbox_checked)
    object CheckBoxWhite: Icons(R.drawable.ic_checkbox_white)
    object CheckBoxCheckedWhite: Icons(R.drawable.ic_checkbox_checked_white)
    object DocumentList: Icons(R.drawable.ic_document_list)
    object Graph: Icons(R.drawable.ic_graph)
    object Plus: Icons(R.drawable.ic_plus)
    object Settings: Icons(R.drawable.ic_settings)
    object Trash: Icons(R.drawable.ic_trash)
}