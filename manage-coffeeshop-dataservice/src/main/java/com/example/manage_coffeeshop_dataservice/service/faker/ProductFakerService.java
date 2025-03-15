package com.example.manage_coffeeshop_dataservice.service.faker;

import com.example.manage_coffeeshop_dataservice.model.Category;
import com.example.manage_coffeeshop_dataservice.model.Product;
import com.example.manage_coffeeshop_dataservice.repository.CategoryRepository;
import com.example.manage_coffeeshop_dataservice.repository.ProductRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFakerService {
    private final Faker faker;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductFakerService(Faker faker, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.faker = faker;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void generateFakeProducts(int count) {
        List<Category> categories = categoryRepository.findAll();

        for (int i = 0; i < count; i++) {
            Product product = new Product();
            product.setProductName(faker.food().dish() + " " + faker.food().ingredient());
            product.setProductPrice(faker.number().randomDouble(2, 1, 20)); // Giá từ $1 đến $20
            product.setProductInventoryQuantity(faker.number().numberBetween(10, 100));

            // Chọn ngẫu nhiên một Category
            product.setCategory(categories.get(faker.number().numberBetween(0, categories.size())));

            productRepository.save(product);
        }
    }
}

//@Service
//public class ProductFakerService {
//    private final Faker faker;
//    private final ProductRepository productRepository;
//    private final CategoryRepository categoryRepository;
//
//    public ProductFakerService(Faker faker, ProductRepository productRepository, CategoryRepository categoryRepository) {
//        this.faker = faker;
//        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
//    }
//
//    public void generateFakeProducts(int count) {
//        List<Category> categories = categoryRepository.findAll();
//        String[] productNames = {
//                "Cà phê đen đá", "Cà phê sữa nóng", "Trà đào cam sả",
//                "Trà sữa trân châu đường đen", "Nước ép cam tươi",
//                "Sinh tố bơ dừa", "Bánh mì sandwich", "Bánh su kem"
//        };
//
//        for (int i = 0; i < count; i++) {
//            Product product = new Product();
//            product.setProductName(faker.options().nextElement(productNames));
//            product.setProductPrice(faker.number().randomDouble(2, 20000, 60000));
//            product.setProductInventoryQuantity(faker.number().numberBetween(10, 100));
////            product.setProductImg("/images/" + faker.file().fileName("png"));
//            product.setCategory(categories.get(faker.number().numberBetween(0, categories.size())));
//            productRepository.save(product);
//        }
//    }
//}
//
