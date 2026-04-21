package com.ailyn.finanzasana.features.home.categoria.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailyn.finanzasana.features.home.categoria.domain.entities.Categoria
import com.ailyn.finanzasana.features.home.categoria.domain.usecases.GetCategoriasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriasViewModel @Inject constructor(
    private val getCategoriasUseCase: GetCategoriasUseCase
) : ViewModel() {

    var categorias: List<Categoria> = emptyList()
        private set

    init {
        viewModelScope.launch {
            categorias = getCategoriasUseCase()
        }
    }
}
