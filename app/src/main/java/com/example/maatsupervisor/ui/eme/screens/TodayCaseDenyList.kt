package com.example.maatsupervisor.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.TodayCaseDeny
import com.example.maatsupervisor.ui.eme.screens.components.CaseDenyCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayCaseDenyList(
    list: List<TodayCaseDeny>,
    onItemClick: (TodayCaseDeny) -> Unit
) {
    if (list.isEmpty()) return

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { item ->
            CaseDenyCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}
