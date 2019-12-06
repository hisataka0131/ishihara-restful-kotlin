package com.example.ishihrarestfulkotlin.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(val awsProperties: AWSProperties) {


    @Bean
    fun getAmazonS3(): AmazonS3 {
        val credentials: AWSCredentials = BasicAWSCredentials(awsProperties.accessKey, awsProperties.secretKey)
        return AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .withRegion(awsProperties.region)
                .build()
    }

}