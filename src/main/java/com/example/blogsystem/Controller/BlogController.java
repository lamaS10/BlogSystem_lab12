package com.example.blogsystem.Controller;


import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;


    @GetMapping("/get")
    public ResponseEntity<?> getBlogs() {
        return ResponseEntity.status(200).body(blogService.getBlogs());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@AuthenticationPrincipal User user, @Valid @RequestBody Blog blog) {
        blogService.addBlog(user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("blog added successfully"));
    }

    @PutMapping("/update/{blogId}")
    public ResponseEntity<?> updateBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId, @Valid @RequestBody Blog blog) {
        blogService.updateBlog(user.getId(), blogId, blog);
        return ResponseEntity.status(200).body(new ApiResponse("blog updated successfully"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<?> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body(new ApiResponse("blog deleted successfully"));
    }

    @GetMapping("/get-my-blogs")
    public ResponseEntity<?> getMyBlogs(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(user.getId()));
    }

    @GetMapping("/get-by-id/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable Integer blogId) {
        return ResponseEntity.status(200).body(blogService.getBlogById(blogId));
    }

    @GetMapping("/get-by-title/{title}")
    public ResponseEntity<?> getBlogByTitle(@PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(title));
    }
}
