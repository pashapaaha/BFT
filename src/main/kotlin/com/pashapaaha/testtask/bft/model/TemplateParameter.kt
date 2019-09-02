package com.pashapaaha.testtask.bft.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class TemplateParameter(
        @JsonIgnore
        @Id
        @GeneratedValue
        val id: Long?,
        var name: String,
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_template")
        var messageTemplate: MessageTemplate?
) {
        override fun toString(): String {
                return name
        }
}