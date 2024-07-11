/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Items;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.aptech.project.inteface.IItemService;
import fpt.aptech.project.repository.ItemRepository;

/**
 *
 * @author Manh_Chien
 */
@Service
public class ItemService implements IItemService{
    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Items> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void createItem(Items items) {
        itemRepository.save(items);
    }

    @Override
    public Items findOne(int itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Override
    public void updateItem(Items items) {
        itemRepository.save(items);
    }

    @Override
    public void deleteItem(int itemId) {
        itemRepository.deleteById(itemId);
    }
    
}
