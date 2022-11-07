package com.anthonyessaye.simpledall_e.Enumerations

enum class ImageSize(val value: Int, val cost: Double, val resolution: Int) {
    small(0,0.016,256),
    medium(1,0.018, 512),
    large(2,0.02,1024);

    companion object {
        fun fromInt(value: Int) = ImageSize.values().first { it.value == value }
    }
}