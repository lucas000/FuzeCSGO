package com.fuze.csgo.repository

import com.fuze.csgo.model.match.*
import com.fuze.csgo.model.team.PlayerResponse
import com.fuze.csgo.model.team.TeamResponse
import com.fuze.csgo.other.Resource

class FakeFuzeRepository: FuzeRepository {

    private val opponents = listOf(
        OpponentList(
            OpponentResponse(
                212,
                "url",
                "123"
            )
        )
    )

    private val leagues = LeagueResponse(212, "url", "123")
    private val series = SerieResponse(212, "url")
    private val matchesList = mutableListOf(
        MatchResponse(
        12344,
        "",
        1234,
        "name",
        2334,
        12334,
        "started",
        opponents,
        leagues,
        series
        )
    )

    private val players = listOf(
        PlayerResponse(
        123123,
        "name",
        "first name",
        "last name",
        "img url")
    )

    private val teams = listOf(
        TeamResponse(123123, "desc1", "1534784400000", players),
        TeamResponse(123124, "desc1", "1534784400000", players))

    private val observableMatchesList = matchesList
    private val observableTeams = teams

    override suspend fun getListMatches(): Resource<List<MatchResponse>> {
        return Resource.success(observableMatchesList)
    }

    override suspend fun getTeamDetail(teamOne: Long, teamTwo: Long): Resource<List<TeamResponse>> {
        return Resource.success(observableTeams)
    }
}