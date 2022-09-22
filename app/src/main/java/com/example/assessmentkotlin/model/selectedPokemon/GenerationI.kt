package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class GenerationI(
        @SerializedName("red-blue")
    val redblue: RedBlue? = null,
    val yellow: Yellow
)