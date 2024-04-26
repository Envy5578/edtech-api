package com.example.focus_group.auth.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.focus_group.auth.entities.TokenEntity;


@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

}
