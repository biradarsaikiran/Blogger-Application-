package com.blog.Service;

import com.blog.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postid, CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPostId(long postId);

    List<CommentDto> getComments();
}
