package com.blog.Controller;

import com.blog.Payload.CommentDto;
import com.blog.Service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentservice;

    public CommentController(CommentService commentservice) {
        this.commentservice = commentservice;
    }
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentservice.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentservice.deleteComment(commentId);
        return new ResponseEntity<>("Comment is deleted!!",HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity< List<CommentDto> > getCommentsByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentservice.getCommentsByPostId(postId);
        return new ResponseEntity <> (commentDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos = commentservice.getComments();
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);

    }
}

