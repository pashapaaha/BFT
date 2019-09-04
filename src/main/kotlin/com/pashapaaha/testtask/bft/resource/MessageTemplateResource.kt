package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import com.pashapaaha.testtask.bft.service.parametersSetIsCorrect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/templates")
class MessageTemplateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @GetMapping
    fun getAll(): ResponseEntity<Iterable<MessageTemplate>> {
        return ResponseEntity.ok(messageTemplateRepository.findAll())
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<String> {
        val template = messageTemplateRepository.findById(id)
        return if (template.isEmpty) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        } else {
            ResponseEntity.ok(template.get().toString())
        }
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
        if (!messageTemplateRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        }
        if (!parametersSetIsCorrect(messageTemplate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }
        messageTemplate.id = id
        return ResponseEntity.ok(saveAndPersist(messageTemplate).toString())
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return if (messageTemplateRepository.existsById(id)) {
            messageTemplateRepository.deleteById(id)
            ResponseEntity.ok("Template was delete successfully")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        }
    }

    @Transactional
    fun saveAndPersist(messageTemplate: MessageTemplate): MessageTemplate {
        val template = messageTemplateRepository.save(messageTemplate)
        template.parameters.forEach {
            it.messageTemplate = template
        }
        return template
    }
}