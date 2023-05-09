package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Quartier;
import com.challenge.digitaldayapp.domain.Ville;
import com.challenge.digitaldayapp.service.dto.QuartierDTO;
import com.challenge.digitaldayapp.service.dto.VilleDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quartier} and its DTO {@link QuartierDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuartierMapper extends EntityMapper<QuartierDTO, Quartier> {
    @Mapping(target = "villes", source = "villes", qualifiedByName = "villeNomSet")
    QuartierDTO toDto(Quartier s);

    @Mapping(target = "removeVille", ignore = true)
    Quartier toEntity(QuartierDTO quartierDTO);

    @Named("villeNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    VilleDTO toDtoVilleNom(Ville ville);

    @Named("villeNomSet")
    default Set<VilleDTO> toDtoVilleNomSet(Set<Ville> ville) {
        return ville.stream().map(this::toDtoVilleNom).collect(Collectors.toSet());
    }
}
