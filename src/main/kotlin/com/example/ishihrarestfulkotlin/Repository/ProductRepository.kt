package com.example.ishihrarestfulkotlin.Repository

import com.example.ishihrarestfulkotlin.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

/**
 *
 * ProductのRepositoryインターフェース
 *
 * @author ishiharahisataka
 *
 * */
interface ProductRepository : JpaRepository<Product, Long> {

    /**
     * 商品検索
     *
     * @param title タイトル
     * @return 商品リスト
     *
     * */
    fun findByTitleContaining(@Param("title") title: String): List<Product>


    /**
     * タイトル検索
     *
     * @param title タイトル
     * @return 商品リスト
     *
     * */
    fun findByTitle(@Param("title") title: String): List<Product?>

    /**
     * 画像名検索
     *
     * @param imgPath 画像名
     * @return 商品リスト
     *
     * */
    fun findByImgPath(imgPath: String): List<Product?>


}