package com.fuze.csgo.ui

import androidx.lifecycle.*
import com.fuze.csgo.model.match.MatchResponse
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

    fun getMatchesList() {
        _matches.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.getListMatches()
            _matches.value = Event(response)
        }
    }
}