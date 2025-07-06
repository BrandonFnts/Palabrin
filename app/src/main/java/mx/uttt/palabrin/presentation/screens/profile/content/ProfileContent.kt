package mx.uttt.palabrin.presentation.screens.profile.content

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mx.uttt.palabrin.R
import mx.uttt.palabrin.presentation.enums.Routes

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(

        modifier = modifier
            .verticalScroll(scrollState)
            .padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileHeader(
                viewModel = viewModel
            )
            BodyOptions(navController = navController)
            LogOut(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun ProfileHeader(viewModel: ProfileViewModel) {
    val name by viewModel.userName.observeAsState("")
    val email by viewModel.userEmail.observeAsState("")
    val type by viewModel.userType.observeAsState("")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.fumo),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                IconButton(
                    onClick = { /* TODO: Navegar a Configuración */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                        .size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Configurar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            SimpleChip(text = type)
        }
    }
}

@Composable
fun SimpleChip(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun BodyOptions(navController: NavController) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            OptionItem(
                title = R.string.notification_option_title,
                body = R.string.notification_option_body,
                option = {
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        thumbContent = if (checked) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                },
                click = { checked = !checked }
            )
            OptionItem(
                title = R.string.Info_option_title,
                body = R.string.Info_option_body,
                option = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Información de la aplicación",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                click = {}
            )
            OptionItem(
                title = R.string.Crash_Error_title,
                body = R.string.Crash_Error_body,
                option = {
                    Icon(
                        imageVector = Icons.Filled.Mail,
                        contentDescription = "Reportar problema",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                click = {context.sendMail(to = "22300101@uttt.edu.mx")}
            )
        }
    }
}

@Composable
fun OptionItem(
    @StringRes title: Int,
    @StringRes body: Int,
    option: @Composable (() -> Unit)? = null,
    click: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { click() }
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = body),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        option?.invoke()
    }
}


@Composable
fun LogOut(
    navController: NavController,
    viewModel: ProfileViewModel
){
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .height(32.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Cerrar Sesion",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable {
                    scope.launch {
                        viewModel.logOut()
                    }
                    navController.navigate(Routes.LOGIN.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                }
            )
        }
    }
}


fun Context.sendMail(to: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(
            Intent.EXTRA_EMAIL, arrayOf(to),
        )
        intent.putExtra(
            Intent.EXTRA_SUBJECT, "Problemas con LogiTrans"
        )
        intent.setPackage("com.google.android.gm")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "no se encontró la aplicacion de Gmail.", Toast.LENGTH_SHORT).show()
    } catch (t: Throwable) {
        Toast.makeText(this, "Ocurrió un error al intentar enviar el correo.", Toast.LENGTH_SHORT).show()
    }
}