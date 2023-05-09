package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Article;
import com.challenge.digitaldayapp.domain.Client;
import com.challenge.digitaldayapp.domain.Vente;
import com.challenge.digitaldayapp.service.dto.ArticleDTO;
import com.challenge.digitaldayapp.service.dto.ClientDTO;
import com.challenge.digitaldayapp.service.dto.VenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vente} and its DTO {@link VenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface VenteMapper extends EntityMapper<VenteDTO, Vente> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientNom")
    @Mapping(target = "article", source = "article", qualifiedByName = "articleNom")
    VenteDTO toDto(Vente s);

    @Named("clientNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    ClientDTO toDtoClientNom(Client client);

    @Named("articleNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    ArticleDTO toDtoArticleNom(Article article);
}
