/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.BikeProperties;
import fpt.aptech.project.entities.Items;
import fpt.aptech.project.inteface.IBikePropertiesService;
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
@RequestMapping("/api/bikeproperties/")
public class BikePropertiesController {

    @Autowired
    IBikePropertiesService service;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeProperties> list() {
        return service.findAll();
    }

    @GetMapping("/{bikePropertiesId}")
    @ResponseStatus(HttpStatus.OK)
    public BikeProperties get(@PathVariable("bikePropertiesId") int bikePropertiesId) {
        return service.findOne(bikePropertiesId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody BikeProperties newBikeProperties) {
        service.createBikeProperties(newBikeProperties);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody BikeProperties editBikeProperties) {
        service.updateBikeProperties(editBikeProperties);
    }

    @DeleteMapping("/{bikePropertiesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int bikePropertiesId) {
        service.deleteBikeProperties(bikePropertiesId);
    }

    @GetMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public BikeProperties findByItemId(@PathVariable("itemId") int itemId) {
        return service.findByItemId(itemId);
    }
}
