package pl.grogowski.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.grogowski.model.CashFlow;
import pl.grogowski.model.Category;
import pl.grogowski.repository.CashFlowRepository;
import pl.grogowski.repository.CategoryRepository;

public class CategoryConverter implements Converter<String, Category> {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category convert(String s) {
        return categoryRepository.findOne(Long.parseLong(s));
    }
}
