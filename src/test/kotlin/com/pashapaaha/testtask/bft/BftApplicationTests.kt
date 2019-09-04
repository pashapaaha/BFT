package com.pashapaaha.testtask.bft

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.model.TemplateParameter
import com.pashapaaha.testtask.bft.service.generateMessage
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class BftApplicationTests {

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

}
