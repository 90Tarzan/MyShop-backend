package com.clever.myproductapi.services.Impl;

import com.clever.myproductapi.DTO.CategoryDto;

import com.clever.myproductapi.entities.Category;
import com.clever.myproductapi.exceptions.CategoryException;
import com.clever.myproductapi.repositories.original.CategoryRepository;
import com.clever.myproductapi.responses.CategoryErrorMessages;
import com.clever.myproductapi.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KafkaTemplate<String, com.clever.myproductapi.entities2.Category>kafkaTemplate;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category checkCategory =  categoryRepository.findByName(categoryDto.getName());

        if(checkCategory != null)
            throw new CategoryException(CategoryErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        Category category = mapper.map(categoryDto, Category.class);

        category.setDateOfCreation(new Date());
        category.setDateOfModification(new Date());
        category.setIsDeleted(false);


        Category newCategory = categoryRepository.save(category);
        com.clever.myproductapi.entities2.Category category1 = new com.clever.myproductapi.entities2.Category();
        BeanUtils.copyProperties(newCategory, category1);
        kafkaTemplate.send("prod", category1);

        return mapper.map(newCategory, CategoryDto.class);

    }



    @Override
    public Page<Category>
    getAllCategoriesWithPage(int page, int size) {
        return categoryRepository.findByIsDeletedFalse(PageRequest.of(page,size));
    }

    @Override
    public List<Category> findAllCategoriesNotDeleted() {
        return categoryRepository.findAllProductsNotDeleted();
    }

    @Override
    public CategoryDto getCategory(String name) throws Exception {
        Category category = categoryRepository.findByName(name);

        if (category == null)
            throw new CategoryException(name);

        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }


    @Override
    public CategoryDto getCategoryById(Long id)  {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new CategoryException( "ID: "+id+" "+ CategoryErrorMessages.NO_RECORD_FOUND.getErrorMessage() );
        }

        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category.get(), categoryDto);

        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws Exception {

        Optional<Category> optCategory = categoryRepository.findById(id);
        if (optCategory.isEmpty()) {
            throw new CategoryException( "ID: "+id+" "+ CategoryErrorMessages.NO_RECORD_FOUND.getErrorMessage() );
        }
        Category category = optCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setSlug(categoryDto.getSlug());

        //Todo : Fix date of creation should be fix when updating !
        category.setIsDeleted(categoryDto.getIsDeleted() != null && categoryDto.getIsDeleted());
        //category.setDateOfCreation(categoryDto.getDateOfCreation() == null ?
           //     new Date() : categoryDto.getDateOfCreation());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Africa/Casablanca"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        Date date = calendar.getTime();

        //String formattedDate = formatter.format(date);
        category.setDateOfModification(date);

        Category updatedCategory = categoryRepository.save(category);
        CategoryDto updatedCategoryDto = new CategoryDto();
        BeanUtils.copyProperties(updatedCategory, updatedCategoryDto);
        return updatedCategoryDto;
    }


    @Override
    public void isDeletedCategory(Long id) throws Exception {

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() ->  new CategoryException( "ID: "+id+" "+ CategoryErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

            category.setIsDeleted(true);
            category.setDateOfModification(new Date());

            Category updatedCategory = categoryRepository.save(category);
        mapper.map(updatedCategory, CategoryDto.class);




    }
}
