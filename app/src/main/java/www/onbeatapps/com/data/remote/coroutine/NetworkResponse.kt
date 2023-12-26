package www.onbeatapps.com.data.remote.coroutine

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : www.onbeatapps.com.data.remote.coroutine.NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class Error<U : Any>(val body: U?, val code: Int?,val throwable: Throwable?) : www.onbeatapps.com.data.remote.coroutine.NetworkResponse<Nothing, U>()
}