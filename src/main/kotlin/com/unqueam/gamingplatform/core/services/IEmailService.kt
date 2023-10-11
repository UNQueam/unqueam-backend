package com.unqueam.gamingplatform.core.services

import org.springframework.core.io.Resource

interface IEmailService {

    fun sendEmail(subject: String, from: String, vararg to: String, content: MutableMap<String, Any>, templateName: String, helperInlineContent: MutableMap<String, Resource>)
}