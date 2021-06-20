package org.om.onestepjob.model

import androidx.annotation.StringRes
import org.om.onestepjob.model.enums.Status

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val throwable: Throwable? = null,
    @StringRes val messageStringResource: Int? = null
) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(
            throwable: Throwable,
            @StringRes msgStringRes: Int
        ): Resource<T> {
            return Resource(status = Status.ERROR, throwable = throwable, messageStringResource = msgStringRes)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }
}
