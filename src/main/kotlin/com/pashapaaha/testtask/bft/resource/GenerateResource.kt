package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import com.pashapaaha.testtask.bft.service.findParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/generate")
class GenerateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @PostMapping("/{id}")
    fun generateMessage(@PathVariable id: Long, @RequestBody params: Map<String, Any>): ResponseEntity<String> {
        if (!messageTemplateRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        }
        val template = messageTemplateRepository.findById(id).get()
        if (!params.map { it.key }.containsAll(findParameters(template.messageText))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter set")
        }
        return ResponseEntity.ok(com.pashapaaha.testtask.bft.service.generateMessage(template, params))
    }
}