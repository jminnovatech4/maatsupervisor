package com.example.maatsupervisor.ui.eme.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maatsupervisor.data.model.TodayCaseDeny

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDenyActionSheet(
    item: TodayCaseDeny,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var selectedReason by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val isMyEndIssue = selectedReason == "Issue from my end"

    ModalBottomSheet(onDismissRequest = onDismiss) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Vehicle: ${item.vehicleNo}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "RO Remarks: ${item.remark}",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))

            // 🔽 Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    value = selectedReason,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Your Remarks") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    remarkOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                selectedReason = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // 🔹 EMT / PILOT ONLY IF NOT MY END ISSUE
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

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text("Final Remarks") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onSubmit(remarks) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Action")
            }
        }
    }
}

val remarkOptions = listOf(
    "Issue from my end",
    "Pilot Issue",
    "EMT Issue",
    "Fleet Issue",
    "Call Not Picked"
)
