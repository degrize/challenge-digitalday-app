package com.challenge.digitaldayapp.service.mapper;

import com.challenge.digitaldayapp.domain.Ville;
import com.challenge.digitaldayapp.service.dto.VilleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ville} and its DTO {@link VilleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VilleMapper extends EntityMapper<VilleDTO, Ville> {}
