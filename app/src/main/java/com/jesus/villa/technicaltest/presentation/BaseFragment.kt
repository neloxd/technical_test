package com.jesus.villa.technicaltest.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * Created by Jesús Villa on 22/01/23
 */
interface BaseActivity{
    fun showLoading()
    fun hideLoading()
}

open class BaseFragment : Fragment() {
    private var progressBar: View? = null
    private var navigation: View? = null
    private var handler = Handler()

    private var stateFragment: Int = SHOW_BACK_BUTTON

    companion object {
        /**
         * To use as parameter in the constructor in order to not display any content in the ToolBar.
         */
        const val SHOW_NOTHING = 0

        /**
         * To use as parameter in the constructor in order to show the home icon is displayed.
         */
        const val SHOW_HOME_BUTTON = 2

        /**
         * To use as parameter in the constructor in order to show the back icon is displayed.
         */
        const val SHOW_BACK_BUTTON = 4

        /**
         * To use as parameter in the constructor in order to show a cross is used instead of the back
         */
        const val SHOW_CANCEL_BUTTON = 8

        /**
         * To use as parameter in the constructor in order to completely hides the ToolBar.
         */
        const val HIDE_TOOLBAR = 16

        /**
         * To use as parameter in the constructor in order to show the title header
         */
        const val SHOW_TITLE_HEADER = 32


    }

    fun showMessage(str: String) {
        Toast.makeText(this.activity?.applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created. The fragment's view hierarchy
     * is not however attached to its parent at this point.
     *
     * @param view
     *            View: The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState
     *            Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @param stateFragment
     *            One or multiple parameters from : SHOW_HOME_BUTTON,
     *            SHOW_TITLE_HEADER, SHOW_CANCEL_BUTTON, etc
     */
    open fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int) {
        onViewCreated(view, savedInstanceState, stateFragment, null, null)
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created. The fragment's view hierarchy
     * is not however attached to its parent at this point.
     *
     * @param view
     *            View: The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState
     *            Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @param stateFragment
     *            One or multiple parameters from : SHOW_HOME_BUTTON,
     *            SHOW_TITLE_HEADER, SHOW_CANCEL_BUTTON, etc
     * @param title
     *            The title for the top bar (only visible when using
     *            SHOW_TITLE_HEADER)
     */
    open fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int, title: String) {
        onViewCreated(view, savedInstanceState, stateFragment, title, null)
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created. The fragment's view hierarchy
     * is not however attached to its parent at this point.
     *
     * @param view
     *            View: The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState
     *            Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @param stateFragment
     *            One or multiple parameters from : SHOW_HOME_BUTTON,
     *            SHOW_TITLE_HEADER, SHOW_CANCEL_BUTTON, etc
     * @param title
     *            The title for the top bar (only visible when using
     *            SHOW_TITLE_HEADER)
     * @param subtitle
     *            The subtitle for the top bar (only visible when using
     *            SHOW_TITLE_HEADER)
     */
    open fun onViewCreated(view: View, savedInstanceState: Bundle?, stateFragment: Int, title: String?,
                           subtitle: String?) {
        super.onViewCreated(view, savedInstanceState)
        this.stateFragment = stateFragment

//        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
//        title?.let { supportActionBar?.setTitle(it) }
//        subtitle?.let { supportActionBar?.setSubtitle(it) }

        if(context is FragmentChangedListener) {
            val listener = context as FragmentChangedListener
            listener.onFragmentChanged(stateFragment)
        }

        /*     if (stateFragment == SHOW_NOTHING) {
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
                 supportActionBar?.setDisplayShowHomeEnabled(false)
                 supportActionBar?.setDisplayShowTitleEnabled(false)
             }
             if ((stateFragment and SHOW_HOME_BUTTON) == SHOW_HOME_BUTTON) {
                 supportActionBar?.setDisplayShowHomeEnabled(true)
                 supportActionBar?.setDisplayHomeAsUpEnabled(true)
                 supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_toolbar)
             }
             if ((stateFragment and SHOW_BACK_BUTTON) == SHOW_BACK_BUTTON) {
                 supportActionBar?.setDisplayShowHomeEnabled(true)
                 supportActionBar?.setDisplayHomeAsUpEnabled(true)
                 supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_toolbar)
             }
             if ((stateFragment and SHOW_CANCEL_BUTTON) == SHOW_CANCEL_BUTTON) {
                 supportActionBar?.setDisplayShowHomeEnabled(true)
                 supportActionBar?.setDisplayHomeAsUpEnabled(true)
                 supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_toolbar)
             }

             if ((stateFragment and SHOW_TITLE_HEADER) == SHOW_TITLE_HEADER) {
                 title?.let { supportActionBar?.setTitle(it) }
                 subtitle?.let { supportActionBar?.setSubtitle(it) }
                 supportActionBar?.setDisplayShowTitleEnabled(true)
             } else {
                 supportActionBar?.setDisplayShowTitleEnabled(false)
             }

             if ((stateFragment and HIDE_TOOLBAR) == HIDE_TOOLBAR) {
                 supportActionBar?.hide()
             } else {
                 supportActionBar?.show()
             }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        onViewCreated(view, savedInstanceState, SHOW_BACK_BUTTON)
    }

    fun setLoading(progressBar: View, navigation: View) {
        this.progressBar = progressBar
        this.navigation = navigation
    }

    fun setProgressBar(progressBar: View) {
        this.progressBar = progressBar
    }

    fun setNavigation(navigation: View) {
        this.navigation = navigation
    }

    fun showLoading(){
        if(activity is BaseActivity){
            (activity as BaseActivity).showLoading()
        }
    }

    fun hideLoading() {
        if(activity is BaseActivity){
            (activity as BaseActivity).hideLoading()
        }
    }

    fun hideKeyboard(view: View){
        context?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}

interface FragmentChangedListener {
    fun onFragmentChanged(type: Int)
}