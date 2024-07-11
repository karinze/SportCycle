/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.project.inteface;

import fpt.aptech.project.entities.Items;
import java.util.List;

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
}
