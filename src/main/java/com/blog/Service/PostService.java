package com.blog.Service;

import com.blog.Payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    PostDto deletePost(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePost(long postId, PostDto postDto);
}
