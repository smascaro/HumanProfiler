package mas.ca.humanprofiler.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

var dispatcherMain: CoroutineDispatcher = Dispatchers.Main
var dispatcherIO: CoroutineDispatcher = Dispatchers.IO
var dispatcherBackground: CoroutineDispatcher = Dispatchers.Default

fun launchCoroutineUI(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(dispatcherMain).launch { block() }
fun launchCoroutineBackground(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(dispatcherBackground).launch { block() }
fun launchCoroutineIO(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(dispatcherIO).launch { block() }

fun CoroutineScope.launchUI(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(dispatcherMain, CoroutineStart.DEFAULT, block)
}

fun CoroutineScope.launchBackground(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(dispatcherBackground, CoroutineStart.DEFAULT, block)
}

fun CoroutineScope.launchIO(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(dispatcherIO, CoroutineStart.DEFAULT, block)
}