package com.jesus.villa.technicaltest.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.compose.material.Snackbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.FragmentEmailBinding
import com.jesus.villa.technicaltest.dependency.OperationModule
import com.jesus.villa.technicaltest.domain.entity.Session
import com.jesus.villa.technicaltest.domain.entity.UserData
import com.jesus.villa.technicaltest.domain.usecases.PersistenceUseCase
import com.jesus.villa.technicaltest.presentation.BaseDataBindingFragment
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import com.jesus.villa.technicaltest.util.ValidationLayer
import com.jesus.villa.technicaltest.util.safeNavigate


/**
 * Created by Jesús Villa on 22/01/23
 */
class EmailFragment :
    BaseDataBindingFragment<FragmentEmailBinding>(R.layout.fragment_email) {

    //val TAG = EmailFragment::class.java.simpleName

    companion object {
        val TAG = "EmailFragment"
    }
    private val operationViewModelFactory = OperationModule.provideOperationViewModelFactory()
    private val operationViewModel: OperationViewModel by navGraphViewModels(R.id.main_nav_graph) { operationViewModelFactory }
    private val persistenceUseCase = PersistenceUseCase()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int) {
        super.onViewCreated(view, savedInstanceState, HIDE_TOOLBAR)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.i(TAG,"onBackPressedDispatcher")
            requireActivity().finish()
        }

        hideLoading()

        showNavigation(false)

        init()
    }

    private fun init() {

        enableButton(false)

        with(binding) {
            textInputEmailLayout.editText?.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    enableButton(s?.length != 0)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i(TAG,"beforeTextChanged")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i(TAG,"onTextChanged")
                }
            })
        }

       retrieve()

        listener()
    }

    private fun enableButton(enable: Boolean) {
        binding.sendButton.isEnabled = enable
    }

    private fun listener() {
        binding.sendButton.setOnClickListener {
            saveSession()
        }
    }

    private fun saveSession() {
        val text = binding.textInputEmailLayout.editText?.text.toString()
        if(text.isNotEmpty()) {
            if(ValidationLayer.validateMail(text)) {
                val user = UserData(email = text, fullName = "Jesus Villa", customerId = "12345678")
                operationViewModel.saveUser(user)
                findNavController().safeNavigate(EmailFragmentDirections.actionWelcomeToPass())
            } else {
                Snackbar.make(binding.root, "Ingrese un email válido", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun retrieve() {
        operationViewModel.getUserData().value?.let {
            binding.textInputEmailText.setText(it.email)
        }
    }
}