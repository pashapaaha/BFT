package com.pashapaaha.testtask.bft.repository

import com.pashapaaha.testtask.bft.model.MessageTemplate
import org.springframework.data.repository.CrudRepository

interface MessageTemplateRepository: CrudRepository<MessageTemplate, Long> {
}