package com.example.ishihrarestfulkotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 *
 * 404エラーExceptionクラス
 *
 * @author ishiharahisataka
 *
 *
 *
 * */
@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException(message: String) : RuntimeException(message) {

}