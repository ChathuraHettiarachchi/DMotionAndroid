package com.choota.dmotion.domain.model

data class VideoPage(
    val page: Int = 1,
    val list: List<Video> = listOf(),
    val total: Int = 1
)
