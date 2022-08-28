package com.choota.dmotion.data.remote.dto

import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.domain.model.ChannelPage
import com.choota.dmotion.domain.model.VideoPage
import com.choota.dmotion.util.resolve
import com.google.gson.annotations.SerializedName

/**
 * ChannelDto will used by both Channel page to list channels
 */
data class ChannelDto(
    @field:SerializedName("explicit")
    val explicit: Boolean? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("has_more")
    val hasMore: Boolean? = null,

    @field:SerializedName("list")
    val list: List<ChannelItem>
)

/**
 * ChannelItem will used to populate channel list in landing page
 */
data class ChannelItem(

    @field:SerializedName("created_time")
    val createdTime: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null
)

/**
 * toChannels will convert response to only containing channel values to list
 */
fun ChannelDto.toChannels(): List<Channel> {
    return list.map {
        Channel(
            createdTime = it.createdTime.resolve(),
            name = it.name.resolve(),
            description = it.description.resolve(),

            )
    }
}

/**
 * toPage will convert Channel list to only what we need
 */
fun ChannelDto.toPage(): ChannelPage {
    return ChannelPage(
        this.page.resolve(),
        this.toChannels(),
        this.total.resolve()
    )
}