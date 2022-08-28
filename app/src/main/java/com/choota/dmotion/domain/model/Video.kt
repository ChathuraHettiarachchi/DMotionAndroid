package com.choota.dmotion.domain.model

/**
 * Video model for UI population
 */
data class Video(
    val duration: Int? = null,
    val country: String? = null,
    val audience: Any? = null,
    val channelName: String? = null,
    val viewsTotal: Int? = null,
    val description: String? = null,
    val language: String? = null,
    val thumbnail720Url: String? = null,
    val title: String? = null,
    val tags: List<String?>? = null
)
