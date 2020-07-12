package store.service;


import store.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    void initCategories();

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findCategoryById(String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel);

    CategoryServiceModel deleteCategory(String id);
}