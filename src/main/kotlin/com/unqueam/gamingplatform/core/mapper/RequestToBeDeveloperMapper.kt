package com.unqueam.gamingplatform.core.mapper

import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperOutput
import com.unqueam.gamingplatform.application.dtos.BecomeDeveloperRequest
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloper
import com.unqueam.gamingplatform.core.domain.RequestToBeDeveloperStatus
import com.unqueam.gamingplatform.core.domain.PlatformUser
import org.apache.commons.lang3.StringUtils
import java.time.LocalDateTime

class RequestToBeDeveloperMapper {

    fun mapToInput(becomeDeveloperRequest: BecomeDeveloperRequest, user: PlatformUser): RequestToBeDeveloper {
        return RequestToBeDeveloper(
            null,
            user,
            LocalDateTime.now(),
            RequestToBeDeveloperStatus.PENDING,
            becomeDeveloperRequest.reasonToBeDeveloper,
            StringUtils.EMPTY
        )
    }

    fun mapToOutput(requestToBeDeveloper: RequestToBeDeveloper): BecomeDeveloperOutput {
        return BecomeDeveloperOutput(
            requestToBeDeveloper.getId(),
            requestToBeDeveloper.getIssuerId(),
            "¡Solicitud enviada con éxito! Un administrador la revisará pronto.",
            requestToBeDeveloper.getDateTime(),
            requestToBeDeveloper.status()
        )
    }

}