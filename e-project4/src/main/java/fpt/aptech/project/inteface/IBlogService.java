/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.*;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IBlogService {
     public List<BlogPost> findAll();
     
     public BlogPost createBlogPost(BlogPost blog);
     
     public void updateBlog(BlogPost blog); 
}
