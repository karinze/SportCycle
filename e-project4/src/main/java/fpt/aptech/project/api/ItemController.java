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

/**
 *
 * @author Manh_Chien
 */
@RestController
@RequestMapping("/api/items/")
public class ItemController {

    @Autowired
    IItemService service;

    @GetMapping("")
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
    public void post(@RequestBody Items newItem) {
        service.createItem(newItem);
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
}
