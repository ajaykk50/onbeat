package www.onbeatapps.com.ui.base

/**
 * Created by PKB on 09-06-2021.
 * PKB@gmail.com
 */

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable?) : Result<Nothing>()
    object InProgress : Result<Nothing>()
}

sealed class DataEvent<out T : Any> {
    data class Start<out T : Any>(val data: T) : DataEvent<T>()
    data class Error(val throwable: Throwable?) : DataEvent<Nothing>()
    object Finish : DataEvent<Nothing>()
}

sealed class DataEventWProgress<out T : Any> {
    object InProgress : DataEventWProgress<Nothing>()
    data class Start<out T : Any>(val data: T) : DataEventWProgress<T>()
    data class Error(val throwable: Throwable?) : DataEventWProgress<Nothing>()
    object Finish : DataEventWProgress<Nothing>()
}

sealed class QuestionnaireEvent<out T : Any> {
    data class Success<out T : Any>(val data: T) : QuestionnaireEvent<T>()
    data class Error(val throwable: Throwable?) : QuestionnaireEvent<Nothing>()
    object InProgress : QuestionnaireEvent<Nothing>()
    object Settle : QuestionnaireEvent<Nothing>()
}

sealed class ExceptionEvent {
    data class Throw(val errorBody: www.onbeatapps.com.data.ErrorBody?, val errorCode: Int?, val throwable: Throwable?) :
        ExceptionEvent()

    object Finish : ExceptionEvent()
}

sealed class Event {
    data class Data(val id: String) :
        Event()

    object Finish : Event()
}

sealed class DelayedNav {
    data class Data(val id: Int) :
        DelayedNav()

    object Finish : DelayedNav()
}

sealed class Events {
    data class Data(val event: EventTypes) :
        Events()

    object Finish : Events()
}

enum class EventTypes {

}