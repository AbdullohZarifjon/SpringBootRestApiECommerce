package uz.pdp.ecommer.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommer.entity.Category;
import uz.pdp.ecommer.entity.Product;
import uz.pdp.ecommer.repo.CategoryRepository;
import uz.pdp.ecommer.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public void saveCategory(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
    }

    @GetMapping("/id/{categoryId}")
    public Category getProductById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable int categoryId) {
        categoryService.deleteCategoryById(categoryId);
    }
}
