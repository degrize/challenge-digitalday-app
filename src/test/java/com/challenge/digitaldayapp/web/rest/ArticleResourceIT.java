package com.challenge.digitaldayapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.challenge.digitaldayapp.IntegrationTest;
import com.challenge.digitaldayapp.domain.Article;
import com.challenge.digitaldayapp.repository.ArticleRepository;
import com.challenge.digitaldayapp.service.ArticleService;
import com.challenge.digitaldayapp.service.dto.ArticleDTO;
import com.challenge.digitaldayapp.service.mapper.ArticleMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArticleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_VENTE = 1D;
    private static final Double UPDATED_PRIX_VENTE = 2D;

    private static final Integer DEFAULT_QTE = 1;
    private static final Integer UPDATED_QTE = 2;

    private static final LocalDate DEFAULT_DATE_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FABRICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FABRICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_BAR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BAR = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_ACHAT = 1D;
    private static final Double UPDATED_PRIX_ACHAT = 2D;

    private static final Double DEFAULT_SEUIL_SECURITE = 1D;
    private static final Double UPDATED_SEUIL_SECURITE = 2D;

    private static final Double DEFAULT_SEUIL_MINIMAL = 1D;
    private static final Double UPDATED_SEUIL_MINIMAL = 2D;

    private static final Double DEFAULT_SEUIL_ALERTE = 1D;
    private static final Double UPDATED_SEUIL_ALERTE = 2D;

    private static final String DEFAULT_EMPLACEMENT = "AAAAAAAAAA";
    private static final String UPDATED_EMPLACEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_PRINCIPALE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_PRINCIPALE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_SECONDAIRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_SECONDAIRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_TERTAIRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_TERTAIRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_TERTAIRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleRepository articleRepository;

    @Mock
    private ArticleRepository articleRepositoryMock;

    @Autowired
    private ArticleMapper articleMapper;

    @Mock
    private ArticleService articleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleMockMvc;

    private Article article;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .nom(DEFAULT_NOM)
            .prixVente(DEFAULT_PRIX_VENTE)
            .qte(DEFAULT_QTE)
            .dateExpiration(DEFAULT_DATE_EXPIRATION)
            .dateFabrication(DEFAULT_DATE_FABRICATION)
            .codeBar(DEFAULT_CODE_BAR)
            .prixAchat(DEFAULT_PRIX_ACHAT)
            .seuilSecurite(DEFAULT_SEUIL_SECURITE)
            .seuilMinimal(DEFAULT_SEUIL_MINIMAL)
            .seuilAlerte(DEFAULT_SEUIL_ALERTE)
            .emplacement(DEFAULT_EMPLACEMENT)
            .description(DEFAULT_DESCRIPTION)
            .photoPrincipale(DEFAULT_PHOTO_PRINCIPALE)
            .photoPrincipaleContentType(DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE)
            .photoSecondaire(DEFAULT_PHOTO_SECONDAIRE)
            .photoSecondaireContentType(DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE)
            .photoTertaire(DEFAULT_PHOTO_TERTAIRE)
            .photoTertaireContentType(DEFAULT_PHOTO_TERTAIRE_CONTENT_TYPE);
        return article;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createUpdatedEntity(EntityManager em) {
        Article article = new Article()
            .nom(UPDATED_NOM)
            .prixVente(UPDATED_PRIX_VENTE)
            .qte(UPDATED_QTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .dateFabrication(UPDATED_DATE_FABRICATION)
            .codeBar(UPDATED_CODE_BAR)
            .prixAchat(UPDATED_PRIX_ACHAT)
            .seuilSecurite(UPDATED_SEUIL_SECURITE)
            .seuilMinimal(UPDATED_SEUIL_MINIMAL)
            .seuilAlerte(UPDATED_SEUIL_ALERTE)
            .emplacement(UPDATED_EMPLACEMENT)
            .description(UPDATED_DESCRIPTION)
            .photoPrincipale(UPDATED_PHOTO_PRINCIPALE)
            .photoPrincipaleContentType(UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE)
            .photoSecondaire(UPDATED_PHOTO_SECONDAIRE)
            .photoSecondaireContentType(UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE)
            .photoTertaire(UPDATED_PHOTO_TERTAIRE)
            .photoTertaireContentType(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);
        return article;
    }

    @BeforeEach
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();
        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testArticle.getPrixVente()).isEqualTo(DEFAULT_PRIX_VENTE);
        assertThat(testArticle.getQte()).isEqualTo(DEFAULT_QTE);
        assertThat(testArticle.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testArticle.getDateFabrication()).isEqualTo(DEFAULT_DATE_FABRICATION);
        assertThat(testArticle.getCodeBar()).isEqualTo(DEFAULT_CODE_BAR);
        assertThat(testArticle.getPrixAchat()).isEqualTo(DEFAULT_PRIX_ACHAT);
        assertThat(testArticle.getSeuilSecurite()).isEqualTo(DEFAULT_SEUIL_SECURITE);
        assertThat(testArticle.getSeuilMinimal()).isEqualTo(DEFAULT_SEUIL_MINIMAL);
        assertThat(testArticle.getSeuilAlerte()).isEqualTo(DEFAULT_SEUIL_ALERTE);
        assertThat(testArticle.getEmplacement()).isEqualTo(DEFAULT_EMPLACEMENT);
        assertThat(testArticle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticle.getPhotoPrincipale()).isEqualTo(DEFAULT_PHOTO_PRINCIPALE);
        assertThat(testArticle.getPhotoPrincipaleContentType()).isEqualTo(DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoSecondaire()).isEqualTo(DEFAULT_PHOTO_SECONDAIRE);
        assertThat(testArticle.getPhotoSecondaireContentType()).isEqualTo(DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoTertaire()).isEqualTo(DEFAULT_PHOTO_TERTAIRE);
        assertThat(testArticle.getPhotoTertaireContentType()).isEqualTo(DEFAULT_PHOTO_TERTAIRE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createArticleWithExistingId() throws Exception {
        // Create the Article with an existing ID
        article.setId(1L);
        ArticleDTO articleDTO = articleMapper.toDto(article);

        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setNom(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixVenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setPrixVente(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQteIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setQte(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateExpirationIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setDateExpiration(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFabricationIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setDateFabrication(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixAchatIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setPrixAchat(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prixVente").value(hasItem(DEFAULT_PRIX_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].qte").value(hasItem(DEFAULT_QTE)))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].dateFabrication").value(hasItem(DEFAULT_DATE_FABRICATION.toString())))
            .andExpect(jsonPath("$.[*].codeBar").value(hasItem(DEFAULT_CODE_BAR)))
            .andExpect(jsonPath("$.[*].prixAchat").value(hasItem(DEFAULT_PRIX_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilSecurite").value(hasItem(DEFAULT_SEUIL_SECURITE.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilMinimal").value(hasItem(DEFAULT_SEUIL_MINIMAL.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilAlerte").value(hasItem(DEFAULT_SEUIL_ALERTE.doubleValue())))
            .andExpect(jsonPath("$.[*].emplacement").value(hasItem(DEFAULT_EMPLACEMENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].photoPrincipaleContentType").value(hasItem(DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoPrincipale").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_PRINCIPALE))))
            .andExpect(jsonPath("$.[*].photoSecondaireContentType").value(hasItem(DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoSecondaire").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_SECONDAIRE))))
            .andExpect(jsonPath("$.[*].photoTertaireContentType").value(hasItem(DEFAULT_PHOTO_TERTAIRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoTertaire").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_TERTAIRE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticlesWithEagerRelationshipsIsEnabled() throws Exception {
        when(articleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(articleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticlesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(articleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(articleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prixVente").value(DEFAULT_PRIX_VENTE.doubleValue()))
            .andExpect(jsonPath("$.qte").value(DEFAULT_QTE))
            .andExpect(jsonPath("$.dateExpiration").value(DEFAULT_DATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.dateFabrication").value(DEFAULT_DATE_FABRICATION.toString()))
            .andExpect(jsonPath("$.codeBar").value(DEFAULT_CODE_BAR))
            .andExpect(jsonPath("$.prixAchat").value(DEFAULT_PRIX_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.seuilSecurite").value(DEFAULT_SEUIL_SECURITE.doubleValue()))
            .andExpect(jsonPath("$.seuilMinimal").value(DEFAULT_SEUIL_MINIMAL.doubleValue()))
            .andExpect(jsonPath("$.seuilAlerte").value(DEFAULT_SEUIL_ALERTE.doubleValue()))
            .andExpect(jsonPath("$.emplacement").value(DEFAULT_EMPLACEMENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.photoPrincipaleContentType").value(DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoPrincipale").value(Base64Utils.encodeToString(DEFAULT_PHOTO_PRINCIPALE)))
            .andExpect(jsonPath("$.photoSecondaireContentType").value(DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoSecondaire").value(Base64Utils.encodeToString(DEFAULT_PHOTO_SECONDAIRE)))
            .andExpect(jsonPath("$.photoTertaireContentType").value(DEFAULT_PHOTO_TERTAIRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoTertaire").value(Base64Utils.encodeToString(DEFAULT_PHOTO_TERTAIRE)));
    }

    @Test
    @Transactional
    void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .nom(UPDATED_NOM)
            .prixVente(UPDATED_PRIX_VENTE)
            .qte(UPDATED_QTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .dateFabrication(UPDATED_DATE_FABRICATION)
            .codeBar(UPDATED_CODE_BAR)
            .prixAchat(UPDATED_PRIX_ACHAT)
            .seuilSecurite(UPDATED_SEUIL_SECURITE)
            .seuilMinimal(UPDATED_SEUIL_MINIMAL)
            .seuilAlerte(UPDATED_SEUIL_ALERTE)
            .emplacement(UPDATED_EMPLACEMENT)
            .description(UPDATED_DESCRIPTION)
            .photoPrincipale(UPDATED_PHOTO_PRINCIPALE)
            .photoPrincipaleContentType(UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE)
            .photoSecondaire(UPDATED_PHOTO_SECONDAIRE)
            .photoSecondaireContentType(UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE)
            .photoTertaire(UPDATED_PHOTO_TERTAIRE)
            .photoTertaireContentType(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);
        ArticleDTO articleDTO = articleMapper.toDto(updatedArticle);

        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArticle.getPrixVente()).isEqualTo(UPDATED_PRIX_VENTE);
        assertThat(testArticle.getQte()).isEqualTo(UPDATED_QTE);
        assertThat(testArticle.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testArticle.getDateFabrication()).isEqualTo(UPDATED_DATE_FABRICATION);
        assertThat(testArticle.getCodeBar()).isEqualTo(UPDATED_CODE_BAR);
        assertThat(testArticle.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
        assertThat(testArticle.getSeuilSecurite()).isEqualTo(UPDATED_SEUIL_SECURITE);
        assertThat(testArticle.getSeuilMinimal()).isEqualTo(UPDATED_SEUIL_MINIMAL);
        assertThat(testArticle.getSeuilAlerte()).isEqualTo(UPDATED_SEUIL_ALERTE);
        assertThat(testArticle.getEmplacement()).isEqualTo(UPDATED_EMPLACEMENT);
        assertThat(testArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticle.getPhotoPrincipale()).isEqualTo(UPDATED_PHOTO_PRINCIPALE);
        assertThat(testArticle.getPhotoPrincipaleContentType()).isEqualTo(UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoSecondaire()).isEqualTo(UPDATED_PHOTO_SECONDAIRE);
        assertThat(testArticle.getPhotoSecondaireContentType()).isEqualTo(UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoTertaire()).isEqualTo(UPDATED_PHOTO_TERTAIRE);
        assertThat(testArticle.getPhotoTertaireContentType()).isEqualTo(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .nom(UPDATED_NOM)
            .prixVente(UPDATED_PRIX_VENTE)
            .qte(UPDATED_QTE)
            .codeBar(UPDATED_CODE_BAR)
            .seuilMinimal(UPDATED_SEUIL_MINIMAL)
            .seuilAlerte(UPDATED_SEUIL_ALERTE)
            .photoTertaire(UPDATED_PHOTO_TERTAIRE)
            .photoTertaireContentType(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArticle.getPrixVente()).isEqualTo(UPDATED_PRIX_VENTE);
        assertThat(testArticle.getQte()).isEqualTo(UPDATED_QTE);
        assertThat(testArticle.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testArticle.getDateFabrication()).isEqualTo(DEFAULT_DATE_FABRICATION);
        assertThat(testArticle.getCodeBar()).isEqualTo(UPDATED_CODE_BAR);
        assertThat(testArticle.getPrixAchat()).isEqualTo(DEFAULT_PRIX_ACHAT);
        assertThat(testArticle.getSeuilSecurite()).isEqualTo(DEFAULT_SEUIL_SECURITE);
        assertThat(testArticle.getSeuilMinimal()).isEqualTo(UPDATED_SEUIL_MINIMAL);
        assertThat(testArticle.getSeuilAlerte()).isEqualTo(UPDATED_SEUIL_ALERTE);
        assertThat(testArticle.getEmplacement()).isEqualTo(DEFAULT_EMPLACEMENT);
        assertThat(testArticle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticle.getPhotoPrincipale()).isEqualTo(DEFAULT_PHOTO_PRINCIPALE);
        assertThat(testArticle.getPhotoPrincipaleContentType()).isEqualTo(DEFAULT_PHOTO_PRINCIPALE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoSecondaire()).isEqualTo(DEFAULT_PHOTO_SECONDAIRE);
        assertThat(testArticle.getPhotoSecondaireContentType()).isEqualTo(DEFAULT_PHOTO_SECONDAIRE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoTertaire()).isEqualTo(UPDATED_PHOTO_TERTAIRE);
        assertThat(testArticle.getPhotoTertaireContentType()).isEqualTo(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .nom(UPDATED_NOM)
            .prixVente(UPDATED_PRIX_VENTE)
            .qte(UPDATED_QTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .dateFabrication(UPDATED_DATE_FABRICATION)
            .codeBar(UPDATED_CODE_BAR)
            .prixAchat(UPDATED_PRIX_ACHAT)
            .seuilSecurite(UPDATED_SEUIL_SECURITE)
            .seuilMinimal(UPDATED_SEUIL_MINIMAL)
            .seuilAlerte(UPDATED_SEUIL_ALERTE)
            .emplacement(UPDATED_EMPLACEMENT)
            .description(UPDATED_DESCRIPTION)
            .photoPrincipale(UPDATED_PHOTO_PRINCIPALE)
            .photoPrincipaleContentType(UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE)
            .photoSecondaire(UPDATED_PHOTO_SECONDAIRE)
            .photoSecondaireContentType(UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE)
            .photoTertaire(UPDATED_PHOTO_TERTAIRE)
            .photoTertaireContentType(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArticle.getPrixVente()).isEqualTo(UPDATED_PRIX_VENTE);
        assertThat(testArticle.getQte()).isEqualTo(UPDATED_QTE);
        assertThat(testArticle.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testArticle.getDateFabrication()).isEqualTo(UPDATED_DATE_FABRICATION);
        assertThat(testArticle.getCodeBar()).isEqualTo(UPDATED_CODE_BAR);
        assertThat(testArticle.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
        assertThat(testArticle.getSeuilSecurite()).isEqualTo(UPDATED_SEUIL_SECURITE);
        assertThat(testArticle.getSeuilMinimal()).isEqualTo(UPDATED_SEUIL_MINIMAL);
        assertThat(testArticle.getSeuilAlerte()).isEqualTo(UPDATED_SEUIL_ALERTE);
        assertThat(testArticle.getEmplacement()).isEqualTo(UPDATED_EMPLACEMENT);
        assertThat(testArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticle.getPhotoPrincipale()).isEqualTo(UPDATED_PHOTO_PRINCIPALE);
        assertThat(testArticle.getPhotoPrincipaleContentType()).isEqualTo(UPDATED_PHOTO_PRINCIPALE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoSecondaire()).isEqualTo(UPDATED_PHOTO_SECONDAIRE);
        assertThat(testArticle.getPhotoSecondaireContentType()).isEqualTo(UPDATED_PHOTO_SECONDAIRE_CONTENT_TYPE);
        assertThat(testArticle.getPhotoTertaire()).isEqualTo(UPDATED_PHOTO_TERTAIRE);
        assertThat(testArticle.getPhotoTertaireContentType()).isEqualTo(UPDATED_PHOTO_TERTAIRE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();
        article.setId(count.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Delete the article
        restArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, article.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
