package com.jesus.villa.technicaltest.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.FragmentDashboardBinding
import com.jesus.villa.technicaltest.dependency.OperationModule
import com.jesus.villa.technicaltest.domain.GeneralConfiguration
import com.jesus.villa.technicaltest.domain.entity.Session
import com.jesus.villa.technicaltest.presentation.BaseDataBindingFragment
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import com.jesus.villa.technicaltest.presentation.dashboard.adapter.TransactionAdapter
import com.jesus.villa.technicaltest.presentation.dashboard.model.ActivityTransaction


/**
 * Created by Jesús Villa on 30/01/23
 */
class DashboardFragment: BaseDataBindingFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    private val TAG = DashboardFragment::class.java.simpleName
    private val operationViewModelFactory = OperationModule.provideOperationViewModelFactory()
    private val operationViewModel: OperationViewModel by navGraphViewModels(R.id.main_nav_graph) { operationViewModelFactory }

    private val listOfContact by lazy {
        listOf(
            ActivityTransaction(
            id = 1, income = false, "Rappi Pago", "Pago de servicio",
            amount = GeneralConfiguration.defaultCurrency!!.symbol + "157,50", "28.03.2020"),
            ActivityTransaction(
                id = 2, income = true, "Carlos Mejia", "Carga de dinero",
                amount = GeneralConfiguration.defaultCurrency!!.symbol + "2400,00", "30.03.2020"),
            ActivityTransaction(
                id = 3, income = false, "Impuesto país", "Pago de servicio",
                amount = GeneralConfiguration.defaultCurrency!!.symbol + "600", "01.04.2020"))
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
            transferList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            transferList.adapter = TransactionAdapter(requireContext(), listOfContact, object : TransactionAdapter.OnClickListener {
                override fun onClick(vista: View, index: Int) {

                }
            })
        }

        retrieve()
    }

    private fun retrieve() {
        Session.userData?.let {
            Log.i(TAG,"it$it")
            binding.fullNameText.text = requireContext().getString(R.string.welcome_user_text, it.fullName)
        }

        val amount = GeneralConfiguration.defaultCurrency!!.symbol + "40212.842"
        binding.amountText.text = amount
    }
}