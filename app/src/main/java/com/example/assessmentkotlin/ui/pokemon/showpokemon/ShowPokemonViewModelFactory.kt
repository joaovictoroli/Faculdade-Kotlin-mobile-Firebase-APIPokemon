package com.example.assessmentkotlin.ui.pokemon.showpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowPokemonViewModelFactory(private val nome: String, private val numberPokedex: String)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowPokemonViewModel(nome, numberPokedex) as T
    }

}