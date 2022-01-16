package com.naga.super_heroes.data.repository

import androidx.core.view.isVisible
import com.google.android.material.progressindicator.LinearProgressIndicator

fun LinearProgressIndicator.setProgressSafe(stat:String) {
    val nullableStat = stat.toIntOrNull()
    if (nullableStat == null) {
        this.isVisible = false
        this.isIndeterminate = true
        this.isVisible = true
    } else {
        this.progress = nullableStat
    }
}