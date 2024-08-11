/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.BlogPost;
import fpt.aptech.project.inteface.IBlogService;
import fpt.aptech.project.repository.BlogRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class BlogService implements IBlogService {

    @Autowired
    BlogRepository repository;

    @Override
    public List<BlogPost> findAll() {
        return repository.findAll();
    }

    @Override
    public BlogPost createBlogPost(BlogPost blog) {
        return repository.save(blog);
    }

   @Override
public void updateBlog(BlogPost blog) {
    repository.updateActiveStatus(blog.getId(), blog.isActive());
}


}
