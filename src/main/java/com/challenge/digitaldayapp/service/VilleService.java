package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.VilleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.Ville}.
 */
public interface VilleService {
    /**
     * Save a ville.
     *
     * @param villeDTO the entity to save.
     * @return the persisted entity.
     */
    VilleDTO save(VilleDTO villeDTO);

    /**
     * Updates a ville.
     *
     * @param villeDTO the entity to update.
     * @return the persisted entity.
     */
    VilleDTO update(VilleDTO villeDTO);

    /**
     * Partially updates a ville.
     *
     * @param villeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VilleDTO> partialUpdate(VilleDTO villeDTO);

    /**
     * Get all the villes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VilleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ville.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VilleDTO> findOne(Long id);

    /**
     * Delete the "id" ville.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
