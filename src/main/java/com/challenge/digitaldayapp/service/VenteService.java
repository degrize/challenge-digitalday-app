package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.VenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.Vente}.
 */
public interface VenteService {
    /**
     * Save a vente.
     *
     * @param venteDTO the entity to save.
     * @return the persisted entity.
     */
    VenteDTO save(VenteDTO venteDTO);

    /**
     * Updates a vente.
     *
     * @param venteDTO the entity to update.
     * @return the persisted entity.
     */
    VenteDTO update(VenteDTO venteDTO);

    /**
     * Partially updates a vente.
     *
     * @param venteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VenteDTO> partialUpdate(VenteDTO venteDTO);

    /**
     * Get all the ventes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VenteDTO> findAll(Pageable pageable);

    /**
     * Get all the ventes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" vente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VenteDTO> findOne(Long id);

    /**
     * Delete the "id" vente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
