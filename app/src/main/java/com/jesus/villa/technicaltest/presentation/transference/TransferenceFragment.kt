package com.jesus.villa.technicaltest.presentation.transference

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.FragmentTransferenceBinding
import com.jesus.villa.technicaltest.dependency.OperationModule

import com.jesus.villa.technicaltest.presentation.BaseDataBindingFragment
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import com.jesus.villa.technicaltest.presentation.dashboard.DashboardFragment

import com.jesus.villa.technicaltest.presentation.transference.adapter.ContactAdapter
import com.jesus.villa.technicaltest.presentation.transference.model.ContactTransference


/**
 * Created by Jesús Villa on 30/01/23
 */
class TransferenceFragment: BaseDataBindingFragment<FragmentTransferenceBinding>(R.layout.fragment_transference) {
    private val TAG = TransferenceFragment::class.java.simpleName
    private val operationViewModelFactory = OperationModule.provideOperationViewModelFactory()
    private val operationViewModel: OperationViewModel by navGraphViewModels(R.id.main_nav_graph) { operationViewModelFactory }

    private val listOfContact by lazy {
        listOf(
            ContactTransference(
                id = 1, "Joaquín Vallejos", "15 3030-2020"),
            ContactTransference(
                id = 2, "Laura Gomez", "15 3030-2020"),
            ContactTransference(
                id = 3, "Maria Alejandra Rodriguez", "15 3030-2020"),
            ContactTransference(
                id = 4, "Pedro Cosentino", "15 3030-2020"),
            ContactTransference(
                id = 5, "Fernando Cristaldo", "15 3030-2020"),
            ContactTransference(
                id = 6, "Jorgelina Estevanez", "15 3030-2020"),
            ContactTransference(
                id = 7, "Juan Pablo Campanario", "15 3030-2020"),
            ContactTransference(
                id = 8, "Yael López", "15 3030-2020"),
            ContactTransference(
                id = 9, "Pamela Sifuentes", "15 3030-2020"),
            ContactTransference(
                id = 10, "Jesús Villa", "15 3030-2020"),
            ContactTransference(
                id = 11, "Miguel Hernandez", "15 3030-2020"),
        )
    }

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
            contactsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            contactsList.adapter = ContactAdapter(requireContext(), listOfContact, object : ContactAdapter.OnClickListener {
                override fun onClick(vista: View, index: Int) {

                }
            })
        }
    }
}