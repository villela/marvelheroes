package com.matheusvillela.marvelheroes.shared

enum class PortraitAspect(val path: String, val widthPixels: Int) {
    SMALL("portrait_small.jpg", 50),
    MEDIUM("portrait_medium.jpg", 100),
    XLARGE("portrait_xlarge.jpg", 150),
    FANTASTIC("portrait_fantastic.jpg", 168),
    INCREDIBLE("portrait_incredible.jpg", 216),
    UNCANNY("portrait_uncanny.jpg", 300)
}