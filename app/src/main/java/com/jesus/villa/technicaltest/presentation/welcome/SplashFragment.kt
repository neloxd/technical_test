package com.jesus.villa.technicaltest.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.domain.entity.Session
import com.jesus.villa.technicaltest.presentation.BaseFragment
import com.jesus.villa.technicaltest.util.safeNavigate
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by Jesús Villa on 22/01/23
 */
class SplashFragment : BaseFragment(), CoroutineScope {

    companion object {

        /**
         * The number of milliseconds to wait to move to the next screen.
         */
        private const val SPLASH_DELAY_MILLIS = 2500L

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private var cancelDelay = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState, HIDE_TOOLBAR)
        // Hide the status bar.
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity?.actionBar?.hide()

        launch {
            delay(SPLASH_DELAY_MILLIS)
            withContext(Dispatchers.Main) {
                if (!cancelDelay) nextPage()
            }
        }
    }

    private fun nextPage() {
        if (!Session.isLoggedIn())
            findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToEmailFragment())
        //  findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToDashboardFragment())
        //else {
        //}
    }
}