package mas.ca.humanprofiler.domain.entities

sealed class Result<S, F> {
    data class Failure<S, F>(val error: F) : Result<S, F>()
    data class Success<S, F>(val result: S) : Result<S, F>()

    suspend fun <T> mapOnSuccess(mapperFunction: suspend (S) -> T): Result<T, F> {
        return when (this) {
            is Failure -> failure(error)
            is Success -> success(mapperFunction(result))
        }
    }
}

fun <S, F> success(result: S) = Result.Success<S, F>(result)

fun <S, F> failure(error: F) = Result.Failure<S, F>(error)
