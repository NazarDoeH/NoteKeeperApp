package com.lab5.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lab5.data.sever_api.ServerAPI
import com.lab5.ui.theme.JetBrainsMono
import com.lab5.ui.theme.TextMain
import com.lab5.ui.theme.TextSecondary

@Composable
fun RegionInfoColumn(
    regionName: String,
    state: String,
    onClickIcon: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column with region name and update time
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = regionName,
                fontFamily = JetBrainsMono,
                color = TextMain
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = state,
                fontFamily = JetBrainsMono,
                color = TextSecondary
            )
        }

        // Icon
        Icon(
            imageVector = Icons.Default.ArrowForward, // Replace with your desired icon
            contentDescription = "Icon",
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickIcon() },
            tint = Color.Black
        )
    }
}