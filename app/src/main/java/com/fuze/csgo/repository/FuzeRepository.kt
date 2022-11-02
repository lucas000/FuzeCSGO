package com.fuze.csgo.repository

import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.model.team.TeamResponse
import com.fuze.csgo.other.Resource

interface FuzeRepository {

    suspend fun getListMatches(): Resource<List<MatchResponse>>

    suspend fun getTeamDetail(teamOne: Long, teamTwo: Long): Resource<List<TeamResponse>>
}