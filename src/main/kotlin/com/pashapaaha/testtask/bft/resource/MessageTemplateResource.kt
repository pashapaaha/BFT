package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import com.pashapaaha.testtask.bft.service.generateMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern

@RestController
@RequestMapping("/templates")
class MessageTemplateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @GetMapping
    fun getAll(): MutableIterable<MessageTemplate> = messageTemplateRepository.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = messageTemplateRepository.findById(id)

    fun findParameters(str: String): List<String> {
        val resultArray = mutableListOf<String>()
        val m = Pattern.compile("\\{([^}]+)}").matcher(str)
        while (m.find()) {
            resultArray.add(m.group(1))
        }
        return resultArray
    }

    fun parametersSetIsCorrect(messageTemplate: MessageTemplate): Boolean {

        val parametersFromMessageText = findParameters(messageTemplate.messageText)
        val templateParameters = messageTemplate.parameters.map { it.name }

        return templateParameters.containsAll(parametersFromMessageText)
    }

    @PostMapping
    @Transactional
    fun add(@RequestBody messageTemplate: MessageTemplate): ResponseEntity<String> {

        if (!parametersSetIsCorrect(messageTemplate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }

        messageTemplate.id = null
        return ResponseEntity.ok(saveAndPersist(messageTemplate).toString())
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@PathVariable id: Long, @RequestBody messageTemplate: MessageTemplate): ResponseEntity<String> {
        if (!parametersSetIsCorrect(messageTemplate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }
        messageTemplate.id = id
        return ResponseEntity.ok(saveAndPersist(messageTemplate).toString())
    }

    @Transactional
    fun saveAndPersist(messageTemplate: MessageTemplate): MessageTemplate {
        val template = messageTemplateRepository.save(messageTemplate)
        template.parameters.forEach {
            it.messageTemplate = template
        }
        return template
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long) = messageTemplateRepository.deleteById(id)

    @PostMapping("/generation/{id}")
    fun generateMessage(@PathVariable id: Long, @RequestBody params: Map<String, Any>): ResponseEntity<String> {
        val template = messageTemplateRepository.findById(id).get()

        if (!params.map { it.key }.containsAll(findParameters(template.messageText))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }
        return ResponseEntity.ok(generateMessage(template, params))

    }
}