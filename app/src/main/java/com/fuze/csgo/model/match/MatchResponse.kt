package com.fuze.csgo.model.match

import java.io.Serializable

data class MatchResponse (
    val tournament_id: Long?,
    val scheduled_at: String?,
    val id: Long?,
    val name: String?,
    val league_id: Long?,
    val serie_id: Long?,
    val status: String?,
    val opponents: List<OpponentList>?,
    val league: LeagueResponse?,
    val serie: SerieResponse?,
) : Serializable
