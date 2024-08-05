/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.inteface.IBikeRentalsService;
import fpt.aptech.project.inteface.IUserService;
import java.util.List;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/bikerentals/")
public class BikeRentalsController {
    @Autowired
    IBikeRentalsService service;
    
    @Autowired
    IUserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeRentals> list() {
        return service.findAll();
    }

    @GetMapping("/{bikeRentalsId}")
    @ResponseStatus(HttpStatus.OK)
    public BikeRentals get(@PathVariable("bikeRentalsId") int bikeRentalsId) {
        return service.findOne(bikeRentalsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BikeRentals post(@RequestBody BikeRentals newBikeRentals) {
        return service.createBikeRentals(newBikeRentals);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody BikeRentals editBikeRentals) {
        service.updateBikeRentals(editBikeRentals);
    }

    @DeleteMapping("/{bikeRentalsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int bikeRentalsId) {
        service.deleteBikeRentals(bikeRentalsId);
    }
    
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeRentals> list(@PathVariable("userId") UUID userId) {
        Users user = new Users();
        user.setUser_id(userId);
        return service.findUsers(user);
    }
    
    @GetMapping("/userpage/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeRentals> list(@PathVariable("userId") UUID userId,@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        Users user = userService.findOne(userId);
        return service.pages(user,validPageNumber, validPageSize);
    }
    
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeRentals> lists(@PathVariable("userId") UUID userId) {
        Users user = new Users();
        user.setUser_id(userId);
        return service.findUser(user);
    }
    
    @GetMapping("/userpages/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeRentals> lists(@PathVariable("userId") UUID userId,@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        Users user = userService.findOne(userId);
        return service.page(user,validPageNumber, validPageSize);
    }
}
