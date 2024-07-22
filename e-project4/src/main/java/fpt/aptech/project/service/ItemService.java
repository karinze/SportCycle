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
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    
    
    @Override
public List<Items> page(int pageNumber, int pageSize) {
    try {
        // Set default page size to 10 if provided page size is less than 1
        int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
        Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
        Page<Items> pageItems = this.itemRepository.findAll(pageable);
        
        List<Items> allItems = pageItems.getContent();
        
        return allItems;
    } catch (Exception ex) {
        // Handle exception (e.g., log it)
        ex.printStackTrace();
        throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
    }
}
    @Override
    public List<Items> searchpage(String name, int pageNumber, int pageSize) {
    try {
        // Set default page size to 10 if provided page size is less than 1
        int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
        Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
        
        Page<Items> pageItems = this.itemRepository.searchByNamePage('%'+name+'%', pageable);
        
        List<Items> allItems = pageItems.getContent();
        
        return allItems;
    } catch (Exception ex) {
        // Handle exception (e.g., log it)
        ex.printStackTrace();
        throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
    }
}

    @Override
    public List<Items> search(String name) {
        return itemRepository.searchByName('%'+name+'%');
    }

    @Override
    public List<Items> searchbyid(int id) {
        return itemRepository.searchByid(id);
    }
    

    

    
    
}
