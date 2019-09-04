package com.pashapaaha.testtask.bft.resource

import com.pashapaaha.testtask.bft.repository.MessageTemplateRepository
import com.pashapaaha.testtask.bft.service.findParameters
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/generate")
class GenerateResource(@Autowired val messageTemplateRepository: MessageTemplateRepository) {

    @ApiOperation("Generate message by template id and parameters")
    @PostMapping("/{id}")
    fun generateMessage(@PathVariable id: Long, @RequestBody params: Map<String, Any>): ResponseEntity<String> {
        if (!messageTemplateRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template is not exist")
        }
        val template = messageTemplateRepository.findById(id).get()
        if (!template.isActive) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Template isn't active")
        }
        val requiredParameters = findParameters(template.messageText)
        if (!params.map { it.key }.containsAll(requiredParameters)) {
            val message = "Invalid parameter set\nRequired parameters: ${requiredParameters.joinToString(separator = ",")}"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message)
        }
        return ResponseEntity.ok(com.pashapaaha.testtask.bft.service.generateMessage(template, params))
    }
}