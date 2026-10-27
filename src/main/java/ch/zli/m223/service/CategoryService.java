package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Category;
import ch.zli.m223.repository.CategoryRepository;

@ApplicationScoped
public class CategoryService {
    @Inject
    private CategoryRepository categoryRepository;

    @Transactional // needed for all "changing" operators
    public Category createCategory(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    public List<Category> findAll() {
        return categoryRepository.listAll();
    }

    @Transactional 
    public boolean deleteCategory(Long id) {
        return categoryRepository.deleteById(id);
    }

    @Transactional 
    public Category updateCategory(Long id, Category category) {
        Category chosenCategory = categoryRepository.findById(id);
        chosenCategory.setTitle(category.getTitle());
        return chosenCategory;
    }
}