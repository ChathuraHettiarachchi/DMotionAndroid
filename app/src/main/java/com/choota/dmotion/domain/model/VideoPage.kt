package com.choota.dmotion.domain.model

data class VideoPage(
    val page: Int,
    val list: List<Video>,
    val total: Int
)
