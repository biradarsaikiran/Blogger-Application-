package com.blog.Controller;

import com.blog.Payload.PostDto;
import com.blog.Service.PostService;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;

 import org.springframework.http.ResponseEntity;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid  @RequestBody PostDto postDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable long id){
        PostDto dto  = postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }






    //http://localhost:8080/api/posts?pageNo=0&pagaSize=5&sortBy=title&sortBy=asc
    @GetMapping
    public ResponseEntity<List<PostDto>>getAllPosts(
          @RequestParam(name="pagNo", defaultValue = "0",required = false) int pageNo,
          @RequestParam(name="pageSize",defaultValue = "3",required = false) int pageSize,
          @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
          @RequestParam(name="sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        List<PostDto> postDtos =  postService.getAllPosts(pageNo,pageSize,sortBy, sortDir);
        return new ResponseEntity<>(postDtos,HttpStatus.OK) ;
    }





    //http://localhost:8080/api/posts?postId=1


    @PutMapping
    public ResponseEntity<PostDto> updatePost(
      @RequestParam("postId") long postId,
      @RequestBody  PostDto postDto
    ){
        PostDto dto = postService.updatePost(postId,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
}

