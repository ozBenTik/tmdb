package util

import kotlinx.coroutines.flow.*
import result.Result
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

@JvmName("collectStatusR")
suspend fun <T> Flow<Result<T>>.collectStatus(
    counter: ObservableLoadingCounter,
    UiMessageManager: UiMessageManager? = null,
) = onStart { counter.addLoader() }.collect { result ->
    when (result) {
        is Result.Success -> counter.removeLoader()
        is Result.Error -> {
            UiMessageManager?.emitMessage(UiMessage(result.exception))
            counter.removeLoader()
        }
    }
}