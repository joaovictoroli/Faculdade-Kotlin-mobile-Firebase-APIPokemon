package com.example.assessmentkotlin.model.selectedPokemon

import com.google.gson.annotations.SerializedName

class Versions(
        @SerializedName("generation-i")
    val generationi: GenerationI? = null,

        @SerializedName("generation-ii")
    val generationii: GenerationIi? = null,

        @SerializedName("generation-iii")
    val generationiii: GenerationIii? = null,

        @SerializedName("generation-iv")
    val generationiv: GenerationIv? = null,

        @SerializedName("generation-v")
    val generationv: GenerationV? = null,

        @SerializedName("generation-vi")
    val generationvi: GenerationVi? = null,

        @SerializedName("generation-vii")
    val generationvii: GenerationVii? = null,

        @SerializedName("generation-viii")
    val generationviii: GenerationViii? = null
)