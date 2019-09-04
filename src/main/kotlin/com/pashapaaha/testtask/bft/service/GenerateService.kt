package com.pashapaaha.testtask.bft.service

import com.pashapaaha.testtask.bft.model.MessageTemplate

fun generateMessage(messageTemplate: MessageTemplate, params: Map<String, Any>): String {
    var resultString = messageTemplate.messageText
    params.forEach {
        resultString = resultString.replace("{${it.key}}", it.value.toString())
    }
    return resultString
}