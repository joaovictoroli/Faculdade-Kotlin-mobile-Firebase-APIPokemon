package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class Other(
    val dream_world: DreamWorld,
    @SerializedName("official-artwork")
    val officialartwork: OfficialArtwork
)