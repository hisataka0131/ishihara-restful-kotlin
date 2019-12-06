package com.example.ishihrarestfulkotlin.exception

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * エラーレスポンスクラス
 *
 *
 * @author ishiharahisataka
 *
 * */
class ErrorResponse() {
    @JsonProperty("error")
    lateinit var error: Error


    fun errorResponse(message: String) {
        this.error = ErrorResponse.Error(message)
    }


    class Error(@JsonProperty("message") val message: String)


}