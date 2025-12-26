package com.example.maatsupervisor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.TodayCaseDeny
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDenyBottomSheet(
    item: TodayCaseDeny,
    onDismiss: () -> Unit
) {
    var selectedReason by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    val isMyEndIssue = selectedReason == "Issue from my end"

    ModalBottomSheet(onDismissRequest = onDismiss) {

        Column(Modifier.padding(16.dp)) {

            Text("Vehicle: ${item.vehicleNo}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            Text("RO Remarks: ${item.remark}", color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = selectedReason,
                    onValueChange = { selectedReason = it },
                    label = { Text("Your Action") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))

            if (!isMyEndIssue) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("EMT GID & Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Pilot GID & Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text("Final Remarks") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Action")
            }
        }
    }
}
