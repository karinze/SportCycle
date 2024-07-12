/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Items;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Manh_Chien
 */
public interface IItemService {

    public List<Items> findAll();

    public void createItem(Items items);

    public Items findOne(int itemId);

    public void updateItem(Items items);

    public void deleteItem(int itemId);
    
    public List<Items> search(String name,int pageNumber,int pageSize);
    
    public List<Items> page(int pageNumber,int pageSize);
}
