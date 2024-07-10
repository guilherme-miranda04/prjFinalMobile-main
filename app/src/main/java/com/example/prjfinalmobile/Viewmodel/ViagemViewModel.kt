package com.example.prjfinalmobile.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.ClassDao.ViagemDao
import com.example.prjfinalmobile.DataBase.SystemDataBase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ViagemViewModelFatory(val db: SystemDataBase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViagemViewModel(db.viagemDao) as T
    }
}

class ViagemViewModel(val viagemDao: ViagemDao): ViewModel() {
    private val _uiState = MutableStateFlow(Viagem())
    val uiState: StateFlow<Viagem> = _uiState.asStateFlow()

    fun updateDestination(destination: String){
        _uiState.update {
            it.copy(destination = destination)
        }
    }

    fun updateType(type: TipoViagem){
        _uiState.update {
            it.copy(type = type)
        }
    }

    fun updateStartDate(startDate: Date?){
        _uiState.update {
            it.copy(startDate = startDate)
        }
    }

    fun updateEndDate(endDate: Date?){
        _uiState.update {
            it.copy(endDate = endDate)
        }
    }

    fun updateBudget(budget: Float){
        _uiState.update {
            it.copy(budget = budget)
        }
    }

    fun updateId(id: Long){
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun save(){
        viewModelScope.launch {
            val id = viagemDao.upsert(uiState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }

    fun delete(viagem: Viagem){
        viewModelScope.launch {
            viagemDao.delete(viagem)
        }
    }

    suspend fun findById(id: Long): Viagem? {
        val deferred : Deferred<Viagem?> = viewModelScope.async {
            viagemDao.findByid(id)
        }
        return deferred.await()
    }

    fun setUiState(viagem: Viagem) {
        _uiState.value = uiState.value.copy(
            id = viagem.id,
            destination = viagem.destination,
            type = viagem.type,
            startDate = viagem.startDate,
            endDate = viagem.endDate,
            budget = viagem.budget
        )
    }
}