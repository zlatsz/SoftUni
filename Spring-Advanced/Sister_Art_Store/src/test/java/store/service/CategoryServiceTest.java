package store.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import store.error.CategoryNotFoundException;
import store.model.entity.Category;
import store.model.service.CategoryServiceModel;
import store.repository.CategoryRepository;
import store.service.impl.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    CategoryServiceImpl categoryService;
    @Mock
    CategoryRepository mockCategoryRepository;
    @Mock
    ModelMapper modelMapper;
    Category category;
    CategoryServiceModel model;

    @Before
    public void initTests() {
        category = mock(Category.class);
        model = mock(CategoryServiceModel.class);
    }

    @Test
    public void findById_WhenCategoryExist_ShouldCorrect() {

        Mockito.when(mockCategoryRepository.findById("id"))
                .thenReturn(Optional.of(category));
        Mockito.when(modelMapper.map(category, CategoryServiceModel.class))
                .thenReturn(model);

        CategoryServiceModel result = categoryService.findCategoryById("id");
        Mockito.verify(mockCategoryRepository).findById("id");
        Mockito.verify(modelMapper).map(category, CategoryServiceModel.class);
        assertEquals(model, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_nullInput_throwsIllegalArgumentException() {
        Mockito.when(mockCategoryRepository.findById(null))
                .thenThrow(new IllegalArgumentException());

        categoryService.findCategoryById(null);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findCategoryById_invalidId_throwsCategoryNotFoundException() {
        Mockito.when(mockCategoryRepository.findById("id"))
                .thenReturn(Optional.empty());

        categoryService.findCategoryById("id");
    }

    @Test
    public void editCategory_validInputData_correctMethodsAndArgumentsUsed() {

        Mockito.when(model.getName()).thenReturn("name");

        Mockito.when(mockCategoryRepository.findById(eq("id"))).thenReturn(Optional.of(category));
        Mockito.when(mockCategoryRepository.saveAndFlush(category)).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryServiceModel.class)).thenReturn(model);

        CategoryServiceModel result = categoryService.editCategory("id", model);

        Mockito.verify(mockCategoryRepository).findById("id");
        Mockito.verify(category).setName("name");

        Mockito.verify(mockCategoryRepository).saveAndFlush(category);
        Mockito.verify(modelMapper).map(category, CategoryServiceModel.class);
        assertEquals(model, result);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void editCategory_invalidId_throwsCategoryNotFoundException() {
        categoryService.editCategory("NoValidId", model);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void editCategory_NULL_throwsCategoryNotFoundException() {
        categoryService.editCategory(null, null);
    }

    @Test
    public void extractAllCategories_WhenCategoriesExist_ShouldWork() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Mockito.when(mockCategoryRepository.findAll()).thenReturn(categories);
        int result = categoryService.findAllCategories().size();
        assertEquals(categories.size(), result);
    }

    @Test
    public void extractAllCategories_WhenNoCategories_ShouldWork() {
        Mockito.when(mockCategoryRepository.findAll()).thenReturn(List.of());
        int result = mockCategoryRepository.findAll().size();
        assertEquals(0, result);
    }

}