/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Admins;
import fpt.aptech.project.entities.Items;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import fpt.aptech.project.inteface.IAdminService;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    IAdminService service;
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Admins> list() {
        return service.findAll();
    }
}
