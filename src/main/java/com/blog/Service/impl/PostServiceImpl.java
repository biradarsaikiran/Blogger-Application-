package com.blog.Service.impl;

import com.blog.Entity.Post;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.PostDto;
import com.blog.Repository.PostRepository;
import com.blog.Service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savedpost = postRepo.save(post);

        PostDto dto = new PostDto();
        dto.setId(savedpost.getId());
        dto.setTitle(savedpost.getTitle());
        dto.setDescription(savedpost.getDescription());
        dto.setContent(savedpost.getContent());


        return dto;


    }

    @Override
    public PostDto deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + id)

        );

        postRepo.deleteById(id);

        return null;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found with id:" + postId)
        );

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);
        return dto;
    }


    PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }


}