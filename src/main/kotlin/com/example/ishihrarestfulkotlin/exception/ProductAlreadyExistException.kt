package com.example.ishihrarestfulkotlin.exception

/**
 *
 * Product重複のExceptionクラス
 *
 * @author ishiharahisataka
 *
 *
 *
 * */
class ProductAlreadyExistException(message: String) : RuntimeException(message) {
}