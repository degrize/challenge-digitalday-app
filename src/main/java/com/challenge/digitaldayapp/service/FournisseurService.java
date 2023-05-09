package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.FournisseurDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.Fournisseur}.
 */
public interface FournisseurService {
    /**
     * Save a fournisseur.
     *
     * @param fournisseurDTO the entity to save.
     * @return the persisted entity.
     */
    FournisseurDTO save(FournisseurDTO fournisseurDTO);

    /**
     * Updates a fournisseur.
     *
     * @param fournisseurDTO the entity to update.
     * @return the persisted entity.
     */
    FournisseurDTO update(FournisseurDTO fournisseurDTO);

    /**
     * Partially updates a fournisseur.
     *
     * @param fournisseurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FournisseurDTO> partialUpdate(FournisseurDTO fournisseurDTO);

    /**
     * Get all the fournisseurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FournisseurDTO> findAll(Pageable pageable);

    /**
     * Get all the fournisseurs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FournisseurDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FournisseurDTO> findOne(Long id);

    /**
     * Delete the "id" fournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
