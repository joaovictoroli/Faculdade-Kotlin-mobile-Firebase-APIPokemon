package com.example.assessmentkotlin.ui.pokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessmentkotlin.api.ApiClient
import com.example.assessmentkotlin.api.service.PokemonsService
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.model.PokemonLista.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.lang.Exception

class ListPokemonViewModel : ViewModel() {


    private val _resultados = MutableLiveData<List<Result>>()
    val resultados: LiveData<List<Result>> = _resultados

    private val firebaseAuth = FirebaseAuth.getInstance()

    val firebaseUser = MutableLiveData<FirebaseUser>()


    init {
        firebaseUser.value = firebaseAuth.currentUser

    }

    fun setupListByApi1Gth(){
        viewModelScope.launch {
            try {
                val projectService: PokemonsService = ApiClient.getPokemonApi()
                val api = projectService.get1thGen()
                _resultados.value = api.results
            } catch(e: Exception) {
                Log.i("errorAPI", "${e.message}")
            }
        }
}

    fun setupListByApi2Gen(){
        viewModelScope.launch {
            try {
                val projectService: PokemonsService = ApiClient.getPokemonApi()
                val api = projectService.get2thGen()
                _resultados.value = api.results
            } catch(e: Exception) {
                Log.i("errorAPI", "${e.message}")
            }
        }
    }

    fun setupListByApi3Gen(){
        viewModelScope.launch {
            try {
                val projectService: PokemonsService = ApiClient.getPokemonApi()
                val api = projectService.get3thGen()
                _resultados.value = api.results
            } catch(e: Exception) {
                Log.i("errorAPI", "${e.message}")
            }
        }
    }

    fun logOut() {
        firebaseAuth.signOut()
    }
}