package com.challenge.digitaldayapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.challenge.digitaldayapp.IntegrationTest;
import com.challenge.digitaldayapp.domain.Commander;
import com.challenge.digitaldayapp.repository.CommanderRepository;
import com.challenge.digitaldayapp.service.dto.CommanderDTO;
import com.challenge.digitaldayapp.service.mapper.CommanderMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CommanderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommanderResourceIT {

    private static final Instant DEFAULT_DATE_COMMANDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_COMMANDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT_ACHAT = 1D;
    private static final Double UPDATED_MONTANT_ACHAT = 2D;

    private static final Boolean DEFAULT_A_CREDIT = false;
    private static final Boolean UPDATED_A_CREDIT = true;

    private static final String ENTITY_API_URL = "/api/commanders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommanderRepository commanderRepository;

    @Autowired
    private CommanderMapper commanderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommanderMockMvc;

    private Commander commander;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commander createEntity(EntityManager em) {
        Commander commander = new Commander()
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .montantAchat(DEFAULT_MONTANT_ACHAT)
            .aCredit(DEFAULT_A_CREDIT);
        return commander;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commander createUpdatedEntity(EntityManager em) {
        Commander commander = new Commander()
            .dateCommande(UPDATED_DATE_COMMANDE)
            .montantAchat(UPDATED_MONTANT_ACHAT)
            .aCredit(UPDATED_A_CREDIT);
        return commander;
    }

    @BeforeEach
    public void initTest() {
        commander = createEntity(em);
    }

    @Test
    @Transactional
    void createCommander() throws Exception {
        int databaseSizeBeforeCreate = commanderRepository.findAll().size();
        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);
        restCommanderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commanderDTO)))
            .andExpect(status().isCreated());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeCreate + 1);
        Commander testCommander = commanderList.get(commanderList.size() - 1);
        assertThat(testCommander.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommander.getMontantAchat()).isEqualTo(DEFAULT_MONTANT_ACHAT);
        assertThat(testCommander.getaCredit()).isEqualTo(DEFAULT_A_CREDIT);
    }

    @Test
    @Transactional
    void createCommanderWithExistingId() throws Exception {
        // Create the Commander with an existing ID
        commander.setId(1L);
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        int databaseSizeBeforeCreate = commanderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommanderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commanderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommanders() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        // Get all the commanderList
        restCommanderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commander.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(DEFAULT_DATE_COMMANDE.toString())))
            .andExpect(jsonPath("$.[*].montantAchat").value(hasItem(DEFAULT_MONTANT_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].aCredit").value(hasItem(DEFAULT_A_CREDIT.booleanValue())));
    }

    @Test
    @Transactional
    void getCommander() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        // Get the commander
        restCommanderMockMvc
            .perform(get(ENTITY_API_URL_ID, commander.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commander.getId().intValue()))
            .andExpect(jsonPath("$.dateCommande").value(DEFAULT_DATE_COMMANDE.toString()))
            .andExpect(jsonPath("$.montantAchat").value(DEFAULT_MONTANT_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.aCredit").value(DEFAULT_A_CREDIT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCommander() throws Exception {
        // Get the commander
        restCommanderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommander() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();

        // Update the commander
        Commander updatedCommander = commanderRepository.findById(commander.getId()).get();
        // Disconnect from session so that the updates on updatedCommander are not directly saved in db
        em.detach(updatedCommander);
        updatedCommander.dateCommande(UPDATED_DATE_COMMANDE).montantAchat(UPDATED_MONTANT_ACHAT).aCredit(UPDATED_A_CREDIT);
        CommanderDTO commanderDTO = commanderMapper.toDto(updatedCommander);

        restCommanderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commanderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
        Commander testCommander = commanderList.get(commanderList.size() - 1);
        assertThat(testCommander.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommander.getMontantAchat()).isEqualTo(UPDATED_MONTANT_ACHAT);
        assertThat(testCommander.getaCredit()).isEqualTo(UPDATED_A_CREDIT);
    }

    @Test
    @Transactional
    void putNonExistingCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commanderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commanderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommanderWithPatch() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();

        // Update the commander using partial update
        Commander partialUpdatedCommander = new Commander();
        partialUpdatedCommander.setId(commander.getId());

        partialUpdatedCommander.montantAchat(UPDATED_MONTANT_ACHAT).aCredit(UPDATED_A_CREDIT);

        restCommanderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommander.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommander))
            )
            .andExpect(status().isOk());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
        Commander testCommander = commanderList.get(commanderList.size() - 1);
        assertThat(testCommander.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommander.getMontantAchat()).isEqualTo(UPDATED_MONTANT_ACHAT);
        assertThat(testCommander.getaCredit()).isEqualTo(UPDATED_A_CREDIT);
    }

    @Test
    @Transactional
    void fullUpdateCommanderWithPatch() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();

        // Update the commander using partial update
        Commander partialUpdatedCommander = new Commander();
        partialUpdatedCommander.setId(commander.getId());

        partialUpdatedCommander.dateCommande(UPDATED_DATE_COMMANDE).montantAchat(UPDATED_MONTANT_ACHAT).aCredit(UPDATED_A_CREDIT);

        restCommanderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommander.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommander))
            )
            .andExpect(status().isOk());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
        Commander testCommander = commanderList.get(commanderList.size() - 1);
        assertThat(testCommander.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommander.getMontantAchat()).isEqualTo(UPDATED_MONTANT_ACHAT);
        assertThat(testCommander.getaCredit()).isEqualTo(UPDATED_A_CREDIT);
    }

    @Test
    @Transactional
    void patchNonExistingCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commanderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommander() throws Exception {
        int databaseSizeBeforeUpdate = commanderRepository.findAll().size();
        commander.setId(count.incrementAndGet());

        // Create the Commander
        CommanderDTO commanderDTO = commanderMapper.toDto(commander);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommanderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commanderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commander in the database
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommander() throws Exception {
        // Initialize the database
        commanderRepository.saveAndFlush(commander);

        int databaseSizeBeforeDelete = commanderRepository.findAll().size();

        // Delete the commander
        restCommanderMockMvc
            .perform(delete(ENTITY_API_URL_ID, commander.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commander> commanderList = commanderRepository.findAll();
        assertThat(commanderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
