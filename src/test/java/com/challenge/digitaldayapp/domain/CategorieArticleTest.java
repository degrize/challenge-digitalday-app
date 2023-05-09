package com.challenge.digitaldayapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.digitaldayapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieArticle.class);
        CategorieArticle categorieArticle1 = new CategorieArticle();
        categorieArticle1.setId(1L);
        CategorieArticle categorieArticle2 = new CategorieArticle();
        categorieArticle2.setId(categorieArticle1.getId());
        assertThat(categorieArticle1).isEqualTo(categorieArticle2);
        categorieArticle2.setId(2L);
        assertThat(categorieArticle1).isNotEqualTo(categorieArticle2);
        categorieArticle1.setId(null);
        assertThat(categorieArticle1).isNotEqualTo(categorieArticle2);
    }
}
