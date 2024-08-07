package com.example.prjfinalmobile.Forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.R
import com.example.prjfinalmobile.Viewmodel.UsuarioViewModel
import com.example.prjfinalmobile.Viewmodel.UsuarioViewModelFatory

@Composable
fun frmCadastrarUsuario(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        val context = LocalContext.current
        val db = SystemDataBase.getDataBase(context)
        val usuViewModel: UsuarioViewModel = viewModel(
            factory = UsuarioViewModelFatory(db)
        )

        val loginState = usuViewModel.usuState.collectAsState()
        val passState = usuViewModel.usuState.collectAsState()
        val emailState = usuViewModel.usuState.collectAsState()

        Button(
            onClick = { onBack() },
            modifier = Modifier
                .padding(start = 5.dp, top = 5.dp)
        ) {
            Text(
                text = "← Voltar",

            )
        }

        Row (modifier = Modifier.padding(top = 40.dp)) {
            Text(
                text = "Registro de Usuário",
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            )
        }

        Row {
            Text(
                text = "Login",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
            )
        }


        Row(

        ) {

            OutlinedTextField(
                value = loginState.value.login,
                onValueChange = {usuViewModel.updateLogin(it)},
                modifier = Modifier
                    .padding(start = 50.dp, top = 10.dp, end = 50.dp)
            )
        }

        Row {
            Text(
                text = "Senha",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
        }


        Row(
        ) {
            val visible = remember { mutableStateOf(true) }

            OutlinedTextField(
                value = passState.value.password,
                onValueChange = { usuViewModel.updatePassword(it) },
                label = {
                    Text(text = "Senha")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (visible.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(onClick = {
                        visible.value = (!visible.value)
                    }) {
                        if (visible.value)
                            Icon(
                                painterResource(id = R.drawable.visiblee), ""
                            )
                        else
                            Icon(
                                painterResource(id = R.drawable.nonvisible), ""
                            )
                    }
                },
                modifier = Modifier
                    .padding(start = 50.dp, top = 10.dp, end = 50.dp)
            )
        }

        Row {
            Text(
                text = "Email",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
        }

        Row(

        ) {
            OutlinedTextField(
                value = emailState.value.email,
                onValueChange = {usuViewModel.updateEmail(it)},
                modifier = Modifier
                    .padding(start = 50.dp, top = 10.dp, end = 50.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }


        Row {
            Button(
                onClick = {
                    usuViewModel.save()
                    onBack() },
                modifier = Modifier
                    .padding(start = 127.dp, top = 25.dp)


            ) {
                Text(
                    text = "Cadastrar",
                    fontSize = 22.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAbout() {
    frmCadastrarUsuario(onBack = {})
}