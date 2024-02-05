package com.example.juzzics.common.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId

@Composable
fun SimpleFAB(modifier: Modifier, text: String, image: ImageVector, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.layoutId("bt_prev"),
        shape = CircleShape
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                image,
                contentDescription = null
            )
            Text(text = text)
        }
    }
}