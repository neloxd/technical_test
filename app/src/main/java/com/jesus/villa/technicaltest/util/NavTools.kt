package com.jesus.villa.technicaltest.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections


/**
 * Created by Jesús Villa on 22/01/23
 */
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}