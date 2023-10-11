package com.unqueam.gamingplatform.core.services.implementation

import com.unqueam.gamingplatform.core.services.IEmailService
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.scheduling.annotation.Async

@Profile ("test")
open class FixedEmailService : IEmailService {

    @Async
    override fun sendEmail(
        subject: String,
        from: String,
        vararg to: String,
        content: MutableMap<String, Any>,
        templateName: String,
        helperInlineContent: MutableMap<String, Resource>) {
        // Do nothing.
    }
}
