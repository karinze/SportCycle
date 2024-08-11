/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BlogPost;
import fpt.aptech.project.service.BlogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/blogs/")
public class BlogController {
    @Autowired
    private BlogService blogService;

    // Lấy danh sách tất cả các bài viết
    @GetMapping("")
    public List<BlogPost> getAllBlogs() {
        return blogService.findAll();
    }

    // Tạo một bài viết mới
    @PostMapping("")
    public ResponseEntity<BlogPost> createBlog(@RequestBody BlogPost blog) {
        BlogPost createdBlog = blogService.createBlogPost(blog);
        return ResponseEntity.ok(createdBlog);
    }

    // Cập nhật trạng thái active của một bài viết
    @PutMapping("{id}")
    public ResponseEntity<String> updateBlogStatus(@PathVariable int id, @RequestBody BlogPost blog) {
        blog.setId(id);  // Đảm bảo id của đối tượng BlogPost khớp với id trong URL
        blogService.updateBlog(blog);
        return ResponseEntity.ok("Blog post status updated successfully.");
    }
}
