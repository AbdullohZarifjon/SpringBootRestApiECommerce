package uz.pdp.ecommer.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommer.dto.ProductSaveDTO;
import uz.pdp.ecommer.entity.Product;
import uz.pdp.ecommer.repo.AttachmentRepository;
import uz.pdp.ecommer.repo.CategoryRepository;
import uz.pdp.ecommer.repo.ProductRepository;
import uz.pdp.ecommer.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public void save(@RequestBody ProductSaveDTO productSaveDTO) {
        productService.saveOrUpdate(productSaveDTO);
    }

    @GetMapping("/all/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/ids")
    public List<Product> getProductsByIds(@RequestParam List<Integer> products) {
        return productService.getProductsByIds(products);
    }

    @GetMapping("/id/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productService.getProductById(productId);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable int productId) {
        productService.deleteById(productId);
    }


}

