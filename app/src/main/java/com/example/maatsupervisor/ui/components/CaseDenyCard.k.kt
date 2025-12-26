package com.example.maatsupervisor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.TodayCaseDeny
import com.example.maatsupervisor.data.model.formatRemaining

@Composable
fun CaseDenyCard(
    item: TodayCaseDeny,
    onClick: () -> Unit
) {
    val remaining = item.remainingMillis
    val expired = item.isExpired

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable(enabled = !expired) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor =
                if (expired)
                    MaterialTheme.colorScheme.surfaceVariant
                else
                    MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(Modifier.padding(14.dp)) {

            Text(
                text = item.vehicleNo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "RO Remarks: ${item.remark}",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = formatRemaining(remaining),
                style = MaterialTheme.typography.labelMedium,
                color = if (expired)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )

            if (expired) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "Transferred to PM (No action allowed)",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
