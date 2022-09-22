package com.example.assessmentkotlin.ui.pokemon.showpokemon

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.model.selectedPokemon.SelectedPokemon
import kotlinx.android.synthetic.main.show_pokemon_fragment.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ShowPokemonFragment : Fragment() {

    companion object {
        fun newInstance() = ShowPokemonFragment()
    }

    private lateinit var showPokemonViewModel: ShowPokemonViewModel
    private lateinit var pokemonSelecionado: SelectedPokemon
    private var apelidoTrainer: String = "erro"
    var state = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.show_pokemon_fragment, container, false)

        val nome = arguments?.getString("nome")
        if (nome == null) {
            findNavController().popBackStack()
        }
        val numberPokedex = arguments?.getString("numberPokedex")
        if (numberPokedex == null) {
            findNavController().popBackStack()
        }

        val showPokemonViewModelFactory = ShowPokemonViewModelFactory(nome!!, numberPokedex!!)

        showPokemonViewModel = ViewModelProvider(this, showPokemonViewModelFactory)
            .get(ShowPokemonViewModel::class.java)

        showPokemonViewModel.pokemonSelecionado.observe(viewLifecycleOwner) {
            if (it != null) {
                updateUI(it)
            }
        }

        showPokemonViewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                updateImageCatched()
            } else if (!it) {
                updateImageToCatch()
            }
        }

        showPokemonViewModel.treinador.observe(viewLifecycleOwner) {
            if (it != null){
                apelidoTrainer = it.apelido!!
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nome = arguments?.getString("nome")
        imgViewCatch.setOnClickListener{
            if (state) {
            showPokemonViewModel.catchPokemon().addOnSuccessListener {
                updateImageCatched()
                val nome = arguments?.getString("nome")
                setLog(nome!!)
                toast(
                    requireContext(),
                    "Você capturou o ${nome!!.capitalize()}"
                )
                }
            }
            else if (!state) toast(requireContext(), "Você ja capturou ${nome!!.capitalize()}")


        }


    }

    private fun updateUI(pokemon: SelectedPokemon) {
        updateImage(pokemon)
        txtNomePokemon.text = pokemon.name.capitalize()
        //
        var showPokemonHeightLength = pokemon.height.toString().length
        var showPokemonHeight = "error"
        when (showPokemonHeightLength) {
            1 -> showPokemonHeight = "0.${pokemon.height}"
            else -> showPokemonHeight= pokemon.height.toString().substring(0, pokemon.height.toString().length - 1) + "." + pokemon.height.toString().substring(pokemon.height.toString().length - 1, pokemon.height.toString().length)
        }
        //
        var showPokemonWeightLength = pokemon.weight.toString().length
        var showPokemonWeight = "error"
        when (showPokemonWeightLength) {
            1 -> showPokemonWeight = "0.${pokemon.weight}"
            else -> showPokemonWeight = pokemon.weight.toString().substring(0, pokemon.weight.toString().length - 1) + "." + pokemon.weight.toString().substring(pokemon.weight.toString().length-1, pokemon.weight.toString().length)
        }
        
        txtAltura.text = "Altura: ${showPokemonHeight} m"
        txtPeso.text = "Peso: ${showPokemonWeight} kg"
        txtArtWork.text = pokemon.sprites.other.officialartwork.front_default

        txtTypeSlot1.text = pokemon.types[0].type.name!!.capitalize()
        txtTypeSlot2.text = pokemon.types[1].type.name!!.capitalize()

    }

    private fun updateImage(pokemon: SelectedPokemon) {
        Glide.with(this)
            .load("${pokemon.sprites.other.officialartwork.front_default}")
            .placeholder(R.drawable.progress)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imgViewArtWork)

    }
    private fun updateImageToCatch() {
        Glide.with(this)
            .load(R.drawable.pokeballgif)
            .into(imgViewCatch)
    }

    private fun updateImageCatched() {
        Glide.with(this)
            .load(R.drawable.pokeballcatched)
            .override(40, 40)
            .into(imgViewCatch)

        state = false
    }

    private fun toast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT).show()
    }

    private fun setLog(nomePokemon: String) {
        val textoAntigo = getLog()
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val arquivo = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "LOG.txt")
            val saida = FileOutputStream(arquivo)
            val texto = "${textoAntigo}${apelidoTrainer} adicionou $nomePokemon \n"
            saida.write(texto.toByteArray())
            saida.close()
        }
    }

    private fun getLog() : String{
        var texto = "error"
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val arquivo = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "LOG.txt")
            val entrada = FileInputStream(arquivo)
            texto = String(entrada.readBytes())
            entrada.close()
        }
        return texto
    }

}