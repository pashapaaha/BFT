package com.pashapaaha.testtask.bft.model

import javax.persistence.*

@Entity
data class MessageTemplate(
        @Id
        @GeneratedValue
        var id: Long?,
        var messageText: String,
        @OneToMany(
                fetch = FetchType.LAZY,
                mappedBy = "messageTemplate",
                cascade = [CascadeType.ALL],
                orphanRemoval = true)
        var parameters: List<TemplateParameter>,
        var isActive: Boolean
)