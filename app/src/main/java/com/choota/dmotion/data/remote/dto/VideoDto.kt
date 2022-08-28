package com.choota.dmotion.data.remote.dto

import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.domain.model.Video
import com.choota.dmotion.util.resolve
import com.google.gson.annotations.SerializedName

/**
 * VideoDto will be used in both videos page and the details page
 */
data class VideoDto(
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
	val list: List<VideoItem>
)

/**
 * VideoItem will be used to transfer data from list to details page
 */
data class VideoItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("audience")
	val audience: Any? = null,

	@field:SerializedName("channel.name")
	val channelName: String? = null,

	@field:SerializedName("views_total")
	val viewsTotal: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("thumbnail_720_url")
	val thumbnail720Url: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null
)


/**
 * toVideos will convert response to only containing video values to list and details
 */
fun VideoDto.toVideos(): List<Video> {
	return list.map {
		Video(
			duration = it.duration.resolve(),
			country = it.country.resolve(),
			audience = it.audience,
			channelName = it.channelName.resolve(),
			viewsTotal = it.viewsTotal.resolve(),
			description = it.description.resolve(),
			language = it.language.resolve(),
			thumbnail720Url = it.thumbnail720Url.resolve(),
			title = it.title.resolve(),
			tags = it.tags
		)
	}
}