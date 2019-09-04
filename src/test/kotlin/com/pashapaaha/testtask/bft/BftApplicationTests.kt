package com.pashapaaha.testtask.bft

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.model.TemplateParameter
import com.pashapaaha.testtask.bft.resource.GenerateResource
import com.pashapaaha.testtask.bft.service.generateMessage
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class BftApplicationTests {

    @Autowired
    var generateResource: GenerateResource? = null

    @Test
    fun contextLoads() {
        val messageTemplate = MessageTemplate(
                id = null,
                messageText = "hey you, {next}, {next1}",
                parameters = arrayListOf(
                        TemplateParameter(null, "next", null),
                        TemplateParameter(null, "next1", null)
                ),
                isActive = true
        )

        println(generateMessage(messageTemplate, mapOf(
                "next" to arrayOf("1", "2", "3").joinToString(),
                "next1" to "123"
        )))
    }

    @Test
    fun asyncTest() {
        generateResource ?: return println("resource is null")
        val map = mapOf("scream" to "yeah")

        println("Start async job")
        generateResource?.generateMessageAsyncRequest(21, map)
        println("End async job")
    }
}
