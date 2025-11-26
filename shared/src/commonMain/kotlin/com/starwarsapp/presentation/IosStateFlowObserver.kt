package com.starwarsapp.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

class IosStateFlowObserver<T>(
    private val stateFlow: StateFlow<T>,
    private val scope: CoroutineScope,
    private val onUpdate: (T) -> Unit
) {
    private var job: Job? = null
    
    fun start() {
        job = scope.launch {
            stateFlow.collect { value ->
                onUpdate(value)
            }
        }
    }
    
    fun stop() {
        job?.cancel()
        job = null
    }
}

