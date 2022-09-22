package com.example.assessmentkotlin.model.PokemonLista

class Result(
    val name: String? = null,
    val url: String? = null
){
    override fun toString(): String = "${url!!.replace("https://pokeapi.co/api/v2/pokemon","").replace("/", "")}:  ${name!!.capitalize()} "
}
