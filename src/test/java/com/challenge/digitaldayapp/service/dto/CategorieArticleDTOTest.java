package com.challenge.digitaldayapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.digitaldayapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieArticleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieArticleDTO.class);
        CategorieArticleDTO categorieArticleDTO1 = new CategorieArticleDTO();
        categorieArticleDTO1.setId(1L);
        CategorieArticleDTO categorieArticleDTO2 = new CategorieArticleDTO();
        assertThat(categorieArticleDTO1).isNotEqualTo(categorieArticleDTO2);
        categorieArticleDTO2.setId(categorieArticleDTO1.getId());
        assertThat(categorieArticleDTO1).isEqualTo(categorieArticleDTO2);
        categorieArticleDTO2.setId(2L);
        assertThat(categorieArticleDTO1).isNotEqualTo(categorieArticleDTO2);
        categorieArticleDTO1.setId(null);
        assertThat(categorieArticleDTO1).isNotEqualTo(categorieArticleDTO2);
    }
}
