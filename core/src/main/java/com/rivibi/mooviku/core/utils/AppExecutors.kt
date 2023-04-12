package com.rivibi.mooviku.core.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
){

    companion object {
        private const val THREAD_COUNT = 3
    }

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    private class MainThreadExecutor : Executor {
        private val mainThreadHandleInfo = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandleInfo.post(command)
        }
    }
}