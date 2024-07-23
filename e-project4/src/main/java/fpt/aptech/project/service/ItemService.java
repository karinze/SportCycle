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
import java.math.BigDecimal;
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
public class ItemService implements IItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Items> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Items createItem(Items items) {
        return itemRepository.save(items);
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
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<Items> pageItems = this.itemRepository.findAll(pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
        }
    }

    @Override
    public List<Items> searchpage(String name, int pageNumber, int pageSize) {
        try {
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<Items> pageItems = this.itemRepository.searchByNamePage('%' + name + '%', pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
        }
    }

    @Override
    public List<Items> search(String name) {
        return itemRepository.searchByName('%' + name + '%');
    }

    @Override
    public List<Items> searchbyid(int id) {
        return itemRepository.searchByid(id);
    }

    @Override
    public List<Items> filterItems(String name, String brand,String type , BigDecimal minPrice, BigDecimal maxPrice,
            String bikeSize, String bikeWheelSize, String bikeColor, String bikeMaterial,
            String bikeBrakeType, int pageNumber, int pageSize) {
        try {
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<Items> pageItems = this.itemRepository.filterByBikeProperties(
                    name, brand,type ,minPrice, maxPrice, bikeSize, bikeWheelSize, bikeColor, bikeMaterial, bikeBrakeType, pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch filtered items: " + ex.getMessage());
        }
    }
    
    @Override
    public List<Items> searchItems(String name, String brand,String type , BigDecimal minPrice, BigDecimal maxPrice,
            String bikeSize, String bikeWheelSize, String bikeColor, String bikeMaterial,
            String bikeBrakeType) {
        return itemRepository.searchByBikeProperties(name, brand, type, minPrice, maxPrice, bikeSize, bikeWheelSize, bikeColor, bikeMaterial, bikeBrakeType);
    }
    
    

}
