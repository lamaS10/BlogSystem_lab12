package com.example.blogsystem.Service;


import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.AuthRepository;
import com.example.blogsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog>getBlogs(){
        return blogRepository.findAll();
    }


    public void addBlog(Integer userId,Blog blog){
        User user=authRepository.findUserById(userId);

        if (user==null){
            throw new ApiException("user not allowed");
        }
        blog.setUser(user);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer userId,Integer blogId,Blog blog){
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("user not allowed");
        }

        Blog oldBlog=blogRepository.findBlogById(blogId);
        if (oldBlog==null){
            throw new ApiException("didn't find blog");
        }

        if (!oldBlog.getUser().getId().equals(userId)){
            throw new ApiException("this blog doesn't belong to the user");

        }
        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());

        blogRepository.save(oldBlog);

    }

    public void deleteBlog(Integer userId,Integer blogId){

        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("user not allowed");
        }

        Blog blog=blogRepository.findBlogById(blogId);

        if (blog==null){
            throw new ApiException("didn't find blog");
        }


        if (!blog.getUser().getId().equals(userId) && !user.getRole().equals("ADMIN")) {
            throw new ApiException("you are not allowed to delete this blog");
        }

        blogRepository.delete(blog);
    }


    public List<Blog>getMyBlogs(Integer userId){
        User user=authRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("didn't find the user");
        }
        return blogRepository.findAllByUser(user);
    }

    public Blog getBlogById(Integer blogId) {
        Blog blog = blogRepository.findBlogById(blogId);

        if (blog == null) {
            throw new ApiException("didn't find blog");
        }

        return blog;
    }

    public List<Blog> getBlogByTitle(String title) {
        return blogRepository.findAllByTitle(title);
    }




}
