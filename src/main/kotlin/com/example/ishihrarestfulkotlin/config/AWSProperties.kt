package com.example.ishihrarestfulkotlin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "aws")
class AWSProperties {

    lateinit var region: String

    lateinit var bucket: String

    lateinit var dir: String

    lateinit var accessKey: String

    lateinit var secretKey: String


}