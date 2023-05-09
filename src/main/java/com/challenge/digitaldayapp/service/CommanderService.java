package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.CommanderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.Commander}.
 */
public interface CommanderService {
    /**
     * Save a commander.
     *
     * @param commanderDTO the entity to save.
     * @return the persisted entity.
     */
    CommanderDTO save(CommanderDTO commanderDTO);

    /**
     * Updates a commander.
     *
     * @param commanderDTO the entity to update.
     * @return the persisted entity.
     */
    CommanderDTO update(CommanderDTO commanderDTO);

    /**
     * Partially updates a commander.
     *
     * @param commanderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommanderDTO> partialUpdate(CommanderDTO commanderDTO);

    /**
     * Get all the commanders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommanderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commander.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommanderDTO> findOne(Long id);

    /**
     * Delete the "id" commander.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
