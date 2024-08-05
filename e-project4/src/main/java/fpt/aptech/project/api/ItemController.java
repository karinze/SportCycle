/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package fpt.aptech.project.api;

import fpt.aptech.project.entities.Items;
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
import fpt.aptech.project.inteface.IItemService;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/items/")
public class ItemController {

    @Autowired
    IItemService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Items> list() {
        return service.findAll();
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Items get(@PathVariable("itemId") int itemId) {
        return service.findOne(itemId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Items post(@RequestBody Items newItem) {
        return service.createItem(newItem);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody Items editItem) {
        service.updateItem(editItem);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int itemId) {
        service.deleteItem(itemId);
    }

    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> list(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        return service.page(validPageNumber, validPageSize);
    }

    @GetMapping("/searchp/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> search(@PathVariable("name") String name, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        return service.searchpage(name, validPageNumber, validPageSize);
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> search(@PathVariable("name") String name) {
        // Ensure pageNumber and pageSize are not null

        return service.search(name);
    }

    @GetMapping("/searchid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> search(@PathVariable("id") int id) {
        // Ensure pageNumber and pageSize are not null

        return service.searchbyid(id);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> filter(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "bikeSize", required = false) String bikeSize,
            @RequestParam(value = "bikeWheelSize", required = false) String bikeWheelSize,
            @RequestParam(value = "bikeColor", required = false) String bikeColor,
            @RequestParam(value = "bikeMaterial", required = false) String bikeMaterial,
            @RequestParam(value = "bikeBrakeType", required = false) String bikeBrakeType,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        int validPageNumber = (pageNumber != null) ? pageNumber : 0;
        int validPageSize = (pageSize != null) ? pageSize : 5;
        return service.filterItems(name, brand, type, minPrice, maxPrice, bikeSize, bikeWheelSize, bikeColor, bikeMaterial, bikeBrakeType, validPageNumber, validPageSize);
    }

    public List<Items> searchByType(String type, String name, String brand, int pageNumber, int pageSize) {
        if ("Bike".equalsIgnoreCase(type)) {
            return service.filterItems(name, brand, type, null, null, null, null, null, null, null, pageNumber, pageSize);
        } else if ("Accessory".equalsIgnoreCase(type)) {
            return service.searchpage(name, pageNumber, pageSize);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    @GetMapping("/showfilter")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> showfilter(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "bikeSize", required = false) String bikeSize,
            @RequestParam(value = "bikeWheelSize", required = false) String bikeWheelSize,
            @RequestParam(value = "bikeColor", required = false) String bikeColor,
            @RequestParam(value = "bikeMaterial", required = false) String bikeMaterial,
            @RequestParam(value = "bikeBrakeType", required = false) String bikeBrakeType) {
        return service.searchItems(name, brand, type, minPrice, maxPrice, bikeSize, bikeWheelSize, bikeColor, bikeMaterial, bikeBrakeType);
    }

    public List<Items> searchByType(String type, String name, String brand) {
        if ("Bike".equalsIgnoreCase(type)) {
            return service.searchItems(name, brand, type, null, null, null, null, null, null, null);
        } else if ("Accessory".equalsIgnoreCase(type)) {
            return service.search(name);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    @GetMapping("/top10")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> listTop10() {
        return service.findTop10NewestItems();
    }
}
