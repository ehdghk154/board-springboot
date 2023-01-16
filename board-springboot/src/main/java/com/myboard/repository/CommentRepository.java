package com.myboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myboard.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
