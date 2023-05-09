package com.challenge.digitaldayapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.challenge.digitaldayapp.IntegrationTest;
import com.challenge.digitaldayapp.domain.CategorieArticle;
import com.challenge.digitaldayapp.repository.CategorieArticleRepository;
import com.challenge.digitaldayapp.service.dto.CategorieArticleDTO;
import com.challenge.digitaldayapp.service.mapper.CategorieArticleMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategorieArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieArticleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorie-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieArticleRepository categorieArticleRepository;

    @Autowired
    private CategorieArticleMapper categorieArticleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieArticleMockMvc;

    private CategorieArticle categorieArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieArticle createEntity(EntityManager em) {
        CategorieArticle categorieArticle = new CategorieArticle().nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION);
        return categorieArticle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieArticle createUpdatedEntity(EntityManager em) {
        CategorieArticle categorieArticle = new CategorieArticle().nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        return categorieArticle;
    }

    @BeforeEach
    public void initTest() {
        categorieArticle = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieArticle() throws Exception {
        int databaseSizeBeforeCreate = categorieArticleRepository.findAll().size();
        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);
        restCategorieArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieArticle testCategorieArticle = categorieArticleList.get(categorieArticleList.size() - 1);
        assertThat(testCategorieArticle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCategorieArticle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCategorieArticleWithExistingId() throws Exception {
        // Create the CategorieArticle with an existing ID
        categorieArticle.setId(1L);
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        int databaseSizeBeforeCreate = categorieArticleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieArticleRepository.findAll().size();
        // set the field null
        categorieArticle.setNom(null);

        // Create the CategorieArticle, which fails.
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        restCategorieArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategorieArticles() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        // Get all the categorieArticleList
        restCategorieArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCategorieArticle() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        // Get the categorieArticle
        restCategorieArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieArticle.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingCategorieArticle() throws Exception {
        // Get the categorieArticle
        restCategorieArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorieArticle() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();

        // Update the categorieArticle
        CategorieArticle updatedCategorieArticle = categorieArticleRepository.findById(categorieArticle.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieArticle are not directly saved in db
        em.detach(updatedCategorieArticle);
        updatedCategorieArticle.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(updatedCategorieArticle);

        restCategorieArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieArticleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
        CategorieArticle testCategorieArticle = categorieArticleList.get(categorieArticleList.size() - 1);
        assertThat(testCategorieArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCategorieArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieArticleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieArticleWithPatch() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();

        // Update the categorieArticle using partial update
        CategorieArticle partialUpdatedCategorieArticle = new CategorieArticle();
        partialUpdatedCategorieArticle.setId(categorieArticle.getId());

        partialUpdatedCategorieArticle.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restCategorieArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieArticle))
            )
            .andExpect(status().isOk());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
        CategorieArticle testCategorieArticle = categorieArticleList.get(categorieArticleList.size() - 1);
        assertThat(testCategorieArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCategorieArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCategorieArticleWithPatch() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();

        // Update the categorieArticle using partial update
        CategorieArticle partialUpdatedCategorieArticle = new CategorieArticle();
        partialUpdatedCategorieArticle.setId(categorieArticle.getId());

        partialUpdatedCategorieArticle.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restCategorieArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieArticle))
            )
            .andExpect(status().isOk());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
        CategorieArticle testCategorieArticle = categorieArticleList.get(categorieArticleList.size() - 1);
        assertThat(testCategorieArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCategorieArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieArticleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieArticle() throws Exception {
        int databaseSizeBeforeUpdate = categorieArticleRepository.findAll().size();
        categorieArticle.setId(count.incrementAndGet());

        // Create the CategorieArticle
        CategorieArticleDTO categorieArticleDTO = categorieArticleMapper.toDto(categorieArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieArticleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieArticleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieArticle in the database
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieArticle() throws Exception {
        // Initialize the database
        categorieArticleRepository.saveAndFlush(categorieArticle);

        int databaseSizeBeforeDelete = categorieArticleRepository.findAll().size();

        // Delete the categorieArticle
        restCategorieArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieArticle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieArticle> categorieArticleList = categorieArticleRepository.findAll();
        assertThat(categorieArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
