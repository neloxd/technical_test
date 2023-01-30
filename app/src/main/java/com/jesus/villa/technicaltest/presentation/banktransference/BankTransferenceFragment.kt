package com.jesus.villa.technicaltest.presentation.banktransference

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.navGraphViewModels
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.FragmentBankTransferenceBinding
import com.jesus.villa.technicaltest.dependency.OperationModule
import com.jesus.villa.technicaltest.domain.entity.Session
import com.jesus.villa.technicaltest.presentation.BaseDataBindingFragment
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import com.jesus.villa.technicaltest.presentation.dashboard.DashboardFragment
import com.jesus.villa.technicaltest.util.copyText


/**
 * Created by Jesús Villa on 30/01/23
 */
class BankTransferenceFragment: BaseDataBindingFragment<FragmentBankTransferenceBinding>(R.layout.fragment_bank_transference) {
    private val TAG = BankTransferenceFragment::class.java.simpleName
    private val operationViewModelFactory = OperationModule.provideOperationViewModelFactory()
    private val operationViewModel: OperationViewModel by navGraphViewModels(R.id.main_nav_graph) { operationViewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int) {
        super.onViewCreated(view, savedInstanceState, HIDE_TOOLBAR)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.i(TAG,"onBackPressedDispatcher")
            requireActivity().finishAffinity()
        }

        hideLoading()

        showNavigation(true)
        init()
    }

    private fun init() {
        with(binding) {
            copyView.setOnClickListener { requireContext().copyText(binding.accountNumberText.text.toString()) }
            copyAliasView.setOnClickListener { requireContext().copyText(binding.aliasText.text.toString()) }
        }

        retrieve()
    }

    private fun retrieve() {
        Session.userData?.let {
            Log.i(TAG,"it$it")
            binding.aliasText.text = it.fullName + ".glt"
        }
    }
}