package mx.uttt.palabrin.presentation.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Immutable
data class ExtendedColorScheme(
    val awesomeYellow: ColorFamily,
    val awesomeOrange: ColorFamily,
    val awesomePink: ColorFamily,
    val awesomePurple: ColorFamily,
    val awesomeGreen: ColorFamily,
    val awesomeBlue: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowLight,
  onAwesomeYellowLight,
  awesomeYellowContainerLight,
  onAwesomeYellowContainerLight,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeLight,
  onAwesomeOrangeLight,
  awesomeOrangeContainerLight,
  onAwesomeOrangeContainerLight,
  ),
  awesomePink = ColorFamily(
  awesomePinkLight,
  onAwesomePinkLight,
  awesomePinkContainerLight,
  onAwesomePinkContainerLight,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleLight,
  onAwesomePurpleLight,
  awesomePurpleContainerLight,
  onAwesomePurpleContainerLight,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenLight,
  onAwesomeGreenLight,
  awesomeGreenContainerLight,
  onAwesomeGreenContainerLight,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueLight,
  onAwesomeBlueLight,
  awesomeBlueContainerLight,
  onAwesomeBlueContainerLight,
  ),
)

val extendedDark = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowDark,
  onAwesomeYellowDark,
  awesomeYellowContainerDark,
  onAwesomeYellowContainerDark,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeDark,
  onAwesomeOrangeDark,
  awesomeOrangeContainerDark,
  onAwesomeOrangeContainerDark,
  ),
  awesomePink = ColorFamily(
  awesomePinkDark,
  onAwesomePinkDark,
  awesomePinkContainerDark,
  onAwesomePinkContainerDark,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleDark,
  onAwesomePurpleDark,
  awesomePurpleContainerDark,
  onAwesomePurpleContainerDark,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenDark,
  onAwesomeGreenDark,
  awesomeGreenContainerDark,
  onAwesomeGreenContainerDark,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueDark,
  onAwesomeBlueDark,
  awesomeBlueContainerDark,
  onAwesomeBlueContainerDark,
  ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowLightMediumContrast,
  onAwesomeYellowLightMediumContrast,
  awesomeYellowContainerLightMediumContrast,
  onAwesomeYellowContainerLightMediumContrast,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeLightMediumContrast,
  onAwesomeOrangeLightMediumContrast,
  awesomeOrangeContainerLightMediumContrast,
  onAwesomeOrangeContainerLightMediumContrast,
  ),
  awesomePink = ColorFamily(
  awesomePinkLightMediumContrast,
  onAwesomePinkLightMediumContrast,
  awesomePinkContainerLightMediumContrast,
  onAwesomePinkContainerLightMediumContrast,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleLightMediumContrast,
  onAwesomePurpleLightMediumContrast,
  awesomePurpleContainerLightMediumContrast,
  onAwesomePurpleContainerLightMediumContrast,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenLightMediumContrast,
  onAwesomeGreenLightMediumContrast,
  awesomeGreenContainerLightMediumContrast,
  onAwesomeGreenContainerLightMediumContrast,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueLightMediumContrast,
  onAwesomeBlueLightMediumContrast,
  awesomeBlueContainerLightMediumContrast,
  onAwesomeBlueContainerLightMediumContrast,
  ),
)

val extendedLightHighContrast = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowLightHighContrast,
  onAwesomeYellowLightHighContrast,
  awesomeYellowContainerLightHighContrast,
  onAwesomeYellowContainerLightHighContrast,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeLightHighContrast,
  onAwesomeOrangeLightHighContrast,
  awesomeOrangeContainerLightHighContrast,
  onAwesomeOrangeContainerLightHighContrast,
  ),
  awesomePink = ColorFamily(
  awesomePinkLightHighContrast,
  onAwesomePinkLightHighContrast,
  awesomePinkContainerLightHighContrast,
  onAwesomePinkContainerLightHighContrast,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleLightHighContrast,
  onAwesomePurpleLightHighContrast,
  awesomePurpleContainerLightHighContrast,
  onAwesomePurpleContainerLightHighContrast,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenLightHighContrast,
  onAwesomeGreenLightHighContrast,
  awesomeGreenContainerLightHighContrast,
  onAwesomeGreenContainerLightHighContrast,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueLightHighContrast,
  onAwesomeBlueLightHighContrast,
  awesomeBlueContainerLightHighContrast,
  onAwesomeBlueContainerLightHighContrast,
  ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowDarkMediumContrast,
  onAwesomeYellowDarkMediumContrast,
  awesomeYellowContainerDarkMediumContrast,
  onAwesomeYellowContainerDarkMediumContrast,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeDarkMediumContrast,
  onAwesomeOrangeDarkMediumContrast,
  awesomeOrangeContainerDarkMediumContrast,
  onAwesomeOrangeContainerDarkMediumContrast,
  ),
  awesomePink = ColorFamily(
  awesomePinkDarkMediumContrast,
  onAwesomePinkDarkMediumContrast,
  awesomePinkContainerDarkMediumContrast,
  onAwesomePinkContainerDarkMediumContrast,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleDarkMediumContrast,
  onAwesomePurpleDarkMediumContrast,
  awesomePurpleContainerDarkMediumContrast,
  onAwesomePurpleContainerDarkMediumContrast,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenDarkMediumContrast,
  onAwesomeGreenDarkMediumContrast,
  awesomeGreenContainerDarkMediumContrast,
  onAwesomeGreenContainerDarkMediumContrast,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueDarkMediumContrast,
  onAwesomeBlueDarkMediumContrast,
  awesomeBlueContainerDarkMediumContrast,
  onAwesomeBlueContainerDarkMediumContrast,
  ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
  awesomeYellow = ColorFamily(
  awesomeYellowDarkHighContrast,
  onAwesomeYellowDarkHighContrast,
  awesomeYellowContainerDarkHighContrast,
  onAwesomeYellowContainerDarkHighContrast,
  ),
  awesomeOrange = ColorFamily(
  awesomeOrangeDarkHighContrast,
  onAwesomeOrangeDarkHighContrast,
  awesomeOrangeContainerDarkHighContrast,
  onAwesomeOrangeContainerDarkHighContrast,
  ),
  awesomePink = ColorFamily(
  awesomePinkDarkHighContrast,
  onAwesomePinkDarkHighContrast,
  awesomePinkContainerDarkHighContrast,
  onAwesomePinkContainerDarkHighContrast,
  ),
  awesomePurple = ColorFamily(
  awesomePurpleDarkHighContrast,
  onAwesomePurpleDarkHighContrast,
  awesomePurpleContainerDarkHighContrast,
  onAwesomePurpleContainerDarkHighContrast,
  ),
  awesomeGreen = ColorFamily(
  awesomeGreenDarkHighContrast,
  onAwesomeGreenDarkHighContrast,
  awesomeGreenContainerDarkHighContrast,
  onAwesomeGreenContainerDarkHighContrast,
  ),
  awesomeBlue = ColorFamily(
  awesomeBlueDarkHighContrast,
  onAwesomeBlueDarkHighContrast,
  awesomeBlueContainerDarkHighContrast,
  onAwesomeBlueContainerDarkHighContrast,
  ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun PalabrinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

