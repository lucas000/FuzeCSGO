package com.fuze.csgo.ui

import androidx.lifecycle.*
import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.model.team.TeamList
import com.fuze.csgo.model.team.TeamResponse
import com.fuze.csgo.other.Event
import com.fuze.csgo.other.Resource
import com.fuze.csgo.repository.FuzeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuzeViewModel @Inject constructor(
    private val repository: FuzeRepository
) : ViewModel() {

    private val _matches = MutableLiveData<Event<Resource<List<MatchResponse>>>>()
    var matches: LiveData<Event<Resource<List<MatchResponse>>>> = _matches

    private val _teams = MutableLiveData<Event<Resource<List<TeamResponse>>>>()
    var teams: LiveData<Event<Resource<List<TeamResponse>>>> = _teams

    fun getMatchesList() {
        _matches.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.getListMatches()
            _matches.value = Event(response)
        }
    }

    fun getTeamMembers(teamOne: Long, teamTwo: Long) {
        _teams.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.getTeamDetail(teamOne, teamTwo)
            _teams.value = Event(response)
        }
    }
}