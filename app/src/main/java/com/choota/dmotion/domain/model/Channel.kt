package com.choota.dmotion.domain.model

/**
 * Channel model for UI population
 */
data class Channel(
    var createdTime: Int = -1,
    var name: String = "",
    var description: String = "",
    var id: String = "",
    var image: String = ""
)
