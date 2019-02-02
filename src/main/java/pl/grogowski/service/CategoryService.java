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

    public void addCategory(String userEmail, Category newCategory) {
        newCategory.setUser(userService.getUserByEmail(userEmail));
        categoryRepository.save(newCategory);
        for (LocalDate date:recordService.getExistingRecordsDates(userEmail)) {
            Record newRecord = new Record();
            newRecord.setUser(userService.getUserByEmail(userEmail));
            newRecord.setCategory(newCategory);
            newRecord.setBudgetedAmount(new BigDecimal(0));
            newRecord.setDate(date);
            recordService.addRecord(newRecord);
        }
    }

    public void editCategory(String categoryId, String newName) {
        Category toBeEdited = categoryRepository.findOne(Long.parseLong(categoryId));
        toBeEdited.setName(newName);
        categoryRepository.save(toBeEdited);
    }

    public boolean userCategoriesContainCategoryWithGivenName(String categoryName, String userEmail) {
        List<Category> categories = getCategoriesForUser(userEmail);
        for (Category c:categories) {
            if(categoryName.equals(c.getName())) {
                return true;
            }
        }
        if (categoryName.equals("Income")) {
            return true;
        }
        return false;
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findOne(Long.parseLong(id));
    }

    public List<String> getCategoriesNamesForUser(String userEmail) {
        List<String> categoriesNames = new ArrayList<>();
        for (Category c: getCategoriesForUser(userEmail)) {
            categoriesNames.add(c.getName());
        }
        return categoriesNames;
    }
}
