package com.challenge.digitaldayapp.web.rest;

import com.challenge.digitaldayapp.repository.CategorieArticleRepository;
import com.challenge.digitaldayapp.service.CategorieArticleService;
import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import com.challenge.digitaldayapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.challenge.digitaldayapp.domain.CategorieArticle}.
 */
@RestController
@RequestMapping("/api")
public class CategorieArticleResource {

    private final Logger log = LoggerFactory.getLogger(CategorieArticleResource.class);

    private static final String ENTITY_NAME = "categorieArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieArticleService categorieArticleService;

    private final CategorieArticleRepository categorieArticleRepository;

    public CategorieArticleResource(
        CategorieArticleService categorieArticleService,
        CategorieArticleRepository categorieArticleRepository
    ) {
        this.categorieArticleService = categorieArticleService;
        this.categorieArticleRepository = categorieArticleRepository;
    }

    /**
     * {@code POST  /categorie-articles} : Create a new categorieArticle.
     *
     * @param categorieArticleDTO the categorieArticleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieArticleDTO, or with status {@code 400 (Bad Request)} if the categorieArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-articles")
    public ResponseEntity<CategorieArticleDTO> createCategorieArticle(@Valid @RequestBody CategorieArticleDTO categorieArticleDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategorieArticle : {}", categorieArticleDTO);
        if (categorieArticleDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieArticleDTO result = categorieArticleService.save(categorieArticleDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-articles/:id} : Updates an existing categorieArticle.
     *
     * @param id the id of the categorieArticleDTO to save.
     * @param categorieArticleDTO the categorieArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieArticleDTO,
     * or with status {@code 400 (Bad Request)} if the categorieArticleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-articles/{id}")
    public ResponseEntity<CategorieArticleDTO> updateCategorieArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategorieArticleDTO categorieArticleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieArticle : {}, {}", id, categorieArticleDTO);
        if (categorieArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieArticleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieArticleDTO result = categorieArticleService.update(categorieArticleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieArticleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-articles/:id} : Partial updates given fields of an existing categorieArticle, field will ignore if it is null
     *
     * @param id the id of the categorieArticleDTO to save.
     * @param categorieArticleDTO the categorieArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieArticleDTO,
     * or with status {@code 400 (Bad Request)} if the categorieArticleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieArticleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategorieArticleDTO> partialUpdateCategorieArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategorieArticleDTO categorieArticleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieArticle partially : {}, {}", id, categorieArticleDTO);
        if (categorieArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieArticleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieArticleDTO> result = categorieArticleService.partialUpdate(categorieArticleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieArticleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-articles} : get all the categorieArticles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieArticles in body.
     */
    @GetMapping("/categorie-articles")
    public ResponseEntity<List<CategorieArticleDTO>> getAllCategorieArticles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CategorieArticles");
        Page<CategorieArticleDTO> page = categorieArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-articles/:id} : get the "id" categorieArticle.
     *
     * @param id the id of the categorieArticleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieArticleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-articles/{id}")
    public ResponseEntity<CategorieArticleDTO> getCategorieArticle(@PathVariable Long id) {
        log.debug("REST request to get CategorieArticle : {}", id);
        Optional<CategorieArticleDTO> categorieArticleDTO = categorieArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieArticleDTO);
    }

    /**
     * {@code DELETE  /categorie-articles/:id} : delete the "id" categorieArticle.
     *
     * @param id the id of the categorieArticleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-articles/{id}")
    public ResponseEntity<Void> deleteCategorieArticle(@PathVariable Long id) {
        log.debug("REST request to delete CategorieArticle : {}", id);
        categorieArticleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
