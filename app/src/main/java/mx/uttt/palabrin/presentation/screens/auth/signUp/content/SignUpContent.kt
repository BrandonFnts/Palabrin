package mx.uttt.palabrin.presentation.screens.auth.signUp.content

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import mx.uttt.palabrin.presentation.components.GenericTextField
import mx.uttt.palabrin.presentation.components.PasswordTextField

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
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
                WelcomeText()
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                RegisterSection(viewModel = viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                LoginSection(navController  = navController)
            }
        }
    }
}

@Composable
fun WelcomeText(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RegisterSection(
    viewModel: SignUpViewModel
){
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column {
        SignUpPage(viewModel)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.onEvent(SignUpFormEvent.Submit)
                }
            }) {
                Text(text = "Registrar")
            }
        }
    }
}

@Composable
fun SignUpPage(viewModel: SignUpViewModel) {
    val name by viewModel.name.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")

    val nameError by viewModel.nameError.observeAsState()
    val emailError by viewModel.emailError.observeAsState()
    val passwordError by viewModel.passwordError.observeAsState()
    val repeatedPasswordError by viewModel.repeatedPasswordError.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        GenericTextField(
            value = name,
            onValueChange = { viewModel.onEvent(SignUpFormEvent.NameChanged(it)) },
            leadingIcon = Icons.Filled.AccountCircle,
            placeholder = R.string.auth_register_name,
            action = ImeAction.Next,
            errorMessage = nameError
        )
        Spacer(modifier = Modifier.height(10.dp))
        GenericTextField(
            value = email,
            onValueChange = { viewModel.onEvent(SignUpFormEvent.EmailChanged(it)) },
            leadingIcon = Icons.Filled.AlternateEmail,
            placeholder = R.string.auth_login_email,
            action = ImeAction.Next,
            errorMessage = emailError
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextField(
            value = password,
            onTextFieldChange = { viewModel.onEvent(SignUpFormEvent.PasswordChanged(it)) },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Next,
            errorMessage = passwordError
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextField(
            value = confirmPassword,
            onTextFieldChange = { viewModel.onEvent(SignUpFormEvent.RepeatedPasswordChanged(it)) },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_register_confirm_password,
            action = ImeAction.Done,
            errorMessage = repeatedPasswordError
        )
    }
}

@Composable
fun LoginSection(navController: NavController){
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.auth_already_account) + " ")

        pushStringAnnotation(tag = "login", annotation = "")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(id = R.string.auth_login_dialog))
        }
        pop()
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp), contentAlignment = Alignment.Center){
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "login", start = offset, end = offset).firstOrNull()?.let {
                navController.popBackStack()
            }
        }, style = TextStyle(color = Color.Gray, fontStyle = FontStyle.Italic))
    }
}