package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Article;
import com.challenge.digitaldayapp.domain.CategorieArticle;
import com.challenge.digitaldayapp.service.dto.ArticleDTO;
import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "categorieArticles", source = "categorieArticles", qualifiedByName = "categorieArticleNomSet")
    ArticleDTO toDto(Article s);

    @Mapping(target = "removeCategorieArticle", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    @Named("categorieArticleNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    CategorieArticleDTO toDtoCategorieArticleNom(CategorieArticle categorieArticle);

    @Named("categorieArticleNomSet")
    default Set<CategorieArticleDTO> toDtoCategorieArticleNomSet(Set<CategorieArticle> categorieArticle) {
        return categorieArticle.stream().map(this::toDtoCategorieArticleNom).collect(Collectors.toSet());
    }
}
