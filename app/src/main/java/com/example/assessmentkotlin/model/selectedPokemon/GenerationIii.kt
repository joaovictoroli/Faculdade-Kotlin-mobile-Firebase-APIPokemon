package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class GenerationIii(
    val emerald: Emerald,
    @SerializedName("firered-leafgreen")
    val fireredleafgreen: FireredLeafgreen,
    @SerializedName("ruby-sapphire")
    val rubysapphire: RubySapphire
)