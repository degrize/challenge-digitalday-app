package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Fournisseur;
import com.challenge.digitaldayapp.domain.Quartier;
import com.challenge.digitaldayapp.service.dto.FournisseurDTO;
import com.challenge.digitaldayapp.service.dto.QuartierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fournisseur} and its DTO {@link FournisseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, Fournisseur> {
    @Mapping(target = "quartier", source = "quartier", qualifiedByName = "quartierNom")
    FournisseurDTO toDto(Fournisseur s);

    @Named("quartierNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    QuartierDTO toDtoQuartierNom(Quartier quartier);
}
