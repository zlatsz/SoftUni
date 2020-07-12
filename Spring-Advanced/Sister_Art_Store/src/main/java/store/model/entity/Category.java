package store.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private MainCategory mainCategory;
    private String name;

    public Category() {
    }

    public Category(MainCategory categoryName, String name) {
        this.mainCategory = categoryName;
        this.name = name;
    }


    @Enumerated(value = EnumType.STRING)
    public MainCategory getMainCategory() {
        return this.mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Column(name = "category_name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
