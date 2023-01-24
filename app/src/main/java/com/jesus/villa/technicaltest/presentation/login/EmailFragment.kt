package com.jesus.villa.technicaltest.presentation.login

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation


/**
 * Created by Jesús Villa on 22/01/23
 */
class EmailFragment :
    BaseDataBindingFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome),
    IAnalyticImplementation {
    private var backgroundDrawable: Int = when ((1..3).random()) {
        1 -> R.drawable.bg_01
        2 -> R.drawable.bg_02
        else -> R.drawable.bg_03
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int) {
        super.onViewCreated(view, savedInstanceState, HIDE_TOOLBAR)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenView(getString(R.string.login_screen_view_welcome))

        binding.bgView.setImageResource(backgroundDrawable)

        if (BuildConfig.DEBUG) {
            try {
                val pInfo = requireContext().packageManager.getPackageInfo(
                    requireContext().packageName, 0
                )
                val version = pInfo.versionName
                val verCode = pInfo.versionCode
                val ent = Core.instance.environment.name.toUpperCase()
                binding.loginVersionTextView.text = "v $version-$verCode @$ent"
                binding.loginVersionTextView.visibility = View.VISIBLE
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }

        binding.loginCreateAccountCustomButton.setOnClickListener {
            userInteraction(
                getString(R.string.ingresar_a_la_app),
                getString(R.string.crear_cuenta),
                getString(R.string.ingreso_fpay),
                getString(R.string.crear_cuenta)
            )
            val bundle = bundleOf(FROM_WELCOME_KEY to true)
            Navigation.findNavController(it).navigate(R.id.action_welcome_to_onboarding, bundle)
        }

        binding.loginHaveAccountCustomButton.setOnClickListener {
            userInteraction(
                getString(R.string.ingresar_a_la_app),
                getString(R.string.ya_tengo_cuenta),
                getString(R.string.ingreso_fpay),
                getString(R.string.login)
            )
            val bundle = bundleOf(FROM_WELCOME_KEY to true)

            Session.userData?.let { userData ->
                bundle.putBoolean(VALIDATE_DEVICE_KEY, Session.isDeviceValidated())
                bundle.putString(CUSTOMER_TYPE_KEY, Session.userData!!.customerType)
                setUser(userData.customerId.id)
                Navigation.findNavController(it).navigate(R.id.action_welcome_to_login, bundle)
            }?: Navigation.findNavController(it).navigate(R.id.action_welcome_to_onboarding, bundle)
        }
        hideLoading()
    }
}