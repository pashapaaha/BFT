package com.pashapaaha.testtask.bft.service

import com.pashapaaha.testtask.bft.model.MessageTemplate
import java.util.regex.Pattern

fun generateMessage(messageTemplate: MessageTemplate, params: Map<String, Any>): String {
    var resultString = messageTemplate.messageText
    params.forEach {
        resultString = resultString.replace("{${it.key}}", it.value.toString())
    }
    return resultString
}


fun findParameters(str: String): List<String> {
    val resultArray = mutableListOf<String>()
    val m = Pattern.compile("\\{([^}]+)}").matcher(str)
    while (m.find()) {
        resultArray.add(m.group(1))
    }
    return resultArray
}

fun parametersSetIsCorrect(messageTemplate: MessageTemplate): Boolean {

    val parametersFromMessageText = findParameters(messageTemplate.messageText)
    val templateParameters = messageTemplate.parameters.map { it.name }

    return templateParameters.containsAll(parametersFromMessageText)
}
