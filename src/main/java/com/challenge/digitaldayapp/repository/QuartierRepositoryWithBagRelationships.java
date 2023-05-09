package com.challenge.digitaldayapp.repository;

import com.challenge.digitaldayapp.domain.Quartier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface QuartierRepositoryWithBagRelationships {
    Optional<Quartier> fetchBagRelationships(Optional<Quartier> quartier);

    List<Quartier> fetchBagRelationships(List<Quartier> quartiers);

    Page<Quartier> fetchBagRelationships(Page<Quartier> quartiers);
}
