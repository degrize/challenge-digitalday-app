package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Client;
import com.challenge.digitaldayapp.domain.Quartier;
import com.challenge.digitaldayapp.service.dto.ClientDTO;
import com.challenge.digitaldayapp.service.dto.QuartierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "quartier", source = "quartier", qualifiedByName = "quartierNom")
    ClientDTO toDto(Client s);

    @Named("quartierNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    QuartierDTO toDtoQuartierNom(Quartier quartier);
}
