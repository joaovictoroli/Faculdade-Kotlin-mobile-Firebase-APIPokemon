package com.example.assessmentkotlin.ui.profile

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.adapter.RecyclerListPokemon
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.model.Pokemon
import com.example.assessmentkotlin.model.Treinador
import com.example.assessmentkotlin.model.selectedPokemon.SelectedPokemon
import kotlinx.android.synthetic.main.cadastro_fragment.*
import kotlinx.android.synthetic.main.cadastro_fragment.txtInfoGenero
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import kotlinx.android.synthetic.main.show_pokemon_fragment.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var profileViewModel: ProfileViewModel

    val imageListPokemonFav = mutableListOf<ImageView>()

    private var apelidoTrainer: String = "erro"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.treinador.observe(viewLifecycleOwner){
            txtProfileInfoApelido.text = it.apelido
            txtProfileInfoEmail.text = it.email
            apelidoTrainer = it.apelido!!

            if (it.genero == "Masculino") {
                updateTrainerMage()
            } else if (it.genero == "Feminino") {
                updateTrainerFmage()
            }
        }

        imageListPokemonFav.add(0, view.imgViewPokemonFav1)
        imageListPokemonFav.add(1, view.imgViewPokemonFav2)
        imageListPokemonFav.add(2, view.imgViewPokemonFav3)
        imageListPokemonFav.add(3, view.imgViewPokemonFav4)
        imageListPokemonFav.add(4, view.imgViewPokemonFav5)
        imageListPokemonFav.add(5, view.imgViewPokemonFav6)

        profileViewModel.pokemons.observe(viewLifecycleOwner, Observer{ pokemons->
            setupListPokemon(pokemons)
//            profileViewModel.calculateFav(txtInfoFav)
            val pokemonRecyclerAdapter = RecyclerListPokemon(pokemons, this::actionClick)
                val itemTouchHelper = ItemTouchHelper(
                    object : ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.RIGHT
                    ) {
                        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val pokemonViewHolder = viewHolder as RecyclerListPokemon.PokemonViewHolder
                            //
                            Log.i("recycle", pokemonViewHolder.textoNome.text.toString().toLowerCase())
                            Log.i("recycle", "${pokemons.indices}")
                            //
                                for (i in pokemons.indices) {
                                    if (pokemons[i].numberPokedex == pokemonViewHolder.textoDex.text.toString().toInt()) {
                                        Log.i("deu certo?", "${pokemons[i].nome!!}, $i")
                                        var pokemon = pokemons[i]
                                        profileViewModel.delete(pokemon)
                                        setLog(pokemon.nome!!)
                                        pokemons.removeAt(i)
                                        pokemonRecyclerAdapter.notifyItemRemoved(i)
                                        break
                                    }
                                }
                        }
                    } 
                )
                itemTouchHelper.attachToRecyclerView(recyclerViewPokemon)
                recyclerViewPokemon.adapter = pokemonRecyclerAdapter
                recyclerViewPokemon.layoutManager = LinearLayoutManager(requireContext())

            })

        profileViewModel.pokemonsFav.observe(viewLifecycleOwner) {
            profileViewModel.calculateFav(txtInfoFav)
            profileViewModel.atualizarImgFav(requireContext(), imageListPokemonFav)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgViewGetFav.setOnClickListener {
            profileViewModel.sortByFav()
        }

        imgBtnAsc.setOnClickListener {
            profileViewModel.sortByNumberPokedex()
        }
        imgBtnDesc.setOnClickListener {
            profileViewModel.sortByNumberPokedexDesc()
        }

        imgBtnSetupList.setOnClickListener {
            profileViewModel.all()
        }
    }

    fun actionClick(pokemon: Pokemon) {
        profileViewModel.selectPokemon(pokemon)
        val bundle = bundleOf(
            "nome" to pokemon.nome,
            "numberPokedex" to pokemon.numberPokedex.toString()
        )
        findNavController().navigate(R.id.showPokemonFragment, bundle)
    }


    private fun setupListPokemon(pokemons: MutableList<Pokemon>) {
        recyclerViewPokemon.adapter = RecyclerListPokemon(pokemons) {
        }
        recyclerViewPokemon.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateTrainerMage() {
        Glide.with(this)
            .load(R.drawable.treinadorm)
            .placeholder(R.drawable.progress)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imgViewTrainerProfile)
    }
    private fun updateTrainerFmage() {
        Glide.with(this)
            .load(R.drawable.treinadorf)
            .placeholder(R.drawable.progress)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imgViewTrainerProfile)
    }

    private fun setLog(nomePokemon: String) {
        val textoAntigo = getLog()
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val arquivo = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "LOG.txt")
            val saida = FileOutputStream(arquivo)
            val texto = "${textoAntigo}${apelidoTrainer} excluiu $nomePokemon \n"
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