package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BgDark
import com.example.ui.theme.AccentTerracota
import com.example.ui.theme.TextMuted

import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun SplashScreen() {
    var startAnim by remember { mutableStateOf(false) }
    
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = twinPulseSpec(2000),
        label = "Alpha"
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnim) 1.05f else 0.95f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = EaseInOutCubic
        ),
        label = "Scale"
    )

    LaunchedEffect(Unit) {
        startAnim = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.graphicsLayerAlphaAndScale(alphaAnim, scaleAnim)
        ) {
            Text(
                text = "marketing",
                color = AccentTerracota,
                fontSize = 54.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Light,
                letterSpacing = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "experiential narrative",
                color = TextMuted,
                fontSize = 11.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                letterSpacing = 6.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

private fun twinPulseSpec(duration: Int): AnimationSpec<Float> {
    return tween(
        durationMillis = duration,
        easing = EaseInOutCubic
    )
}

private fun Modifier.graphicsLayerAlphaAndScale(alpha: Float, scale: Float): Modifier {
    return this.graphicsLayer {
        this.alpha = alpha
        this.scaleX = scale
        this.scaleY = scale
    }
}
