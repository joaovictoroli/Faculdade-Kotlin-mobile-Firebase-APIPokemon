package com.example.assessmentkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.database.PokemonDao
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.model.Pokemon
import kotlinx.android.synthetic.main.recycler_list_pokemon.view.*

class RecyclerListPokemon (
    private val pokemons: MutableList<Pokemon>,
    private val actionClick: (Pokemon) -> Unit
        ): RecyclerView.Adapter<RecyclerListPokemon.PokemonViewHolder>() {

            class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
                val textoDex: TextView = itemView.txtPokemonDex
                val textoNome: TextView = itemView.txtPokemonNome
                val textoType: TextView = itemView.txtPokemonType
                val imageArtWork: ImageView = itemView.imgViewPokemonArtWork
                val imageFav: ImageView = itemView.imgViewRecyclerFav
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_list_pokemon,
                parent, false
            )
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.textoDex.text = pokemon.numberPokedex.toString()
        holder.textoNome.text = "${pokemon.nome!!.capitalize()}"
        holder.textoType.text = pokemon.type!!.capitalize()
        Glide.with(holder.itemView.context)
            .load(pokemon.urlImage)
            .placeholder(R.drawable.progress)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.imageArtWork)

        if (pokemon.favorito == true) {
            startOn(holder.itemView.context, holder.imageFav)
        }
        else if (pokemon.favorito== false)
            starOff(holder.itemView.context, holder.imageFav)

        holder.imageFav.setOnClickListener {
            getSelectedPokemonFav(pokemon)
        }

        holder.imageArtWork.setOnClickListener {
            actionClick(pokemon)
        }

    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun startOn(itemView: Context, image: ImageView) {
        Glide.with(itemView)
            .load(R.drawable.staron)
            .into(image)
    }

    fun starOff(itemView: Context, image: ImageView) {
        Glide.with(itemView)
            .load(R.drawable.staroff)
            .into(image)
    }

    fun getSelectedPokemonFav(pokemon: Pokemon) {
        if (pokemon.favorito == true) {
            pokemon.favorito = false
        } else if (pokemon.favorito == false && AppUtil.canFav == true){
            pokemon.favorito = true
        }
        PokemonDao.addPokemon(pokemon)
    }


}