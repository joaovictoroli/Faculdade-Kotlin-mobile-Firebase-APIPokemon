package com.example.assessmentkotlin.api.service

import com.example.assessmentkotlin.model.PokemonLista.Pokemons
import com.example.assessmentkotlin.model.selectedPokemon.SelectedPokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonsService {


    @GET("api/v2/pokemon?limit=151&offset=0.json")
    suspend fun get1thGen(): Pokemons

    @GET("api/v2/pokemon?limit=100&offset=151.json")
    suspend fun get2thGen(): Pokemons

    @GET("api/v2/pokemon?limit=135&offset=251.json")
    suspend fun get3thGen(): Pokemons

    @GET("api/v2/pokemon/{nome}")
    suspend fun getClickedPokemon(
        @Path("nome") nome : String,
    ) : SelectedPokemon


}