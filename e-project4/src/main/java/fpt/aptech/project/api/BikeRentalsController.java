/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BikeRentals;
import fpt.aptech.project.inteface.IBikeRentalsService;
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
@RequestMapping("/api/bikerentals/")
public class BikeRentalsController {
    @Autowired
    IBikeRentalsService service;

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
    public void post(@RequestBody BikeRentals newBikeRentals) {
        service.createBikeRentals(newBikeRentals);
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
}
