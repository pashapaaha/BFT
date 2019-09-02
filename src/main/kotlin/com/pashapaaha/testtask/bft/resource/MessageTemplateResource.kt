package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/templates")
class MessageTemplateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @GetMapping
    fun getAll(): MutableIterable<MessageTemplate> = messageTemplateRepository.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = messageTemplateRepository.findById(id)

    @PostMapping
    @Transactional
    fun add(@RequestBody messageTemplate: MessageTemplate): MessageTemplate {
        val template = messageTemplateRepository.save(messageTemplate)
        template.parameters.forEach {
            it.messageTemplate = template
        }
        return template
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: Long) = messageTemplateRepository.deleteById(id)

}