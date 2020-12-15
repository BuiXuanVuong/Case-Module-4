package com.mvc.controller;


import com.mvc.entity.CategoryEntity;
import com.mvc.service.ICategoryService;
import com.mvc.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller(value = "categoryControllerOfAdmin")
public class AdminCategoryController {

    @Autowired
    private ICategoryService categoryService;


    @Autowired
    private INewService newService;

    @GetMapping(value = "/quan-tri/the-loai/danh-sach")
    public ModelAndView showListCategory(@RequestParam("search")Optional<String> search, Pageable pageable) {
        ModelAndView mav = new ModelAndView("admin/category/list");
        if (search.isPresent()) {
            Page<CategoryEntity> categories;
            categories = categoryService.findAllByNameContaining(search.get(), pageable);
//            mav.addObject("addCategory", new CategoryEntity());
            mav.addObject("categories", categories);
        } else {
            List<CategoryEntity> categories;
            categories = categoryService.listCategory();
            mav.addObject("message", "Ko thay ket qua nao");
//            mav.addObject("addCategory", new CategoryEntity());
            mav.addObject("categories", categories);
        }

        return mav;
    }


    @GetMapping(value = "/quan-tri/the-loai/them-moi")
    public ModelAndView showCreateForm(){
        ModelAndView mav = new ModelAndView("admin/category/create");
        mav.addObject("addCategory", new CategoryEntity());
        return mav;
    }

    @PostMapping(value = "/quan-tri/the-loai/them-moi")
    public ModelAndView createCategory(@ModelAttribute("addCategory") CategoryEntity category) {
        categoryService.save(category);
        ModelAndView mav = new ModelAndView("admin/category/create");
        mav.addObject("addCategory", new CategoryEntity());
//        mav.addObject("categories", categoryService.listCategory());
        mav.addObject("message", "Them thanh cong");
        return mav;
    }


    @GetMapping(value = "/quan-tri/the-loai/chinh-sua/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id){
        CategoryEntity currentCategory = categoryService.findById(id);

        if(currentCategory != null) {
            ModelAndView mav = new ModelAndView("admin/category/edit");
            mav.addObject("category", currentCategory);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("/error.404");
            return mav;
        }
    }

    @PostMapping("/quan-tri/the-loai/chinh-sua/{id}")
    public ModelAndView updateCategory(@PathVariable Long id, @ModelAttribute("category") CategoryEntity newCategory){
        CategoryEntity category = categoryService.findById(id);
        category.setCode(newCategory.getCode());
        category.setName(newCategory.getName());
        categoryService.save(category);
        List<CategoryEntity> categories = categoryService.listCategory();
        ModelAndView mav = new ModelAndView("admin/category/list");
        mav.addObject("categories", categories);
        mav.addObject("message", "Sua thanh cong");
        return mav;
    }

    @GetMapping("/quan-tri/the-loai/xoa/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id){
        CategoryEntity currentCategory = categoryService.findById(id);
        if(currentCategory != null) {
            ModelAndView mav = new ModelAndView("admin/category/delete");
            mav.addObject("category", currentCategory);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("/error.404");
            return mav;
        }
    }

    @PostMapping("/quan-tri/the-loai/xoa/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id){
        CategoryEntity category = categoryService.findById(id);
        categoryService.remove(category);
        List<CategoryEntity> categories = categoryService.listCategory();
        ModelAndView mav = new ModelAndView("admin/category/list");
        mav.addObject("message", "Xoa thanh cong");
        mav.addObject("categories", categories);
        return mav;
    }
}
