package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Article;
import com.challenge.digitaldayapp.domain.Commander;
import com.challenge.digitaldayapp.domain.Fournisseur;
import com.challenge.digitaldayapp.service.dto.ArticleDTO;
import com.challenge.digitaldayapp.service.dto.CommanderDTO;
import com.challenge.digitaldayapp.service.dto.FournisseurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commander} and its DTO {@link CommanderDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommanderMapper extends EntityMapper<CommanderDTO, Commander> {
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    CommanderDTO toDto(Commander s);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);
}
