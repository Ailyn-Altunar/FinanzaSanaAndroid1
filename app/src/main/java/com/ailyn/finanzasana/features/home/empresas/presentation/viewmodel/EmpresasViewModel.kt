package com.ailyn.finanzasana.features.home.empresas.presentation.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.home.empresas.domain.entities.Empresa
import com.ailyn.finanzasana.features.home.empresas.domain.usecases.GetEmpresasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpresasViewModel @Inject constructor(
    private val getEmpresasUseCase: GetEmpresasUseCase
) : ViewModel() {

    var empresas by mutableStateOf<List<Empresa>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            empresas = getEmpresasUseCase()
        }
    }
}
