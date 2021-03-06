package com.mvc.controller;

import com.mvc.entity.UserEntity;
import com.mvc.entity.UserEntityForm;
import com.mvc.service.IAccountService;
import com.mvc.util.RoleUtil;
import com.mvc.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tai-khoan")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("admin_account");
        List<UserEntity> userEntities = (List<UserEntity>) accountService.findAll();
        mav.addObject("userEntitys", userEntities);
        mav.addObject("message", "Thanh cong");
        if(RoleUtil.checkRole().size() > 0) {
            mav.addObject("fullName", RoleUtil.checkRole().get(0));
            mav.addObject("author", RoleUtil.checkRole().get(1));
            mav.addObject("userId",RoleUtil.checkRole().get(2));
            mav.addObject("image", RoleUtil.checkRole().get(3));
        }
        return mav;
    }

    @GetMapping("/tao-moi")
    public ModelAndView showFormCreate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("sign_up");
        modelAndView.addObject("userEntity", new UserEntityForm());
        return modelAndView;
    }

    @PostMapping("/tao-moi")
    public RedirectView createTask(@ModelAttribute UserEntityForm userEntity,HttpServletRequest request, HttpServletResponse response) {

//
        final List<UserEntity> userEntityList = new LinkedList<>();
        Iterable<UserEntity> iterableUser = accountService.findAll();
        iterableUser.forEach(userEntityList::add);

//
        UserEntity userEntity1 = new UserEntity.UserEntityBuilder(userEntity.getUserName())
                .encrytedPassword(userEntity.getEncrytedPassword())
                .userRole(userEntity.getUserRole())
                .active(userEntity.isActive()).build();
        MultipartFile multipartFile = userEntity.getImage();
        String fileName = multipartFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(userEntity.getImage().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String password = BCrypt.hashpw(userEntity.getEncrytedPassword(), BCrypt.gensalt(10));
        userEntity1.setEncrytedPassword(password);

        if(SecurityUtils.getAuthorities().get(0).equals("ROLE_MANAGER")) {
            userEntity1.setUserRole("ROLE_MANAGER");
        } else {
            userEntity1.setUserRole("ROLE_EMPLOYEE");
        }
        userEntity1.setActive(true);
        userEntity1.setImage(fileName);



        for (UserEntity user: userEntityList) {
            if(userEntity1.getUserName().equals(user.getUserName())){
                RedirectView redirectView = new RedirectView("/tai-khoan/tao-moi?message=1");
                return redirectView;
            }
        }


        accountService.save(userEntity1);
        return new RedirectView("/dang-nhap");

    }

    @GetMapping("/trang-thai/{id}")
    public ModelAndView changeStatus(@PathVariable("id") long id,HttpServletRequest request, HttpServletResponse response) {
            ModelAndView modelAndView = new ModelAndView("admin_home");
            UserEntity userEntity = accountService.findOneById(id);
            if(userEntity.isActive()) {
                userEntity.setActive(false);
            } else {
                userEntity.setActive(true);
            }
            accountService.save(userEntity);
            return modelAndView;
    }

}
