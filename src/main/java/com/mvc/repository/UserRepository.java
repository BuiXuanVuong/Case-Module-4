package com.mvc.repository;

import com.mvc.entity.NewEntity;
import com.mvc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);
    UserEntity findOneById(long id);

    List<UserEntity> findAllByUserNameContaining(String name);
}
