/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package fpt.aptech.project.repository;

import fpt.aptech.project.entities.Items;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Manh_Chien
 */
public interface ItemRepository extends JpaRepository<Items, Integer> {

    @Query("Select e From Items e Where e.name LIKE :name")
    Page<Items> searchByNamePage(@Param("name") String name, Pageable pageable);

    @Query("Select e From Items e Where e.name LIKE :name")
    List<Items> searchByName(@Param("name") String name);

    @Query("Select e From Items e Where e.item_id = :item_id")
    List<Items> searchByid(@Param("item_id") int item_id);

    @Query("SELECT i FROM Items i WHERE i.price BETWEEN :minPrice AND :maxPrice")
    Page<Items> filterByPrice(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT i FROM Items i JOIN i.Blist bp WHERE "
            + "(:name IS NULL OR i.name LIKE %:name%) AND "
            + "(:brand IS NULL OR i.brand LIKE %:brand%) AND "
            + "(:type IS NULL OR i.type LIKE %:type%) AND "
            + "(:minPrice IS NULL OR i.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR i.price <= :maxPrice) AND "
            + "(:bikeSize IS NULL OR bp.bike_size LIKE %:bikeSize%) AND "
            + "(:bikeWheelSize IS NULL OR bp.bike_wheel_size LIKE %:bikeWheelSize%) AND "
            + "(:bikeColor IS NULL OR bp.bike_color LIKE %:bikeColor%) AND "
            + "(:bikeMaterial IS NULL OR bp.bike_material LIKE %:bikeMaterial%) AND "
            + "(:bikeBrakeType IS NULL OR bp.bike_brake_type LIKE %:bikeBrakeType%) AND "
            + "i.is_visible = true")
    Page<Items> filterByBikeProperties(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("type") String type,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("bikeSize") String bikeSize,
            @Param("bikeWheelSize") String bikeWheelSize,
            @Param("bikeColor") String bikeColor,
            @Param("bikeMaterial") String bikeMaterial,
            @Param("bikeBrakeType") String bikeBrakeType,
            Pageable pageable);

    @Query("SELECT i FROM Items i WHERE i.price BETWEEN :minPrice AND :maxPrice")
    List<Items> searchByPrice(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT i FROM Items i JOIN i.Blist bp WHERE "
            + "(:name IS NULL OR i.name LIKE %:name%) AND "
            + "(:brand IS NULL OR i.brand LIKE %:brand%) AND "
            + "(:type IS NULL OR i.type LIKE %:type%) AND "
            + "(:minPrice IS NULL OR i.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR i.price <= :maxPrice) AND "
            + "(:bikeSize IS NULL OR bp.bike_size LIKE %:bikeSize%) AND "
            + "(:bikeWheelSize IS NULL OR bp.bike_wheel_size LIKE %:bikeWheelSize%) AND "
            + "(:bikeColor IS NULL OR bp.bike_color LIKE %:bikeColor%) AND "
            + "(:bikeMaterial IS NULL OR bp.bike_material LIKE %:bikeMaterial%) AND "
            + "(:bikeBrakeType IS NULL OR bp.bike_brake_type LIKE %:bikeBrakeType%) AND "
            + "i.is_visible = true")
    List<Items> searchByBikeProperties(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("type") String type,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("bikeSize") String bikeSize,
            @Param("bikeWheelSize") String bikeWheelSize,
            @Param("bikeColor") String bikeColor,
            @Param("bikeMaterial") String bikeMaterial,
            @Param("bikeBrakeType") String bikeBrakeType
    );

}
