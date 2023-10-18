package com.unqueam.gamingplatform.core.exceptions.comments

class InvalidCommentContentException (val errorsMap: Map<String, List<String>>) : RuntimeException()