package com.codeturtle.notes.common.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "NETWORK"
    val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        if (!idlingResource.isIdleNow) {
            idlingResource.decrement()
        }
    }
}