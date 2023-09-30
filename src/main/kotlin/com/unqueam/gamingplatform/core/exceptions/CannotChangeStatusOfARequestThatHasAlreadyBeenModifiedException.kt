package com.unqueam.gamingplatform.core.exceptions

class CannotChangeStatusOfARequestThatHasAlreadyBeenModifiedException
    : RuntimeException("No se puede aprobar o rechazar una petición que ya ha sido atendida.")