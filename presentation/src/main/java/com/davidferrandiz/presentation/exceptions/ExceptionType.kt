package com.davidferrandiz.presentation.exceptions

import java.lang.Exception

sealed class ExceptionType : Exception() {
    class NetworkException : ExceptionType()
    class ErrorException : ExceptionType()
    class GenericException : ExceptionType()
}