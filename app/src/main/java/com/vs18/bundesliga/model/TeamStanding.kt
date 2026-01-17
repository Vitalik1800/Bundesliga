package com.vs18.bundesliga.model

data class TeamStanding(
    val rank: Int,
    val team: TeamInfo,
    val points: Int,
    val goalsDiff: Int,
    val all: MatchStars
)