package com.vs18.bundesliga.model

data class FixtureWrapper(
    val fixture: Fixture,
    val teams: Teams,
    val goals: Goals,
    val score: Score,
    val league: LeagueInfo
)