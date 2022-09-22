package com.example.assessmentkotlin.ui.pokemon.showpokemon

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessmentkotlin.api.ApiClient
import com.example.assessmentkotlin.database.PokemonDao
import com.example.assessmentkotlin.database.TreinadorDao
import com.example.assessmentkotlin.model.Pokemon
import com.example.assessmentkotlin.model.Treinador
import com.example.assessmentkotlin.model.selectedPokemon.SelectedPokemon
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL

class ShowPokemonViewModel (
    private val nome: String,
    private val numberPokedex: String
) : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = MutableLiveData<FirebaseUser>()

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _pokemonSelecionado = MutableLiveData<SelectedPokemon>()
    val pokemonSelecionado: LiveData<SelectedPokemon> = _pokemonSelecionado
    private lateinit var nonProtectedSelectedPokemon : SelectedPokemon

    val treinador = MutableLiveData<Treinador>()

    init {
        PokemonDao.isAlreadySelected(nome)
            .addOnCompleteListener {
                _status.value = it.result!!.exists()
            }

        firebaseUser.value = firebaseAuth.currentUser
        TreinadorDao.selecionarTreinadorPorEmail(firebaseUser.value!!.email!!)
                .addOnSuccessListener {
                    val trainer = it.toObject(Treinador::class.java)
                    treinador.value = trainer!!
                }


        viewModelScope.launch {
            try {
                val response = ApiClient.getPokemonApi().getClickedPokemon(nome)
                nonProtectedSelectedPokemon = response
                _pokemonSelecionado.value = response
                Log.i("SPVMResponse", "${response.types}")
            } catch (e: Exception) {
                Log.i("SPVMResponse", "${e.message}")
            }
        }

    }

    fun catchPokemon() : Task<Void> {
        val urlImage = nonProtectedSelectedPokemon.sprites.other.officialartwork.front_default
        val nome = nonProtectedSelectedPokemon.name
        val type = nonProtectedSelectedPokemon.types[0].type.name
        return PokemonDao.addPokemon(Pokemon(numberPokedex.toInt(), type, urlImage, false, nome))
    }

}