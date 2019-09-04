package com.pashapaaha.testtask.bft.confing

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.spring.web.plugins.Docket

@EnableSwagger2
@Configuration
class SwaggerConfig {
    @Bean
    fun productApi() = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.pashapaaha.testtask.bft"))
            .paths(regex("/.*"))
            .build()
            .apiInfo(metaInfo())

    private fun metaInfo() = ApiInfo(
            "BFT Test Task",
            "This application is a message template generator!",
            "1.0",
            "",
            Contact("Pavel Andreev", null, "paaha98@gmail.com"),
            "",
            "",
            listOf()
    )
}