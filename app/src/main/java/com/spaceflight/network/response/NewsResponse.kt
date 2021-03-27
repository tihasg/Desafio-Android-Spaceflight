package com.spaceflight.network.response

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "id")
    var id: String?,

    @Json(name = "featured")
    var featured: Boolean?,

    @Json(name = "title")
    var title: String?,

    @Json(name = "url")
    var url: String?,

    @Json(name = "imageUrl")
    var imageUrl: String?,

    @Json(name = "newsSite")
    var newsSite: String?,

    @Json(name = "summary")
    var summary: String?,

    @Json(name = "launches")
    var launches: List<LaunchesResponse?>,

    @Json(name = "events")
    var events: List<EventsResponse>

)

data class LaunchesResponse(
    @Json(name = "id")
    var id: String?,

    @Json(name = "provider")
    var provider: String?
)


data class EventsResponse(
    @Json(name = "id")
    var id: String?,

    @Json(name = "provider")
    var provider: String?
)