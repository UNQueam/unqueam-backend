import com.unqueam.gamingplatform.core.services.IEmailService
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext

@Profile ("prod")
open class EmailService : IEmailService {

    private val javaMailSender: JavaMailSender
    private val templateEngine: TemplateEngine

    constructor(javaMailSender: JavaMailSender, templateEngine: TemplateEngine) {
        this.javaMailSender = javaMailSender
        this.templateEngine = templateEngine
    }

    @Async
    override fun sendEmail(
        subject: String,
        from: String,
        vararg to: String,
        content: MutableMap<String, Any>,
        templateName: String,
        helperInlineContent: MutableMap<String, Resource>) {
        try {
            val message = javaMailSender.createMimeMessage()
            val helper: MimeMessageHelper = buildHelper(message, subject, from, to)

            val processedHtml = buildTemplateInformation(templateName, content)

            helper.setText(processedHtml, true)

            if (helperInlineContent.isNotEmpty()) {
                helperInlineContent.forEach { (key, value) -> helper.addInline(key, value) }
            }

            javaMailSender.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    private fun buildTemplateInformation(templateName: String, content: MutableMap<String, Any>): String {
        return templateEngine.process(templateName, buildContext(content))
    }

    private fun buildContext(content: MutableMap<String, Any>): IContext {
        val context = Context()
        context.setVariables(content)
        return context
    }

    private fun buildHelper(message: MimeMessage, subject: String, from: String, to: Array<out String>): MimeMessageHelper {
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(from)
        helper.setTo(to)
        helper.setSubject(subject)
        return helper
    }
}
