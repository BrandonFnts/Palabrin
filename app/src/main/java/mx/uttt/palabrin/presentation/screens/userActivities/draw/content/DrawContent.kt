package mx.uttt.palabrin.presentation.screens.userActivities.draw.content

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import com.google.mlkit.vision.digitalink.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
import mx.uttt.palabrin.R


@Composable
fun DrawContent(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: DrawViewModel = hiltViewModel()
) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LetterDrawingContent(
                viewModel = viewModel,
                navController = navController
            )


        }
}


@Composable
fun DrawingHeader(
    currentLetter: String,
    currentStreak: Int,
    onBackPressed: () -> Unit
) {
    var showExitDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        if (currentStreak > 0) {
                            showExitDialog = true
                        } else {
                            onBackPressed()
                        }
                    },
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title and letter
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.val_draw_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = currentLetter,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.val_strack_title),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentStreak.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(
                text= stringResource(R.string.val_leave_title)
            )
                    },
            text = {
                Text(
                    text = stringResource(R.string.val_leave_body) +
                        " $currentStreak " + stringResource(R.string.val_leave_body_2)
                )
                   },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                    onBackPressed()
                }) {
                    Text(text = stringResource(R.string.val_leave_button_yes))
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text(text = stringResource(R.string.val_leave_button_no))
                }
            }
        )
    }
}





@Composable
fun LetterDrawingContent(
    viewModel: DrawViewModel = hiltViewModel(),
    navController: NavController
) {

    var currentLetter by remember { mutableStateOf(viewModel.generateRandomLetter()) }
    var inkStrokes by remember { mutableStateOf<List<List<Offset>>>(emptyList()) }
    val streak = viewModel.streak.collectAsState()


    val inkRecognizer = remember {
        val options = DigitalInkRecognizerOptions.builder(viewModel.model).build()
        DigitalInkRecognition.getClient(options)
    }

    DrawingHeader(
        currentLetter = currentLetter,
        currentStreak = streak.value,
        onBackPressed = { navController.popBackStack() }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        DrawingCanvas(
            inkStrokes = inkStrokes,
            onInkStrokesChange = { inkStrokes = it },
            currentLetter = currentLetter
        )

        ActionButtons(
            currentLetter = currentLetter,
            inkStrokes = inkStrokes,
            inkRecognizer = inkRecognizer,
            onInkStrokesChange = { inkStrokes = it },
            onCurrentLetterChange = { currentLetter = it },
            viewModel = viewModel
        )


    }
}

@Composable
fun ActionButtons(
    currentLetter: String,
    inkStrokes: List<List<Offset>>,
    inkRecognizer: DigitalInkRecognizer,
    onInkStrokesChange: (List<List<Offset>>) -> Unit,
    onCurrentLetterChange: (String) -> Unit,
    viewModel: DrawViewModel
) {
    var showSuccessDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Escuchar eventos del ViewModel
    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.recognizeInk(inkStrokes, inkRecognizer) { recognizedText ->
                        if (recognizedText.equals(currentLetter, ignoreCase = true)) {
                            viewModel.incrementeStreak()
                            showSuccessDialog = true
                        } else {
                            viewModel.resetstreak(recognizedText)
                        }
                        if (recognizedText.equals(currentLetter, ignoreCase = true)) {
                            onCurrentLetterChange(viewModel.generateRandomLetter())
                            onInkStrokesChange(emptyList())
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.val_vefify_answer))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    onInkStrokesChange(emptyList())
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = stringResource(R.string.val_clean_answer))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onCurrentLetterChange(viewModel.generateRandomLetter())
                onInkStrokesChange(emptyList())
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = stringResource(R.string.val_change_letter))
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text(text = stringResource(R.string.val_alert_success_title)) },
                text = { Text( text = stringResource(R.string.val_alert_success_body)) },
                confirmButton = {
                    Button(onClick = { showSuccessDialog = false }) {
                        Text(text = stringResource(R.string.val_alert_continue_button))
                    }
                }
            )
        }
    }
}





@Composable
fun DrawingCanvas(
    inkStrokes: List<List<Offset>>,
    onInkStrokesChange: (List<List<Offset>>) -> Unit,
    currentLetter: String
) {
    var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }
    val guideLetterColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(400.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter { event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            currentStroke = mutableListOf(Offset(event.x, event.y))
                            true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            currentStroke = currentStroke + Offset(event.x, event.y)
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            onInkStrokesChange(inkStrokes + listOf(currentStroke))
                            currentStroke = emptyList()
                            true
                        }
                        else -> false
                    }
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = guideLetterColor.toArgb()
                    textSize = canvasHeight * 0.7f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }

                drawText(
                    currentLetter,
                    canvasWidth / 2,
                    canvasHeight / 2 + (paint.textSize / 3),
                    paint
                )
            }

            // Dibujar todos los trazos
            inkStrokes.forEach { stroke ->
                val path = Path()
                stroke.forEachIndexed { index, point ->
                    if (index == 0) path.moveTo(point.x, point.y)
                    else path.lineTo(point.x, point.y)
                }
                drawPath(
                    path = path,
                    color = primaryColor,
                    style =  Stroke(50f)
                )
            }

            // Dibujar trazo actual
            val currentPath = Path()
            currentStroke.forEachIndexed { index, point ->
                if (index == 0) currentPath.moveTo(point.x, point.y)
                else currentPath.lineTo(point.x, point.y)
            }
            drawPath(
                path = currentPath,
                color = primaryColor,
                style = Stroke(50f)
            )
        }
    }
}

