package uz.pdp.ecommer.service;

import org.springframework.stereotype.Service;
import uz.pdp.ecommer.dto.ProductSaveDTO;
import uz.pdp.ecommer.entity.Attachment;
import uz.pdp.ecommer.entity.Category;
import uz.pdp.ecommer.entity.Product;
import uz.pdp.ecommer.repo.AttachmentRepository;
import uz.pdp.ecommer.repo.CategoryRepository;
import uz.pdp.ecommer.repo.ProductRepository;

import java.util.List;

@Service
public class ProductService {


    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(AttachmentRepository attachmentRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findProductsByCategory(categoryId);
    }

    public List<Product> getProductsByIds(List<Integer> products) {
        return productRepository.findAllById(products);
    }

    public void saveOrUpdate(ProductSaveDTO productSaveDTO) {
        if (productSaveDTO.getProductId() == 0 || productSaveDTO.getCategoryId() == null) {
            Attachment attachment = attachmentRepository.findById(productSaveDTO.getAttachmentId()).orElse(null);
            Category category = categoryRepository.findById(productSaveDTO.getCategoryId()).orElse(null);
            Product product = new Product(productSaveDTO.getName(), productSaveDTO.getPrice(), category, attachment);
            productRepository.save(product);
        } else {
            Product product = productRepository.findById(productSaveDTO.getProductId()).orElse(null);
            Attachment attachment = attachmentRepository.findById(productSaveDTO.getAttachmentId()).orElse(null);
            Category category = categoryRepository.findById(productSaveDTO.getCategoryId()).orElse(null);
            product.setCategory(category);
            product.setAttachment(attachment);
            product.setName(productSaveDTO.getName());
            product.setPrice(productSaveDTO.getPrice());
            productRepository.save(product);
        }
    }

    public void deleteById(int productId) {
        productRepository.deleteById(productId);
    }

}
