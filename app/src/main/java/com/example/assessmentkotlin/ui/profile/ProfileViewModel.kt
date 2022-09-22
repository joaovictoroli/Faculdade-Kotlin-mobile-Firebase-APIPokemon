package com.example.assessmentkotlin.ui.profile

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.database.PokemonDao
import com.example.assessmentkotlin.database.TreinadorDao
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.model.Pokemon
import com.example.assessmentkotlin.model.Treinador
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = MutableLiveData<FirebaseUser>()
    val treinador = MutableLiveData<Treinador>()

    private val _pokemons = MutableLiveData<MutableList<Pokemon>>()
    val pokemons: LiveData<MutableList<Pokemon>> = _pokemons

    private val _pokemonsFav = MutableLiveData<List<Pokemon>>()
    val pokemonsFav: LiveData<List<Pokemon>> = _pokemonsFav

    private val _pokemonSelect = MutableLiveData<Pokemon>()
    val pokemonSelect: LiveData<Pokemon> = _pokemonSelect

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon : LiveData<Pokemon> = _pokemon

    init {
        firebaseUser.value = firebaseAuth.currentUser
            TreinadorDao.selecionarTreinadorPorEmail(firebaseUser.value!!.email!!)
                .addOnSuccessListener {
                    val trainer = it.toObject(Treinador::class.java)
                    treinador.value = trainer!!
                }

        PokemonDao.allPokemon()
            .addSnapshotListener { snapshot, error ->
                if (error != null)
                else
                    if (snapshot != null && !snapshot.isEmpty)
                        _pokemons.value = snapshot.toObjects(Pokemon::class.java)
            }

        getFavs()
    }

    fun sortByFav() {
        PokemonDao.getFav()
            .addOnSuccessListener { it->
                _pokemons.value = it.toObjects(Pokemon::class.java)
            }
    }


    fun sortByNumberPokedex() {
        PokemonDao.sortByPokedexNumber()
            .addSnapshotListener{ snapshot, error ->
                if (error != null)
                else
                    if (snapshot != null && !snapshot.isEmpty)
                        _pokemons.value = snapshot.toObjects(Pokemon::class.java)
            }
    }

    fun sortByNumberPokedexDesc() {
        PokemonDao.sortByPokedexNumberDesc()
            .addSnapshotListener{ snapshot, error ->
                if (error != null)
                    Log.i("Profile", "error na lista")
                else
                    if (snapshot != null && !snapshot.isEmpty)
                        _pokemons.value = snapshot.toObjects(Pokemon::class.java)
            }
    }

    fun calculateFav(textView: TextView) {
        PokemonDao.getFav()
            .addOnSuccessListener {
                _pokemonsFav.value = it.toObjects(Pokemon::class.java)
            }
            .addOnCompleteListener {
                if (pokemonsFav.value!!.size >= 6) {
                    AppUtil.canFav = false
                    textView.text = "Time Completo"
                } else {
                    AppUtil.canFav = true
                    textView.text = "Time Incompleto"
                }
            }
    }

    fun delete(pokemon: Pokemon) {
        PokemonDao.delPokemon(pokemon)
//        all()
        getFavs()
    }

    fun getFavs() {
        PokemonDao.getFav()
            .addOnSuccessListener {
                _pokemonsFav.value = it.toObjects(Pokemon::class.java)
            }
    }

    fun atualizarImgFav(context: Context, imageView: List<ImageView>) {
        if (pokemonsFav.value!!.size < 6) {
            for (f in pokemonsFav.value!!.size until 6)
                Glide.with(context)
                    .load(R.drawable.progress)
                    .into(imageView[f])
        }

        for (i in pokemonsFav.value!!.indices) {
            Glide.with(context)
                .load(pokemonsFav.value!![i].urlImage)
                .placeholder(R.drawable.progress)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView[i])
        }
    }

    fun selectPokemon(pokemon: Pokemon) {
        _pokemonSelect.value = pokemon
    }

    fun all() {
        PokemonDao.allPokemon()
            .addSnapshotListener { snapshot, error ->
                if (error != null)
                    Log.i("Profile", "erro na lista")
                else
                    if (snapshot != null && !snapshot.isEmpty)
                        _pokemons.value = snapshot.toObjects(Pokemon::class.java)
            }
    }

    fun getPokemon(nome: String): Pokemon {
        var pokemon: Pokemon
        PokemonDao.getPokemon(nome)
            .addOnSuccessListener {
                _pokemon.value = it.toObject(Pokemon::class.java)
                pokemon = _pokemon.value!!
            }
        return _pokemon.value!!
    }
}