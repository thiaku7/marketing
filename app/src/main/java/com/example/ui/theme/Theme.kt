package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CustomDarkColorScheme = darkColorScheme(
  primary = AccentTerracota,
  onPrimary = TextCrema,
  secondary = PrimaryDeep,
  onSecondary = TextCrema,
  tertiary = TextMuted,
  onTertiary = TextCrema,
  background = BgDark,
  onBackground = TextCrema,
  surface = CardBg,
  onSurface = TextCrema,
  surfaceVariant = CardBg,
  onSurfaceVariant = TextCrema,
  outline = BorderDark
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = CustomDarkColorScheme,
    typography = Typography,
    content = content
  )
}
