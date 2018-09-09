package com.matheusvillela.marvelheroes.util

import android.content.Context
import com.matheusvillela.marvelheroes.shared.PortraitAspect

class PortraitAspectResolver {
    fun getPortraitAspect(context: Context, scale : Int): PortraitAspect {
        val metrics = context.resources.displayMetrics
        val magicNumber = metrics.density * 30 * scale
        return PortraitAspect.values().firstOrNull { magicNumber < it.widthPixels }
                ?: PortraitAspect.UNCANNY
    }
}