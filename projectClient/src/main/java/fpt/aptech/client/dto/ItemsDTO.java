package fpt.aptech.client.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 * ItemsDTO class with validation annotations
 * 
 * @author Manh_Chien
 */
public class ItemsDTO {
    private int item_id;
    
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    @NotEmpty(message = "Name is required")
    private String name;

    @Size(max = 100, message = "Brand cannot be longer than 100 characters")
    @NotEmpty(message = "Brand is required")
    private String brand;

    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be 0 or greater")
    private int stock;

    @NotEmpty(message = "Type is required")
    private String type;
    public MultipartFile image;

    public boolean is_visible;

    public Date created_dt;

    public ItemsDTO() {
    }

    public ItemsDTO(int item_id) {
        this.item_id = item_id;
    }

    public ItemsDTO(int item_id, String name, String brand, String description, BigDecimal price, int stock, String type, MultipartFile image, boolean is_visible, Date created_dt) {
        this.item_id = item_id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.image = image;
        this.is_visible = is_visible;
        this.created_dt = created_dt;
    }

    public ItemsDTO(String name, String brand, String description, BigDecimal price, int stock, String type, MultipartFile image, boolean is_visible, Date created_dt) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.image = image;
        this.is_visible = is_visible;
        this.created_dt = created_dt;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public Date getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(Date created_dt) {
        this.created_dt = created_dt;
    }
}
