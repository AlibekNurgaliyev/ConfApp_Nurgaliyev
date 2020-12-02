package kz.kolesateam.confapp.events.utils.model

sealed class ResponseData<out Result, out Error> {
        data class Success<Result>(
            val result: Result
        ): ResponseData<Result, Nothing>()

        data class Error<Error>(
            val error: Error
        ): ResponseData<Nothing, Error>()
}