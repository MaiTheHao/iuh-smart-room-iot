package com.example.smartroom.modules.language.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartroom.modules.language.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long>{
    public Optional<Language> findByCode(String code);
}
