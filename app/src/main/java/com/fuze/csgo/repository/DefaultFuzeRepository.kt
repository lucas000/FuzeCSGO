package com.fuze.csgo.repository

import com.fuze.csgo.api.ApiFuzeService
import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.model.team.TeamList
import com.fuze.csgo.model.team.TeamResponse
import com.fuze.csgo.other.Resource
import javax.inject.Inject

class DefaultFuzeRepository @Inject constructor(
    private val fuzeService: ApiFuzeService
): FuzeRepository {
    override suspend fun getListMatches(): Resource<List<MatchResponse>> {
        return try {
            val response = fuzeService.getListMatches()

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Coundn't reach the server. Check your internet connection.", null)
        }
    }

    override suspend fun getTeamDetail(teamOne: Long, teamTwo: Long): Resource<List<TeamResponse>> {
        return try {
            var listTeamResponse = mutableListOf<TeamResponse>()

            val response1 = fuzeService.getTeamDetail(teamOne)
            val response2 = fuzeService.getTeamDetail(teamTwo)

            if (response1.isSuccessful && response2.isSuccessful) {
                response1.body()?.let { body1 ->
                    response2.body()?.let { body2 ->
                        listTeamResponse.add(body1)
                        listTeamResponse.add(body2)
                        return@let Resource.success(listTeamResponse)
                    }
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Coundn't reach the server. Check your internet connection.", null)
        }
    }
}