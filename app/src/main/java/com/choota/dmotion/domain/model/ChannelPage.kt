package com.choota.dmotion.domain.model

data class ChannelPage(
    val page: Int,
    val list: List<Channel>,
    val total: Int
)
