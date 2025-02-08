package uz.pdp.ecommer.service;

import org.springframework.stereotype.Service;
import uz.pdp.ecommer.entity.Category;
import uz.pdp.ecommer.repo.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public void saveOrUpdate(Category category) {
        if (category.getId() != 0 && category.getName() != null && !category.getName().isEmpty()) {
            categoryRepository.save(category);
        } else {
            if (category.getName() != null && !category.getName().isEmpty()) {
                categoryRepository.save(category);
            }
        }
    }

    public void deleteCategoryById(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }


}
