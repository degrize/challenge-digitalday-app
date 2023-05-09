package com.challenge.digitaldayapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieArticleMapperTest {

    private CategorieArticleMapper categorieArticleMapper;

    @BeforeEach
    public void setUp() {
        categorieArticleMapper = new CategorieArticleMapperImpl();
    }
}
