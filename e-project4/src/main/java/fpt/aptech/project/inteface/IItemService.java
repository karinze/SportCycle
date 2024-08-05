/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Items;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Manh_Chien
 */
public interface IItemService {

    public List<Items> findAll();

    public Items createItem(Items items);

    public Items findOne(int itemId);

    public void updateItem(Items items);

    public void deleteItem(int itemId);
    
    public List<Items> searchpage(String name,int pageNumber,int pageSize);
    
    public List<Items> search(String name);
    
    public List<Items> page(int pageNumber,int pageSize);
    
    public List<Items> searchbyid(int id);
    
    public List<Items> filterItems(String name, String brand,String type ,BigDecimal minPrice, BigDecimal maxPrice,
            String bikeSize, String bikeWheelSize, String bikeColor, String bikeMaterial,
            String bikeBrakeType, int pageNumber, int pageSize);
    
    public List<Items> searchItems(String name, String brand,String type , BigDecimal minPrice, BigDecimal maxPrice,
            String bikeSize, String bikeWheelSize, String bikeColor, String bikeMaterial,
            String bikeBrakeType);
    
    
    public List<Items> findTop10NewestItems();
}
