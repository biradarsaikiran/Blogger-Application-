package com.blog.Service.impl;

import com.blog.Entity.Comment;
import com.blog.Entity.Post;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.CommentDto;
import com.blog.Repository.CommentRepository;
import com.blog.Repository.PostRepository;
import com.blog.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;



    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)
        );

        Comment comment = new Comment();
        comment.setName(comment.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());

        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + commentId)

        );
        commentRepository.deleteById(commentId);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    CommentDto mapToDto(Comment comment ){
       CommentDto dto = new CommentDto();
       dto.setId(comment.getId());
       dto .setName(comment.getName());
       dto.setEmail(comment.getEmail());
       dto .setBody(comment.getBody());
       return dto;
    }


}

