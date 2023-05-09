package com.challenge.digitaldayapp.web.rest;

import com.challenge.digitaldayapp.repository.CommanderRepository;
import com.challenge.digitaldayapp.service.CommanderService;
import com.challenge.digitaldayapp.service.dto.CommanderDTO;
import com.challenge.digitaldayapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.challenge.digitaldayapp.domain.Commander}.
 */
@RestController
@RequestMapping("/api")
public class CommanderResource {

    private final Logger log = LoggerFactory.getLogger(CommanderResource.class);

    private static final String ENTITY_NAME = "commander";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommanderService commanderService;

    private final CommanderRepository commanderRepository;

    public CommanderResource(CommanderService commanderService, CommanderRepository commanderRepository) {
        this.commanderService = commanderService;
        this.commanderRepository = commanderRepository;
    }

    /**
     * {@code POST  /commanders} : Create a new commander.
     *
     * @param commanderDTO the commanderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commanderDTO, or with status {@code 400 (Bad Request)} if the commander has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commanders")
    public ResponseEntity<CommanderDTO> createCommander(@RequestBody CommanderDTO commanderDTO) throws URISyntaxException {
        log.debug("REST request to save Commander : {}", commanderDTO);
        if (commanderDTO.getId() != null) {
            throw new BadRequestAlertException("A new commander cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommanderDTO result = commanderService.save(commanderDTO);
        return ResponseEntity
            .created(new URI("/api/commanders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commanders/:id} : Updates an existing commander.
     *
     * @param id the id of the commanderDTO to save.
     * @param commanderDTO the commanderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commanderDTO,
     * or with status {@code 400 (Bad Request)} if the commanderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commanderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commanders/{id}")
    public ResponseEntity<CommanderDTO> updateCommander(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommanderDTO commanderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Commander : {}, {}", id, commanderDTO);
        if (commanderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commanderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commanderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommanderDTO result = commanderService.update(commanderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commanderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commanders/:id} : Partial updates given fields of an existing commander, field will ignore if it is null
     *
     * @param id the id of the commanderDTO to save.
     * @param commanderDTO the commanderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commanderDTO,
     * or with status {@code 400 (Bad Request)} if the commanderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commanderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commanderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commanders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommanderDTO> partialUpdateCommander(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommanderDTO commanderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Commander partially : {}, {}", id, commanderDTO);
        if (commanderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commanderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commanderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommanderDTO> result = commanderService.partialUpdate(commanderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commanderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /commanders} : get all the commanders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commanders in body.
     */
    @GetMapping("/commanders")
    public ResponseEntity<List<CommanderDTO>> getAllCommanders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Commanders");
        Page<CommanderDTO> page = commanderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commanders/:id} : get the "id" commander.
     *
     * @param id the id of the commanderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commanderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commanders/{id}")
    public ResponseEntity<CommanderDTO> getCommander(@PathVariable Long id) {
        log.debug("REST request to get Commander : {}", id);
        Optional<CommanderDTO> commanderDTO = commanderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commanderDTO);
    }

    /**
     * {@code DELETE  /commanders/:id} : delete the "id" commander.
     *
     * @param id the id of the commanderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commanders/{id}")
    public ResponseEntity<Void> deleteCommander(@PathVariable Long id) {
        log.debug("REST request to delete Commander : {}", id);
        commanderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
