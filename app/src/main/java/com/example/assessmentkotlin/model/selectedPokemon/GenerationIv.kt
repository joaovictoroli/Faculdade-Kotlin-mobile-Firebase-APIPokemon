package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class GenerationIv(
        @SerializedName("diamond-pearl")
    val diamondpearl: DiamondPearl,
        @SerializedName("heartgold-soulsilver")
    val heartgoldsoulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)