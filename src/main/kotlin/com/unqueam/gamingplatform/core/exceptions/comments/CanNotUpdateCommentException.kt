package com.unqueam.gamingplatform.core.exceptions.comments

class CanNotUpdateCommentException : RuntimeException("Solo el dueño de un comentario puede editarlo")