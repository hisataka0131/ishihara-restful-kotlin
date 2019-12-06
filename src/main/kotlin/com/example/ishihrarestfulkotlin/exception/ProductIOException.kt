package com.example.ishihrarestfulkotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 *
 * ファイル操作のExceptionクラス
 *
 * @author ishiharahisataka
 *
 *
 *
 * */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class ProductIOException(message: String) : RuntimeException(message) {


}