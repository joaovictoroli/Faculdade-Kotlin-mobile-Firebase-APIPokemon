package com.example.assessmentkotlin.ui.login

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
import com.example.assessmentkotlin.model.AppUtil
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)



        loginViewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_listPokemonFragment)
            }
        }

        loginViewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank())
                Toast
                    .makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_LONG
                    ).show()
        }


        return inflater.inflate(R.layout.login_fragment, container, false)
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

        floatLogar.setOnClickListener{
            val email: String = editTxtLoginEmail.text.toString()
            val senha: String = editTxtLoginSenha.text.toString()
            if ( email != "" && senha != "") {
                loginViewModel.authUsuario(email, senha)
            } else
                toast(requireContext(), "Insira todos os campos")
        }

        btnGotoCadastro.setOnClickListener {
            findNavController().navigate(R.id.cadastroFragment)
        }

    }

}