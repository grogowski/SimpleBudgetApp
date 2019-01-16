package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.CategoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;

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

    public void addCategory(String userEmail, String newCategoryName) {
        Category newCategory = new Category();
        newCategory.setUser(userService.getUserByEmail(userEmail));
        newCategory.setName(newCategoryName);
        categoryRepository.save(newCategory);
        for (LocalDate date:recordService.getExistingRecordsDates(userEmail)) {
            Record newRecord = new Record();
            newRecord.setUser(userService.getUserByEmail(userEmail));
            newRecord.setCategory(newCategory);
            newRecord.setBudgetedAmount(new BigDecimal(0));
            newRecord.setDate(date);
        }
    }

    public void editCategory(String categoryId, String newName) {
        Category toBeEdited = categoryRepository.findOne(Long.parseLong(categoryId));
        toBeEdited.setName(newName);
        categoryRepository.save(toBeEdited);
    }
}
