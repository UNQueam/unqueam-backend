package com.unqueam.gamingplatform.core.exceptions.comments

class CanNotDeleteCommentException : RuntimeException("Solo los admins y los dueños del comentario pueden borrarlo")