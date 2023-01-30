package com.jesus.villa.technicaltest.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.FragmentPinPassBinding
import com.jesus.villa.technicaltest.dependency.OperationModule
import com.jesus.villa.technicaltest.domain.usecases.PersistenceUseCase
import com.jesus.villa.technicaltest.presentation.BaseDataBindingFragment
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import com.jesus.villa.technicaltest.presentation.welcome.SplashFragment
import com.jesus.villa.technicaltest.util.safeNavigate
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by Jesús Villa on 29/01/23
 */
class PassFragment:
    BaseDataBindingFragment<FragmentPinPassBinding>(R.layout.fragment_pin_pass) , CoroutineScope {

    private val TAG = PassFragment::class.java.simpleName
    private val operationViewModelFactory = OperationModule.provideOperationViewModelFactory()
    private val operationViewModel: OperationViewModel by navGraphViewModels(R.id.main_nav_graph) { operationViewModelFactory }
    private val persistenceUseCase = PersistenceUseCase()

    private val pinList = listOf<Int>()
    private var textPinPass: String? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int) {
        super.onViewCreated(view, savedInstanceState, HIDE_TOOLBAR)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.i(TAG,"onBackPressedDispatcher")
            findNavController().navigateUp()
        }

        hideLoading()

        showNavigation(false)
        init()
    }

    private fun init() {
        with(binding) {
            reload(false)
            editText.requestFocus()

            pinContainer.setOnClickListener {
                showKeyboard(editText)
            }

//            editText.setOnKeyListener { v, keyCode, event -> //You can identify which key pressed by checking keyCode value with KeyEvent.KEYCODE_
//               Log.i(TAG,"setOnKeyListener - > keyCode:$keyCode - event:$event ")
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//                    //this is for backspace
//                }
//
//                Log.i(TAG,"setOnKeyListener - > event.action:${event.action} && event.keyCode:${event.keyCode}")
//                if(event.action == KeyEvent.ACTION_DOWN
//                    && event.keyCode == KeyEvent.KEYCODE_DEL) {
//
//                }
//                false
//            }

            //editText.filters = arrayOf<InputFilter>(InputFilterMinMax("1", "4"))
        }

        showKeyboard(binding.editText)
        listener()
    }

    private fun listener() {
        Log.i(TAG,"listener")
        binding.editText.addTextChangedListener(object : TextWatcher {
            var enteredText: String? = null
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged -> s:${s.toString()}");
                validateKeyboard(s)
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                enteredText = s.toString();
                Log.i(TAG,"beforeTextChanged:$enteredText")

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()
                Log.i(TAG,"onTextChanged -> enteredText:$enteredText - newText:$newText")
                if(newText.length > enteredText?.length!!) {
                    //Now get last char of enteredText here .. which is deleted currently

                }
            }
        })

        binding.editText.setOnEditorActionListener{ v, keyCode, event -> //You can identify which key pressed by checking keyCode value with KeyEvent.KEYCODE_
            Log.i(TAG,"setOnKeyListener - > event.action:${event}")
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                }

                /*if(event?.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_DEL) {

                }*/
                false
            }

        binding.sendButton.setOnClickListener {
            Toast.makeText(requireActivity(),"Pin:$textPinPass",Toast.LENGTH_LONG).show()
            showLoading()
            hideKeyboard(binding.root)
            launch {
                delay(2000L)
                withContext(Dispatchers.Main) {
                    nextPage()
                    hideLoading()
                }
            }
        }
    }

    private fun nextPage() {
        Log.i(TAG,"nextPage")
        operationViewModel.saveData()
        findNavController().safeNavigate(PassFragmentDirections.actionPinPassFragmentToDashboardFragment())
    }

    private fun validateKeyboard(s: Editable?) {
        Log.i(TAG,"validateKeyboard:${s.toString()}")
        if(s.isNullOrEmpty()) {
            reload(false)
        } else {
            binding.sendButton.isEnabled = false
            val text = s.toString()
            val size = text.length

            Log.i(TAG,"validateKeyboard -> text:$text - size:$size")
            if(size > 4) {
                binding.sendButton.isEnabled = true
                val textFinal = text.substring(0, text.length- 1)
                Log.i(TAG,"textFinal:$textFinal")
                binding.editText.setText(textPinPass)
                binding.editText.setSelection(textPinPass!!.length)
            } else {
                if (size == 4) {
                    textPinPass = text
                }
                pinEnable(size, true)
            }
        }
    }

    private fun reload(value: Boolean){
        binding.sendButton.isEnabled = value
        val resource = if(value) R.drawable.ic_pin_enable else R.drawable.ic_pin_disable
        binding.pinOne.setImageResource(resource)
        binding.pinTwo.setImageResource(resource)
        binding.pinThree.setImageResource(resource)
        binding.pinFour.setImageResource(resource)
    }

    private fun pinEnable(position: Int, value:Boolean) {
        val resourceEnable = if(value) R.drawable.ic_pin_enable else R.drawable.ic_pin_disable
        val resourceDisable = if(!value) R.drawable.ic_pin_enable else R.drawable.ic_pin_disable
        when(position) {
            1 -> {
                binding.pinOne.setImageResource(resourceEnable)

                binding.pinTwo.setImageResource(resourceDisable)
                binding.pinThree.setImageResource(resourceDisable)
                binding.pinFour.setImageResource(resourceDisable)
            }
            2 -> {
                binding.pinOne.setImageResource(resourceEnable)
                binding.pinTwo.setImageResource(resourceEnable)

                binding.pinThree.setImageResource(resourceDisable)
                binding.pinFour.setImageResource(resourceDisable)
            }
            3 -> {
                binding.pinOne.setImageResource(resourceEnable)
                binding.pinTwo.setImageResource(resourceEnable)
                binding.pinThree.setImageResource(resourceEnable)

                binding.pinFour.setImageResource(resourceDisable)
            }
            4 -> {
                reload(true)
            }
        }
    }

}