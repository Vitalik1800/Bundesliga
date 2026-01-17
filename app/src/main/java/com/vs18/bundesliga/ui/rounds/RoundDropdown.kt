package com.vs18.bundesliga.ui.rounds

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RoundDropdown(
    rounds: List<String>,
    onSelect: (String) -> Unit
) {
   var expanded by remember { mutableStateOf(false) }
   var selected by remember { mutableStateOf(rounds.first())}

   Box {
       OutlinedButton(onClick = { expanded = true }) {
           Text("Round: $selected")
       }

       DropdownMenu(
           expanded = expanded,
           onDismissRequest = { expanded = false }
       ) {
           rounds.forEach { round ->
               DropdownMenuItem(
                   text = { Text(round) },
                   onClick = {
                       selected = round
                       expanded = false
                       onSelect(round)
                   }
               )
           }
       }
   }
}
