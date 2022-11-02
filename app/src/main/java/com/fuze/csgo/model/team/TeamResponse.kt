package com.fuze.csgo.model.team

data class TeamResponse(
    val id: Long?,
    val name: String?,
    val image_url: String?,
    val players: List<PlayerResponse>?
)