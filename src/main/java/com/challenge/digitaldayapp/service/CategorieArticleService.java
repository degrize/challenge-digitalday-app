package com.challenge.digitaldayapp.service;

import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.challenge.digitaldayapp.domain.CategorieArticle}.
 */
public interface CategorieArticleService {
    /**
     * Save a categorieArticle.
     *
     * @param categorieArticleDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieArticleDTO save(CategorieArticleDTO categorieArticleDTO);

    /**
     * Updates a categorieArticle.
     *
     * @param categorieArticleDTO the entity to update.
     * @return the persisted entity.
     */
    CategorieArticleDTO update(CategorieArticleDTO categorieArticleDTO);

    /**
     * Partially updates a categorieArticle.
     *
     * @param categorieArticleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieArticleDTO> partialUpdate(CategorieArticleDTO categorieArticleDTO);

    /**
     * Get all the categorieArticles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieArticleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieArticleDTO> findOne(Long id);

    /**
     * Delete the "id" categorieArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
