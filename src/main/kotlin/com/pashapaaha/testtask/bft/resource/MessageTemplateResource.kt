package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.model.MessageTemplate
import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RestController
@RequestMapping("/templates")
class MessageTemplateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @GetMapping
    @Transactional
    fun getAll(): MutableIterable<MessageTemplate> = messageTemplateRepository.findAll()
}