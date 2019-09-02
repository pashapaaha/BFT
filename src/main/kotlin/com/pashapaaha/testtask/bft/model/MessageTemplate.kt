package com.pashapaaha.testtask.bft.model

import javax.persistence.*

@Entity
data class MessageTemplate(
        @Id
        @GeneratedValue
        val id: Long,
        var messageText: String,
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "messageTemplate")
        var parameters: List<TemplateParameter>,
        var isActive: Boolean
)