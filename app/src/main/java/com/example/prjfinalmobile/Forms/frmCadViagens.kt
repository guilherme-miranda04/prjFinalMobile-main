package com.example.prjfinalmobile.Forms

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.ClassComponents.MyTopBar
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.Viewmodel.ViagemViewModel
import com.example.prjfinalmobile.Viewmodel.ViagemViewModelFatory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun frmCadViagens(onBack: ()->Unit, viagemId : Long?) {
    Scaffold(
        topBar = {
            MyTopBar("Cadastrar viagem") { onBack() }
        }
    ) { it ->
        val context = LocalContext.current
        val db = SystemDataBase.getDataBase(context)
        val viagemViewModel: ViagemViewModel = viewModel(
            factory = ViagemViewModelFatory(db)
        )

        LaunchedEffect(viagemId) {
            if (viagemId != null){
                val viagem =  viagemViewModel.findById(viagemId)
                viagem?.let { viagemViewModel.setUiState(viagem) }
            }
        }

        val state = viagemViewModel.uiState.collectAsState()

        val showDatePickerDialogStartDate = remember { mutableStateOf(false) }
        val datePickerStateStartDate =
            remember { mutableStateOf(DatePickerState(CalendarLocale("PT-BR"))) }

        val showDatePickerDialogEndDate = remember { mutableStateOf(false) }
        val datePickerStateEndDate =
            remember { mutableStateOf(DatePickerState(CalendarLocale("PT-BR"))) }

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Destino",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row {
                OutlinedTextField(
                    value = state.value.destination,
                    onValueChange = { viagemViewModel.updateDestination(it) },
                    modifier = Modifier
                        .weight(4f)
                        .padding(top = 10.dp)
                )
            }

            Row {
                Text(
                    text = "Tipo",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.value.type == TipoViagem.Leisure,
                    onClick = { viagemViewModel.updateType(TipoViagem.Leisure) },
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Lazer",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.value.type == TipoViagem.Business,
                    onClick = { viagemViewModel.updateType(TipoViagem.Business) },
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Negócios",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                )
            }

                Row {

                    Text(
                        text = "Data Inicio",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {

                    if (showDatePickerDialogStartDate.value) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialogStartDate.value = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        datePickerStateStartDate.value.selectedDateMillis?.let { millis ->
                                            viagemViewModel.updateStartDate(Date(millis))
                                        }
                                        showDatePickerDialogStartDate.value = false
                                    }) {
                                    Text(text = "Escolher data")
                                }
                            },
                            modifier = Modifier
                                .weight(4f)
                        ) {
                            DatePicker(state = datePickerStateStartDate.value)
                        }
                    }

                    OutlinedTextField(
                        value = state.value.startDate?.time?.toBrazilianDateFormat() ?: "",
                        onValueChange = { /*Campo de atualização -> feito pelo DatePicker */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) {
                                    showDatePickerDialogStartDate.value = true
                                }
                            },
                        readOnly = true
                    )

                }

                Row {

                    Text(
                        text = "Data Final",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {

                    if (showDatePickerDialogEndDate.value) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialogEndDate.value = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        datePickerStateEndDate.value.selectedDateMillis?.let { millis ->
                                            viagemViewModel.updateEndDate(Date(millis))
                                        }
                                        showDatePickerDialogEndDate.value = false
                                    }) {
                                    Text(text = "Escolher data")
                                }
                            },
                            modifier = Modifier
                                .weight(4f)
                        ) {
                            DatePicker(state = datePickerStateEndDate.value)
                        }
                    }

                    OutlinedTextField(
                        value = state.value.startDate?.time?.toBrazilianDateFormat() ?: "",
                        onValueChange = { /* Campo de atualização -> feito pelo DatePicker */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) {
                                    showDatePickerDialogEndDate.value = true
                                }
                            },
                        readOnly = true
                    )

                }

                Row {

                    Text(
                        text = "Orçamento",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {
                    OutlinedTextField(
                        value = state.value.budget?.toString() ?: "",
                        onValueChange = { viagemViewModel.updateBudget(it.toFloat()) },
                        modifier = Modifier
                            .weight(4f)
                            .padding(top = 10.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viagemViewModel.save()
                            Toast.makeText(context, "Viagem salva!", Toast.LENGTH_SHORT).show()
                            onBack()
                        },
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .weight(2f)
                    ) {
                        Text(text = "Salvar")
                    }
                }

            }

        }
    }


    fun Long.toBrazilianDateFormat(
        pattern: String = "dd/MM/yyyy"
    ): String {
        val date = Date(this)
        val formatter = SimpleDateFormat(
            pattern, Locale("pt-br")
        ).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        return formatter.format(date)
    }
