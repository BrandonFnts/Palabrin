package mx.uttt.palabrin.presentation.screens.auth.login.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mx.uttt.palabrin.R
import mx.uttt.palabrin.presentation.components.AppLogo
import mx.uttt.palabrin.presentation.components.GenericOutlinedButton
import mx.uttt.palabrin.presentation.components.GenericTextField
import mx.uttt.palabrin.presentation.components.PasswordTextField
import mx.uttt.palabrin.presentation.enums.Routes

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                AppLogo()
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                LoginForm(navController, viewModel)
            }
        }
    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel) {
    val scope = rememberCoroutineScope()
    //Variables
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    //Error variables
    val emailError by viewModel.emailError.observeAsState()
    val passwordError by viewModel.passwordError.observeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        //Text Field for email
        GenericTextField(
            value = email,
            onValueChange = {
                viewModel.onEvent(
                    LoginFormEvent.EmailChanged(it),
                )
            },
            leadingIcon = Icons.Filled.AlternateEmail,
            placeholder = R.string.auth_login_email,
            action = ImeAction.Next,
            errorMessage = emailError
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text Field for password
        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onEvent(
                    LoginFormEvent.PasswordChanged(it),
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Done,
            errorMessage = passwordError
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Button for login
        GenericOutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = R.string.auth_login_title,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = {
                scope.launch {
                    viewModel.onEvent(
                        LoginFormEvent.Submit,
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Text Button for SignUp
        SignUpSection(navController = navController)
    }

}

@Composable
fun SignUpSection(navController: NavController){
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.auth_no_account) + " ")

        pushStringAnnotation(tag = "signup", annotation = "")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(id = R.string.auth_register_title))
        }
        pop()
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp), contentAlignment = Alignment.Center){
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "signup", start = offset, end = offset).firstOrNull()?.let {
                navController.navigate(Routes.SIGNUP.name)
            }
        }, style = TextStyle(color = Color.Gray, fontStyle = FontStyle.Italic))
    }
}
