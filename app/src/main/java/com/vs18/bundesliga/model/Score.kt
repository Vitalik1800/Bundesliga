package com.vs18.bundesliga.model

data class Score(
    val fulltime: Goals?,
    val extratime: Goals?,
    val penalty: Goals?
)
