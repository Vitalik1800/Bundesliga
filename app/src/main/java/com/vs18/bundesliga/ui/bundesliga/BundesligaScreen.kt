package com.vs18.bundesliga.ui.bundesliga

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vs18.bundesliga.datastore.AuthPreferences
import com.vs18.bundesliga.ui.rounds.RoundDropdown
import com.vs18.bundesliga.viewmodel.FootballViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun BundesligaScreen(viewModel: FootballViewModel) {

    val context = LocalContext.current
    val prefs = remember { AuthPreferences(context) }
    val email by prefs.email.collectAsState(initial = null)

    val results by viewModel.results.collectAsState()
    val standings by viewModel.standings.collectAsState()
    val rounds by viewModel.rounds.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hello, ${email ?: "user"} 👋",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00b7eb)
                    )

                    val scope = rememberCoroutineScope()

                    TextButton(
                        onClick = {
                            scope.launch {
                                prefs.clear()
                            }
                        }
                    ) {
                        Text(
                            text = "Logout",
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "⚽ Bundesliga",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00b7eb)
                    )
                }
                Spacer(Modifier.height(24.dp))
            }

            item {
                Divider(
                    color = Color(0xFF2a2a2a),
                    thickness = 1.dp
                )
                Spacer(Modifier.height(16.dp))
            }

            if (loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            error?.let {
                item {
                    Text(
                        text = "Помилка: $it",
                        color = Color.Red
                    )
                }
            }

            if (standings.isNotEmpty()) {
                item {
                    Text(
                        "Tournament Table",
                        fontSize = 24.sp,
                        color = Color(0xFF00b7eb)
                    )
                    Spacer(Modifier.height(16.dp))
                }

                items(standings) { team ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1e1e1e)
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "${team.rank}.",
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.width(40.dp)
                            )

                            GlideImage(
                                model = team.team.logo,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            Text(
                                team.team.name,
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                "${team.points} pts.",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF43a047)
                            )
                        }
                    }
                }

                item { Spacer(Modifier.height(32.dp)) }
            }

            if (rounds.isNotEmpty()) {
                item {
                    RoundDropdown(rounds) {
                        viewModel.selectRound(it)
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            items(results) { match ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1e1e1e)
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlideImage(
                                    model = match.teams.home.logo,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )

                                Spacer(Modifier.width(8.dp))

                                Text(match.teams.home.name, color = Color.White)
                            }

                            Spacer(Modifier.width(12.dp))

                            Text(
                                "${match.goals.home} : ${match.goals.away}",
                                fontSize = 22.sp,
                                color = Color(0xFF43a047),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    match.teams.away.name,
                                    color = Color.White,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )

                                Spacer(Modifier.width(8.dp))

                                GlideImage(
                                    model = match.teams.away.logo,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        val penalty = match.score.penalty
                        if (penalty?.home != null && penalty.away != null) {
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "Penalty: ${penalty.home} : ${penalty.away}",
                                color = Color(0xFFFF9800),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            match.fixture.date.take(10),
                            color = Color(0xFFAAAAAA),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}