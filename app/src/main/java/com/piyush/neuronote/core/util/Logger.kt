package com.piyush.neuronote.core.util

import timber.log.Timber

object Logger {

    fun d(message: String) {
        Timber.d(message)
    }

    fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun i(message: String) {
        Timber.i(message)
    }

    fun i(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    fun e(message: String, throwable: Throwable) {
        Timber.e(throwable, message)
    }

    fun e(tag: String, message: String, throwable: Throwable) {
        Timber.tag(tag).e(throwable, message)
    }
}