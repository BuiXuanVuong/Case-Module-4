package com.mvc.service;

import com.mvc.dto.NewDTO;
import com.mvc.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService{
        UserEntity findByUsername(String username);


}
