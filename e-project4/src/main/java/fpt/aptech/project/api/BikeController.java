/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.inteface.IBikeService;
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
@RequestMapping("/api/bikes")
public class BikeController {

    @Autowired
    IBikeService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> list() {
        return service.findAll();
    }

    @GetMapping("/{bikeId}")
    @ResponseStatus(HttpStatus.OK)
    public Items get(@PathVariable("bikeId") int bikeId) {
        return service.findOne(bikeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody Items newBike) {
        service.createBike(newBike);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody Items editBike) {
        service.updateBike(editBike);
    }

    @DeleteMapping("/{bikeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int bikeId) {
        service.deleteBike(bikeId);
    }
}
