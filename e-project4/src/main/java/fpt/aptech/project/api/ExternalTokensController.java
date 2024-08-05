/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.ExternalTokens;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IExternalTokensService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/externalTokens/")
public class ExternalTokensController {
    @Autowired
    IExternalTokensService service;
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ExternalTokens> list() {
        return service.findAll();
    }

    @GetMapping("/{externalTokensId}")
    @ResponseStatus(HttpStatus.OK)
    public ExternalTokens get(@PathVariable("externalTokensId") int externalTokensId) {
        return service.findOne(externalTokensId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody ExternalTokens newExternalTokens) {
        service.createExternalTokens(newExternalTokens);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody ExternalTokens editExternalTokens) {
        service.updateExternalTokens(editExternalTokens);
    }

    @DeleteMapping("/{externalTokensId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int externalTokensId) {
        service.deleteExternalTokens(externalTokensId);
    }
    
    @GetMapping("/findbyuser/{users}")
    @ResponseStatus(HttpStatus.OK)
    public ExternalTokens get(@PathVariable("users") Users users) {
        return service.findByUserId(users);
    }
}
