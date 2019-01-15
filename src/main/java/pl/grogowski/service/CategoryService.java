package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.Category;
import pl.grogowski.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;

    public List<Category> getCategoriesForUser(String userEmail) {
        List<Category> categories = categoryRepository.findByUser(userService.getUserByEmail(userEmail));
        if (categories.isEmpty()) {
            createCategoriesForNewUser(userEmail);
            categories = categoryRepository.findByUser(userService.getUserByEmail(userEmail));
        }
        return categories;
    }

    private void createCategoriesForNewUser(String userEmail) {
        List<Category> categories = categoryRepository.findByUser(null);
        List<Category> toBePersisted = new ArrayList<>();
        for (Category c: categories) {
            Category newCategory = new Category();
            newCategory.setName(c.getName());
            newCategory.setUser(userService.getUserByEmail(userEmail));
            toBePersisted.add(newCategory);
        }
        categoryRepository.save(toBePersisted);
    }
}
