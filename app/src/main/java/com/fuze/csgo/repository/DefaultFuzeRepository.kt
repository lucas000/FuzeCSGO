package com.fuze.csgo.repository

import com.fuze.csgo.api.ApiFuzeService
import com.fuze.csgo.model.match.MatchResponse
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

    override suspend fun getTeamDetail(eventId: String): Resource<TeamResponse> {
        return try {
            val response = fuzeService.getTeamDetail(eventId)

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
}