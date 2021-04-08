package com.example.projetoitisenha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projetoitisenha.entity.PasswordEntity;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

}
