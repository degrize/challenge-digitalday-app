package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.CategorieArticle;
import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieArticle} and its DTO {@link CategorieArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieArticleMapper extends EntityMapper<CategorieArticleDTO, CategorieArticle> {}
