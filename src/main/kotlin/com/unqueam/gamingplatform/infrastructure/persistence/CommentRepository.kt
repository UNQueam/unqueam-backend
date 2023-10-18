package com.unqueam.gamingplatform.infrastructure.persistence


import com.unqueam.gamingplatform.core.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
}