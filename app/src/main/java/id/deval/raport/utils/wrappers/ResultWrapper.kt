package id.deval.raport.utils.wrappers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T>{
    data class Success<out T>(val value: T): ResultWrapper<T>()
//    data class GenericError(val globalWrapper: GlobalWrapper): ResultWrapper<GlobalWrapper<Nothing>>()
    object NetworkError : ResultWrapper<GlobalWrapper<Nothing>>()
}

//
//suspend fun <T> safeApiCall(globalScope: GlobalScope, apiCall : suspend () -> T): ResultWrapper<T>{
//    return globalScope.launch {
//        try {
//            ResultWrapper.Success(apiCall.invoke())
//        } catch (throwable: Throwable){
//            when(throwable){
//                is IOException -> ResultWrapper.NetworkError
//                is HttpException-> {
//                    ResultWrapper.GenericError(Nothing)
//                }
//            }
//        }
//    }
//}