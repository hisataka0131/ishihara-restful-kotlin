package com.example.ishihrarestfulkotlin.model

import javax.persistence.*

/**
 *
 * ProductのEntityクラス
 *
 * @author ishiharahisataka
 * */
@Entity
@Table(name = "products")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        var title: String,

        var opinion: String,

        var price: Long,
       
        var imgPath: String? = null)