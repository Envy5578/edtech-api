package com.example.focus_group.auth.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.focus_group.auth.entities.UserEntity;





@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @SuppressWarnings("null")
    Optional<UserEntity> findById(Long id);
    Optional<Boolean> existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    
    

}
