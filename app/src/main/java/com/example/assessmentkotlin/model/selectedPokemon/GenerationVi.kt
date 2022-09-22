package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class GenerationVi(
        @SerializedName("omegaruby-alphasapphire")
    val omegarubyalphasapphire: OmegarubyAlphasapphire,
        @SerializedName("x-y")
    val xy: XY
)