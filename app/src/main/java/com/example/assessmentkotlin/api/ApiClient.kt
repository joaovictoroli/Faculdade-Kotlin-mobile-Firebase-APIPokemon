package com.example.assessmentkotlin.api

import com.example.assessmentkotlin.api.service.PokemonsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance: Retrofit? = null
    private fun getRetrofit(): Retrofit {
        if (instance == null)
            instance = Retrofit
                .Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return instance as Retrofit
    }

    fun getPokemonApi()
            = getRetrofit().create(PokemonsService::class.java)
}