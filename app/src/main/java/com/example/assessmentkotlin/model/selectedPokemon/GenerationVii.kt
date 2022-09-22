package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class GenerationVii(
    val icons: Icons,
    @SerializedName("ultra-sun-ultra-moon")
    val ultrasunultramoon: UltraSunUltraMoon
)