package com.vs18.bundesliga.viewmodel

import androidx.lifecycle.*
import com.vs18.bundesliga.model.*
import com.vs18.bundesliga.repository.FootballRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FootballViewModel : ViewModel() {

    private val repository = FootballRepository()

    private val _allRounds =
        MutableStateFlow<Map<String, List<FixtureWrapper>>>(emptyMap())

    private val _selectedRound = MutableStateFlow<String?>(null)

    val rounds: StateFlow<List<String>> =
        _allRounds
            .map { roundsMap ->
                roundsMap.keys.sortedBy { extractRoundNumber(it) }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                emptyList()
            )

    val results: StateFlow<List<FixtureWrapper>> =
        combine(_allRounds, _selectedRound) { rounds, selected ->
            selected?.let { rounds[it] } ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _standings = MutableStateFlow<List<TeamStanding>>(emptyList())
    val standings: StateFlow<List<TeamStanding>> = _standings

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _loading.value = true

            repository.getAllResults()
                .onSuccess { fixtures ->
                    val grouped = fixtures.groupBy { it.league.round }
                    _allRounds.value = grouped
                    _selectedRound.value = grouped.keys.minOrNull()
                }
                .onFailure { _error.value = it.message }

            repository.getStandings()
                .onSuccess {
                    _standings.value =
                        it.response.first().league.standings.first()
                }
            _loading.value = false
        }
    }

    fun selectRound(round: String) {
        _selectedRound.value = round
    }

    private fun extractRoundNumber(round: String): Int {
        return Regex("\\d+")
            .find(round)
            ?.value
            ?.toInt()
            ?: Int.MAX_VALUE
    }
}