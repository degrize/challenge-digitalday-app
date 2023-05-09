package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.QuartierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.Quartier}.
 */
public interface QuartierService {
    /**
     * Save a quartier.
     *
     * @param quartierDTO the entity to save.
     * @return the persisted entity.
     */
    QuartierDTO save(QuartierDTO quartierDTO);

    /**
     * Updates a quartier.
     *
     * @param quartierDTO the entity to update.
     * @return the persisted entity.
     */
    QuartierDTO update(QuartierDTO quartierDTO);

    /**
     * Partially updates a quartier.
     *
     * @param quartierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuartierDTO> partialUpdate(QuartierDTO quartierDTO);

    /**
     * Get all the quartiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuartierDTO> findAll(Pageable pageable);

    /**
     * Get all the quartiers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuartierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" quartier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuartierDTO> findOne(Long id);

    /**
     * Delete the "id" quartier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
