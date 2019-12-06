package com.example.ishihrarestfulkotlin.controller.api

import com.example.ishihrarestfulkotlin.Repository.ProductRepository
import com.example.ishihrarestfulkotlin.Service.ProductService
import com.example.ishihrarestfulkotlin.exception.ProductNotFoundException
import com.example.ishihrarestfulkotlin.model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile

/**
 * ProductのControllerクラス
 *
 * @author ishiharahisataka
 * */
@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService, private val productRepository: ProductRepository) {

    /**
     * 商品一覧
     *
     * @return 商品全件
     *
     * */
    @GetMapping
    fun list(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(productService.findAll())
    }

    /**
     *
     * 商品新規投稿
     *
     * @param product 商品
     * @return 投稿結果
     *
     * */
    @PostMapping
    fun create(@RequestBody product: Product): ResponseEntity<String> {
        productService.save(product)
        return ResponseEntity.ok("create success")
    }

    /**
     * 商品１件取得
     *
     * @param id ID
     * @return 取得結果
     *
     * */
    @GetMapping("/{id}")
    fun id(@PathVariable(value = "id") id: Long): ResponseEntity<Product> {
        val product = productService.findById(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            throw ProductNotFoundException("商品が見つかりません")
        }

    }

    /**
     *
     * 商品更新
     *
     * @param id ID
     * @return 更新結果
     *
     * */
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody product: Product): ResponseEntity<String> {
        val targetProduct = productService.findById(id)
        targetProduct?.let { productService.update(it, product) }
        return ResponseEntity.ok("update success")
    }


    /**
     * 商品削除
     *
     * @param id ID
     * @return 削除結果
     *
     *
     * */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<String> {

        val product = productService.findById(id)
        if (product != null) {
            productService.deleteById(id)
        } else {
            throw ProductNotFoundException("商品が見つかりません")

        }

        return ResponseEntity.ok("delete success")
    }

    /**
     * 商品検索
     *
     * @param title タイトル
     * @return 検索結果
     *
     * */
    @GetMapping("/search")
    fun getProductsByTitle(@RequestParam("title") title: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(productService.searchProduct(title))
    }

    /**
     *
     * 商品画像アップデート
     *
     * @param id ID
     * @param multipartFile マルチパートファイル
     *
     * @return 商品情報
     * */
    @PostMapping("/{id}/imageUpload")
    fun uploadImage(@PathVariable("id") id: Long, @RequestParam("imgPath") multipartFile: MultipartFile): Product? {

        if (multipartFile.isEmpty) {
            throw MultipartException("画像が未選択です")
        }

        return productService.updateImg(id, multipartFile)
    }


}