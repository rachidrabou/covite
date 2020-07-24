package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.Commandes;
import com.pfe.covite.repository.CommandesRepository;
import com.pfe.covite.service.CommandesService;
import com.pfe.covite.service.dto.CommandesCriteria;
import com.pfe.covite.service.CommandesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pfe.covite.domain.enumeration.Categorie;
import com.pfe.covite.domain.enumeration.Service;
/**
 * Integration tests for the {@link CommandesResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommandesResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    private static final Categorie DEFAULT_TYPE = Categorie.PERSONNE;
    private static final Categorie UPDATED_TYPE = Categorie.ANIMAL;

    private static final Service DEFAULT_TYPESERVICE = Service.TRANSPORT;
    private static final Service UPDATED_TYPESERVICE = Service.LIVRAISON;

    @Autowired
    private CommandesRepository commandesRepository;

    @Autowired
    private CommandesService commandesService;

    @Autowired
    private CommandesQueryService commandesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandesMockMvc;

    private Commandes commandes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandes createEntity(EntityManager em) {
        Commandes commandes = new Commandes()
            .date(DEFAULT_DATE)
            .prix(DEFAULT_PRIX)
            .type(DEFAULT_TYPE)
            .typeservice(DEFAULT_TYPESERVICE);
        return commandes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandes createUpdatedEntity(EntityManager em) {
        Commandes commandes = new Commandes()
            .date(UPDATED_DATE)
            .prix(UPDATED_PRIX)
            .type(UPDATED_TYPE)
            .typeservice(UPDATED_TYPESERVICE);
        return commandes;
    }

    @BeforeEach
    public void initTest() {
        commandes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandes() throws Exception {
        int databaseSizeBeforeCreate = commandesRepository.findAll().size();

        // Create the Commandes
        restCommandesMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandes)))
            .andExpect(status().isCreated());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeCreate + 1);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCommandes.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testCommandes.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCommandes.getTypeservice()).isEqualTo(DEFAULT_TYPESERVICE);
    }

    @Test
    @Transactional
    public void createCommandesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandesRepository.findAll().size();

        // Create the Commandes with an existing ID
        commandes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandesMockMvc.perform(post("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandes)))
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList
        restCommandesMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandes.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeservice").value(hasItem(DEFAULT_TYPESERVICE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommandes() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get the commandes
        restCommandesMockMvc.perform(get("/api/commandes/{id}", commandes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandes.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.typeservice").value(DEFAULT_TYPESERVICE.toString()));
    }


    @Test
    @Transactional
    public void getCommandesByIdFiltering() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        Long id = commandes.getId();

        defaultCommandesShouldBeFound("id.equals=" + id);
        defaultCommandesShouldNotBeFound("id.notEquals=" + id);

        defaultCommandesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommandesShouldNotBeFound("id.greaterThan=" + id);

        defaultCommandesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommandesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommandesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date equals to DEFAULT_DATE
        defaultCommandesShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the commandesList where date equals to UPDATED_DATE
        defaultCommandesShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date not equals to DEFAULT_DATE
        defaultCommandesShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the commandesList where date not equals to UPDATED_DATE
        defaultCommandesShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCommandesShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the commandesList where date equals to UPDATED_DATE
        defaultCommandesShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date is not null
        defaultCommandesShouldBeFound("date.specified=true");

        // Get all the commandesList where date is null
        defaultCommandesShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date is greater than or equal to DEFAULT_DATE
        defaultCommandesShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the commandesList where date is greater than or equal to UPDATED_DATE
        defaultCommandesShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date is less than or equal to DEFAULT_DATE
        defaultCommandesShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the commandesList where date is less than or equal to SMALLER_DATE
        defaultCommandesShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date is less than DEFAULT_DATE
        defaultCommandesShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the commandesList where date is less than UPDATED_DATE
        defaultCommandesShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommandesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where date is greater than DEFAULT_DATE
        defaultCommandesShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the commandesList where date is greater than SMALLER_DATE
        defaultCommandesShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllCommandesByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix equals to DEFAULT_PRIX
        defaultCommandesShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the commandesList where prix equals to UPDATED_PRIX
        defaultCommandesShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix not equals to DEFAULT_PRIX
        defaultCommandesShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the commandesList where prix not equals to UPDATED_PRIX
        defaultCommandesShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultCommandesShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the commandesList where prix equals to UPDATED_PRIX
        defaultCommandesShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix is not null
        defaultCommandesShouldBeFound("prix.specified=true");

        // Get all the commandesList where prix is null
        defaultCommandesShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix is greater than or equal to DEFAULT_PRIX
        defaultCommandesShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the commandesList where prix is greater than or equal to UPDATED_PRIX
        defaultCommandesShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix is less than or equal to DEFAULT_PRIX
        defaultCommandesShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the commandesList where prix is less than or equal to SMALLER_PRIX
        defaultCommandesShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix is less than DEFAULT_PRIX
        defaultCommandesShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the commandesList where prix is less than UPDATED_PRIX
        defaultCommandesShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllCommandesByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where prix is greater than DEFAULT_PRIX
        defaultCommandesShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the commandesList where prix is greater than SMALLER_PRIX
        defaultCommandesShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }


    @Test
    @Transactional
    public void getAllCommandesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where type equals to DEFAULT_TYPE
        defaultCommandesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the commandesList where type equals to UPDATED_TYPE
        defaultCommandesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where type not equals to DEFAULT_TYPE
        defaultCommandesShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the commandesList where type not equals to UPDATED_TYPE
        defaultCommandesShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCommandesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the commandesList where type equals to UPDATED_TYPE
        defaultCommandesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where type is not null
        defaultCommandesShouldBeFound("type.specified=true");

        // Get all the commandesList where type is null
        defaultCommandesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeserviceIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where typeservice equals to DEFAULT_TYPESERVICE
        defaultCommandesShouldBeFound("typeservice.equals=" + DEFAULT_TYPESERVICE);

        // Get all the commandesList where typeservice equals to UPDATED_TYPESERVICE
        defaultCommandesShouldNotBeFound("typeservice.equals=" + UPDATED_TYPESERVICE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeserviceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where typeservice not equals to DEFAULT_TYPESERVICE
        defaultCommandesShouldNotBeFound("typeservice.notEquals=" + DEFAULT_TYPESERVICE);

        // Get all the commandesList where typeservice not equals to UPDATED_TYPESERVICE
        defaultCommandesShouldBeFound("typeservice.notEquals=" + UPDATED_TYPESERVICE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeserviceIsInShouldWork() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where typeservice in DEFAULT_TYPESERVICE or UPDATED_TYPESERVICE
        defaultCommandesShouldBeFound("typeservice.in=" + DEFAULT_TYPESERVICE + "," + UPDATED_TYPESERVICE);

        // Get all the commandesList where typeservice equals to UPDATED_TYPESERVICE
        defaultCommandesShouldNotBeFound("typeservice.in=" + UPDATED_TYPESERVICE);
    }

    @Test
    @Transactional
    public void getAllCommandesByTypeserviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesRepository.saveAndFlush(commandes);

        // Get all the commandesList where typeservice is not null
        defaultCommandesShouldBeFound("typeservice.specified=true");

        // Get all the commandesList where typeservice is null
        defaultCommandesShouldNotBeFound("typeservice.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandesShouldBeFound(String filter) throws Exception {
        restCommandesMockMvc.perform(get("/api/commandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandes.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeservice").value(hasItem(DEFAULT_TYPESERVICE.toString())));

        // Check, that the count call also returns 1
        restCommandesMockMvc.perform(get("/api/commandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandesShouldNotBeFound(String filter) throws Exception {
        restCommandesMockMvc.perform(get("/api/commandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandesMockMvc.perform(get("/api/commandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommandes() throws Exception {
        // Get the commandes
        restCommandesMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandes() throws Exception {
        // Initialize the database
        commandesService.save(commandes);

        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();

        // Update the commandes
        Commandes updatedCommandes = commandesRepository.findById(commandes.getId()).get();
        // Disconnect from session so that the updates on updatedCommandes are not directly saved in db
        em.detach(updatedCommandes);
        updatedCommandes
            .date(UPDATED_DATE)
            .prix(UPDATED_PRIX)
            .type(UPDATED_TYPE)
            .typeservice(UPDATED_TYPESERVICE);

        restCommandesMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandes)))
            .andExpect(status().isOk());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCommandes.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testCommandes.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCommandes.getTypeservice()).isEqualTo(UPDATED_TYPESERVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();

        // Create the Commandes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandesMockMvc.perform(put("/api/commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandes)))
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandes() throws Exception {
        // Initialize the database
        commandesService.save(commandes);

        int databaseSizeBeforeDelete = commandesRepository.findAll().size();

        // Delete the commandes
        restCommandesMockMvc.perform(delete("/api/commandes/{id}", commandes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
