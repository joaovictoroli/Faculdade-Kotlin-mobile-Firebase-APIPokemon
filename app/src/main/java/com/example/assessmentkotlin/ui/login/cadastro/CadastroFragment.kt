package com.example.assessmentkotlin.ui.login.cadastro

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assessmentkotlin.R
import com.example.assessmentkotlin.model.Treinador
import kotlinx.android.synthetic.main.cadastro_fragment.*

class CadastroFragment : Fragment() {

    companion object {
        fun newInstance() = CadastroFragment()
    }

    private lateinit var cadastroViewModel: CadastroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cadastroViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        cadastroViewModel.status.observe(viewLifecycleOwner) {
            if (it)
                findNavController().popBackStack()
        }

        cadastroViewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                toast(requireContext(), it)
            }
        }



        return inflater.inflate(R.layout.cadastro_fragment, container, false)
    }

    fun toast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioButtonM.setOnClickListener {
            imgViewCadastro.setImageDrawable(resources.getDrawable(R.drawable.treinadorm))
            txtInfoGenero.text = "Masculino"
        }

        radiobuttonF.setOnClickListener {
            imgViewCadastro.setImageDrawable(resources.getDrawable(R.drawable.treinadorf))
            txtInfoGenero.text = "Feminino"
        }

        editTxtApelidoCadastro.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                txtInfoApelidoCadastro.text = editTxtApelidoCadastro.text.toString()
            }
        }

        editTxtEmailCadastro.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                txtInfoEmailCadastro.text = editTxtEmailCadastro.text.toString()
            }
        }

        floatBtnGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        btnConfirmarCadastro.setOnClickListener {
            val apelido = editTxtApelidoCadastro.text.toString()
            val email = editTxtEmailCadastro.text.toString()
            val senha =editTxtSenhaCadastro.text.toString()
            val resenha = editTxtResenhaCadastro.text.toString()
            if (apelido != "" && email != "" && senha != "" && senha == resenha) {
                cadastroViewModel.addUserAuth(Treinador(apelido, txtInfoGenero.text.toString(), email), senha)
            } else toast(requireContext(), "Campos em branco ou senhas não conferem")
        }
    }

    }

