package store.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductAddBindingModel {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private MultipartFile image;
    private List<String> categories;

    public ProductAddBindingModel() {
    }

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 40, message = "Jewellery name must be between 3 and 40 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    @Size(min = 15, max = 100, message = "Description must be between 15 and 100 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DecimalMin(value = "0", message = "Invalid price!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Min(value = 0, message = "Invalid quantity!")
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @NotNull
    @NotEmpty
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @NotNull
    @NotEmpty
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
