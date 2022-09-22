package com.example.assessmentkotlin.ui.pokemon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.model.AppUtil
import kotlinx.android.synthetic.main.list_pokemon_fragment.*

class ListPokemonFragment : Fragment() {

    companion object {
        fun newInstance() = ListPokemonFragment()
    }

    private lateinit var listPokemonViewModel: ListPokemonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_pokemon_fragment, container, false)

        listPokemonViewModel = ViewModelProvider(this).get(ListPokemonViewModel::class.java)

        listPokemonViewModel.resultados.observe(viewLifecycleOwner) {
            listPokemon
                .adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                it
            )
            listPokemon.setOnItemClickListener { parent, view, position, name ->
                val bundle = bundleOf(
                        "nome" to it[position].name,
                        "numberPokedex" to it[position].url!!.replace("https://pokeapi.co/api/v2/pokemon","").replace("/", "")
                )
                Log.i("LISTPOKEMON", "${it[position].name}")
                findNavController().navigate(R.id.action_listPokemonFragment_to_showPokemonFragment, bundle)
            }

        }





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        listPokemonViewModel.setupListByApi1Gth()
        when (AppUtil.numberGen) {
            1 -> listPokemonViewModel.setupListByApi1Gth()
            2 -> listPokemonViewModel.setupListByApi2Gen()
            3 -> listPokemonViewModel.setupListByApi3Gen()
        }

        imgViewPrimeiraGeração.setOnClickListener {
            AppUtil.numberGen = 1
            listPokemonViewModel.setupListByApi1Gth()
        }

        imgViewSegundaGeracao.setOnClickListener {
            AppUtil.numberGen = 2
            listPokemonViewModel.setupListByApi2Gen()
        }

        imgViewTerceiraGeracao.setOnClickListener {
            AppUtil.numberGen = 3
            listPokemonViewModel.setupListByApi3Gen()
        }

        imgViewProfile.setOnClickListener{
            findNavController().navigate(R.id.profileFragment)
        }

        imgBtnLogout.setOnClickListener {
            listPokemonViewModel.logOut()
            findNavController().navigate(R.id.loginFragment)
        }
    }

}