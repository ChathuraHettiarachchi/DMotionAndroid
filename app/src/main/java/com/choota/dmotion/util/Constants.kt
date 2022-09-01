package com.choota.dmotion.util

object Constants {
    const val DATABASE = "dmotion_database"
    const val BASE_URL = "https://api.dailymotion.com/"
    const val VIDEO_FIELDS =
        "views_total,audience,channel.name,country,duration,thumbnail_720_url,title,tags,description,language"
    const val CHANNEL_FIELDS = "id,name,description,created_time,slug"
    const val CHANNEL = "CHANNEL"
    const val IMAGE = "IMAGE"
    const val TITLE = "TITLE"
    const val DESCRIPTION = "DESCRIPTION"
    const val DETAILS = "DETAILS"

    const val PixaBASE_URL = "https://pixabay.com/"
    const val PixabayKey = "29628766-c529d59caea354059c02ef28d"

    val RESOURCE_VIDEOS = arrayOf(
        "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://storage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        "https://storage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
    )
}