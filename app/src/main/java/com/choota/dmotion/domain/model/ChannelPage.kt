package com.choota.dmotion.domain.model

data class ChannelPage(
    val page: Int = 1,
    val list: List<Channel> = listOf(),
    val total: Int = 1
)
