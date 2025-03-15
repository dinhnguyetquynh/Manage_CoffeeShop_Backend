package com.example.manage_coffeeshop_dataservice.service.faker;

import com.example.manage_coffeeshop_dataservice.model.Category;
import com.example.manage_coffeeshop_dataservice.repository.CategoryRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class CategoryFakerService {
    private final Faker faker;
    private final CategoryRepository categoryRepository;

    public CategoryFakerService(Faker faker, CategoryRepository categoryRepository) {
        this.faker = faker;
        this.categoryRepository = categoryRepository;
    }

    public void generateFakeCategories(int count) {
        for (int i = 0; i < count; i++) {
            Category category = new Category();
            category.setCategoryName(faker.food().dish());
            category.setCategoryDescription(faker.lorem().sentence());
            categoryRepository.save(category);
        }
    }
}

//@Service
//public class CategoryFakerService {
//    private final Faker faker;
//    private final CategoryRepository categoryRepository;
//
//    public CategoryFakerService(Faker faker, CategoryRepository categoryRepository) {
//        this.faker = faker;
//        this.categoryRepository = categoryRepository;
//    }
//
//    public void generateFakeCategories(int count) {
//        String[] categoryNames = {
//                "Cà phê", "Trà", "Nước ép", "Sinh tố", "Bánh ngọt", "Đồ ăn nhẹ"
//        };
//        String[] categoryDescriptions = {
//                "Các loại cà phê truyền thống và đặc biệt",
//                "Trà các loại, trà sữa, trà trái cây",
//                "Nước ép trái cây tươi mát",
//                "Sinh tố trái cây nhiệt đới",
//                "Bánh ngọt tự làm tại quán",
//                "Snack và đồ ăn vặt"
//        };
//
//        for (int i = 0; i < count; i++) {
//            Category category = new Category();
//            category.setCategoryName(faker.options().nextElement(categoryNames));
//            category.setCategoryDescription(faker.options().nextElement(categoryDescriptions));
//            categoryRepository.save(category);
//        }
//    }
//}
