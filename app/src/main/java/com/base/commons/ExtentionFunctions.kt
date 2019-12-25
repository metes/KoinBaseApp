package com.base.commons

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.base.commons.AnimUtils.startFadeInAnimation
import com.base.commons.AnimUtils.startFadeOutAnimation
import com.base.commons.AnimUtils.startSpringResize
import kotlin.reflect.KClass

fun ImageView.splashAnimation(functionOnFinish: () -> Unit) {
    startSpringResize(this, functionOnFinish)
}


fun View.visibileIf(showing: Boolean) {
    if (showing) {
        startFadeInAnimation(this, 300L)
    } else {
        startFadeOutAnimation(this, 300L)
    }
}

