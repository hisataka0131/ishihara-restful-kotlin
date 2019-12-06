package com.example.ishihrarestfulkotlin.exception

import org.slf4j.Logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 *
 * Productのハンドラークラス
 *
 * @author ishiharahisataka
 *
 *
 *
 * */
@RestControllerAdvice(basePackages = ["com.example.ishihrarestfulkotlin.controller.api"])
class ProductExceptionHandler(val log: Logger) : ResponseEntityExceptionHandler() {

    /**
     * 404エラーハンドラー
     *
     * @param e Exception
     * @param webRequest リクエスト
     * @param httpHeaders ヘッダー
     *
     * @return レスポンス
     *
     * */
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleNotFoundException(e: ProductNotFoundException, webRequest: WebRequest, httpHeaders: HttpHeaders): ResponseEntity<Any?> {
        log.warn(e.message, e)
        val errorResponse = ErrorResponse().errorResponse(message = "リクエストが見つかりません")
        return super.handleExceptionInternal(e, errorResponse, httpHeaders, HttpStatus.NOT_FOUND, webRequest)
    }

    /**
     * 500エラーハンドラー
     *
     * @param e Exception
     * @param webRequest リクエスト
     * @param httpHeaders ヘッダー
     *
     * @return レスポンス
     *
     * */
    @ExceptionHandler(Exception::class)
    fun handleUnknownException(e: Exception, webRequest: WebRequest, httpHeaders: HttpHeaders): ResponseEntity<Any> {
        log.error(e.message, e)
        val errorResponse = ErrorResponse().errorResponse(message = "サーバーエラー")
        return super.handleExceptionInternal(e, errorResponse, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, webRequest)
    }


    /**
     * 重複エラー
     *
     * @param e Exception
     * @param webRequest リクエスト
     * @param httpHeaders ヘッダー
     *
     * @return レスポンス
     *
     * */
    @ExceptionHandler(ProductAlreadyExistException::class)
    fun handleProductAlreadyExistException(e: ProductAlreadyExistException, webRequest: WebRequest, httpHeaders: HttpHeaders): ResponseEntity<Any> {
        log.error(e.message, e)
        val errorResponse = ErrorResponse().errorResponse(message = "既に画像は登録されています")
        return super.handleExceptionInternal(e, errorResponse, httpHeaders, HttpStatus.BAD_REQUEST, webRequest)
    }


    /**
     * ファイル操作エラー
     *
     * @param e Exception
     * @param webRequest リクエスト
     * @param httpHeaders ヘッダー
     *
     * @return レスポンス
     *
     * */
    @ExceptionHandler(ProductIOException::class)
    fun handleProductIOException(e: ProductIOException, webRequest: WebRequest, httpHeaders: HttpHeaders): ResponseEntity<Any> {
        log.warn(e.message, e)
        val errorResponse = ErrorResponse().errorResponse(message = "ファイルの操作に失敗しました")
        return super.handleExceptionInternal(e, errorResponse, httpHeaders, HttpStatus.BAD_REQUEST, webRequest)
    }


}