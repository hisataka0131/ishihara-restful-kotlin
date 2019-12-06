package com.example.ishihrarestfulkotlin.Service

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.example.ishihrarestfulkotlin.Repository.ProductRepository
import com.example.ishihrarestfulkotlin.config.AWSProperties
import com.example.ishihrarestfulkotlin.exception.ProductAlreadyExistException
import com.example.ishihrarestfulkotlin.exception.ProductIOException
import com.example.ishihrarestfulkotlin.model.Product
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.transaction.Transactional

/**
 * Productのサービスクラス
 *
 * @author ishiharahisataka
 * */
@Service
@Transactional
class ProductService(private val productRepository: ProductRepository, private val s3Client: AmazonS3, val awsProperties: AWSProperties) {


    /**
     *
     * 商品全件取得
     *
     * @return 商品リスト
     *
     * */
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    /**
     *
     * 商品新規作成
     *
     * @param product 商品
     *
     * */
    fun save(product: Product) {
        if (isExistTitle(product.title)) {
            throw ProductAlreadyExistException("この商品名はすでに登録されています")
        }
        val newProduct = Product(title = product.title, opinion = product.opinion, price = product.price)
        productRepository.save(newProduct)
    }

    /**
     *
     * 商品一件取得
     *
     * @param id ID
     * @return 商品情報
     *
     * */
    fun findById(id: Long): Product? {
        return productRepository.findById(id).orElse(null)
    }

    /**
     *
     * 商品更新
     *
     * @param product 商品
     *
     * */
    fun update(target: Product, product: Product) {
        if (isExistTitle(product.title)) {
            throw ProductAlreadyExistException("この商品名はすでに登録されています")
        }
        val updateProduct: Product = target.copy(title = product.title, opinion = product.opinion, price = product.price)
        productRepository.save(updateProduct)

    }

    /**
     *
     * 商品削除
     *
     * @param id ID
     * */
    fun deleteById(id: Long) {
        val product: Product? = findById(id)
        productRepository.deleteById(id)
        if (!StringUtils.isEmpty(product?.imgPath)) {
            try {
                s3Client.deleteObject(awsProperties.bucket, awsProperties.dir + product?.imgPath)
            } catch (e: AmazonServiceException) {
                throw IOException(e)
            }
        }
    }

    /**
     *
     * 商品検索
     *
     * @param title タイトル
     * @return 商品リスト
     * */
    fun searchProduct(title: String): List<Product> {
        return productRepository.findByTitleContaining(title)
    }

    /**
     * 商品画像をDBに保存
     *
     * @param id ID
     * @param multiPartFile マルチパートファイル
     *
     * @return 商品情報
     *
     * */
    fun updateImg(id: Long, multiPartFile: MultipartFile): Product? {
        val product: Product? = findById(id)
        if (!StringUtils.isEmpty(product?.imgPath)) {
            try {
                s3Client.deleteObject(awsProperties.bucket, awsProperties.dir + product?.imgPath)
                product?.imgPath = null
                product?.let { productRepository.save(it) }
            } catch (e: AmazonServiceException) {
                throw ProductIOException("ファイル操作に失敗しました")
            }
        }

        val fileName = upload(product?.title, multiPartFile)
        product?.imgPath = fileName

        return product?.let { productRepository.save(it) }
    }


    /**
     *
     * 商品画像をS３にアップデート
     *
     * @param fileName ファイル名
     * @param multiPartFile マルチパートファイル
     *
     * @return ファイル名
     *
     * */
    private fun upload(fileName: String?, multiPartFile: MultipartFile): String {

        val extention: String?

        if (multiPartFile.originalFilename != null) {

            val point: Int? = with(multiPartFile) { originalFilename?.lastIndexOf(".") }

            extention = with(multiPartFile) { point?.let { originalFilename?.substring(it) } }

            try {
                val originalFileName = fileName + extention
                if (isExistImgPath(originalFileName)) {
                    throw ProductAlreadyExistException("この画像名はすでに存在しています")
                }
                val metaData = ObjectMetadata()
                metaData.contentLength
                s3Client.putObject(awsProperties.bucket, awsProperties.dir + originalFileName, multiPartFile.inputStream, metaData)
                return originalFileName
            } catch (e: IOException) {
                throw ProductIOException("ファイル操作に失敗しました")

            }

        } else {
            throw ProductAlreadyExistException("このファイルはすでに存在しています")
        }
    }

    /**
     *　タイトル重複確認
     *
     * @param title タイトル
     * @return 重複してるかの結果
     *
     * */
    private fun isExistTitle(title: String): Boolean {
        val product = productRepository.findByTitle(title)
        if (product.isNullOrEmpty()) {
            return true
        }
        return false

    }

    /**
     *
     * 画像名重複確認
     *
     * @param imgPath 画像名
     * @return 重複してるかの結果
     *
     * */
    private fun isExistImgPath(imgPath: String): Boolean {
        val product = productRepository.findByImgPath(imgPath)
        if (product.isNullOrEmpty()) {
            return true
        }
        return false
    }


}