package com.fuze.csgo.api

import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.model.team.TeamResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiFuzeService {

    @GET("csgo/matches/")
    suspend fun getListMatches(): Response<List<MatchResponse>>

    @GET("teams/{id}")
    suspend fun getTeamDetail(@Path("id") id: Long): Response<TeamResponse>
}