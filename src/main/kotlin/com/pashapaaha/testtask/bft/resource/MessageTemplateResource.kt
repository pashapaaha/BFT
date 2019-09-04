package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import com.pashapaaha.testtask.bft.service.parametersSetIsCorrect
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/templates")
@Api(value = "CRUD-actions", description = "The methods for creating, reading, updating and deleting templates")
class MessageTemplateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @ApiOperation("Show all existing templates")
    @GetMapping
    fun getAll(): ResponseEntity<Iterable<MessageTemplate>> {
        return ResponseEntity.ok(messageTemplateRepository.findAll())
    }

    @ApiOperation("Show template with index")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<String> {
        val template = messageTemplateRepository.findById(id)
        return if (template.isEmpty) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        } else {
            ResponseEntity.ok(template.get().toString())
        }
    }


    @ApiOperation("Add new template using JSON")
    @PostMapping
    @Transactional
    fun add(@RequestBody messageTemplate: MessageTemplate): ResponseEntity<String> {

        if (!parametersSetIsCorrect(messageTemplate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }

        messageTemplate.id = null
        return ResponseEntity.ok(saveAndPersist(messageTemplate).toString())
    }

    @ApiOperation("Update template using JSON and id")
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

    @ApiOperation("Delete template with id")
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

    /**
     * Метод для сохранения в базу указанного объекта, а также присоединенных сущностей
     */
    @Transactional
    fun saveAndPersist(messageTemplate: MessageTemplate): MessageTemplate {
        val template = messageTemplateRepository.save(messageTemplate)
        template.parameters.forEach {
            it.messageTemplate = template
        }
        return template
    }
}