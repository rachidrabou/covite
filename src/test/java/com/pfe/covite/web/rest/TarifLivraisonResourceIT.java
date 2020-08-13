package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.TarifLivraison;
import com.pfe.covite.repository.TarifLivraisonRepository;
import com.pfe.covite.service.TarifLivraisonService;
import com.pfe.covite.service.dto.TarifLivraisonCriteria;
import com.pfe.covite.service.TarifLivraisonQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TarifLivraisonResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TarifLivraisonResourceIT {

    private static final String DEFAULT_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJET = "AAAAAAAAAA";
    private static final String UPDATED_OBJET = "BBBBBBBBBB";

    private static final Float DEFAULT_DISTANCE = 1F;
    private static final Float UPDATED_DISTANCE = 2F;
    private static final Float SMALLER_DISTANCE = 1F - 1F;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifLivraisonRepository tarifLivraisonRepository;

    @Autowired
    private TarifLivraisonService tarifLivraisonService;

    @Autowired
    private TarifLivraisonQueryService tarifLivraisonQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifLivraisonMockMvc;

    private TarifLivraison tarifLivraison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifLivraison createEntity(EntityManager em) {
        TarifLivraison tarifLivraison = new TarifLivraison()
            .service(DEFAULT_SERVICE)
            .objet(DEFAULT_OBJET)
            .distance(DEFAULT_DISTANCE)
            .prix(DEFAULT_PRIX);
        return tarifLivraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifLivraison createUpdatedEntity(EntityManager em) {
        TarifLivraison tarifLivraison = new TarifLivraison()
            .service(UPDATED_SERVICE)
            .objet(UPDATED_OBJET)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);
        return tarifLivraison;
    }

    @BeforeEach
    public void initTest() {
        tarifLivraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifLivraison() throws Exception {
        int databaseSizeBeforeCreate = tarifLivraisonRepository.findAll().size();

        // Create the TarifLivraison
        restTarifLivraisonMockMvc.perform(post("/api/tarif-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifLivraison)))
            .andExpect(status().isCreated());

        // Validate the TarifLivraison in the database
        List<TarifLivraison> tarifLivraisonList = tarifLivraisonRepository.findAll();
        assertThat(tarifLivraisonList).hasSize(databaseSizeBeforeCreate + 1);
        TarifLivraison testTarifLivraison = tarifLivraisonList.get(tarifLivraisonList.size() - 1);
        assertThat(testTarifLivraison.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testTarifLivraison.getObjet()).isEqualTo(DEFAULT_OBJET);
        assertThat(testTarifLivraison.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testTarifLivraison.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifLivraisonRepository.findAll().size();

        // Create the TarifLivraison with an existing ID
        tarifLivraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifLivraisonMockMvc.perform(post("/api/tarif-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the TarifLivraison in the database
        List<TarifLivraison> tarifLivraisonList = tarifLivraisonRepository.findAll();
        assertThat(tarifLivraisonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifLivraisons() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifLivraison() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get the tarifLivraison
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons/{id}", tarifLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifLivraison.getId().intValue()))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE))
            .andExpect(jsonPath("$.objet").value(DEFAULT_OBJET))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifLivraisonsByIdFiltering() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        Long id = tarifLivraison.getId();

        defaultTarifLivraisonShouldBeFound("id.equals=" + id);
        defaultTarifLivraisonShouldNotBeFound("id.notEquals=" + id);

        defaultTarifLivraisonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifLivraisonShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifLivraisonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifLivraisonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service equals to DEFAULT_SERVICE
        defaultTarifLivraisonShouldBeFound("service.equals=" + DEFAULT_SERVICE);

        // Get all the tarifLivraisonList where service equals to UPDATED_SERVICE
        defaultTarifLivraisonShouldNotBeFound("service.equals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service not equals to DEFAULT_SERVICE
        defaultTarifLivraisonShouldNotBeFound("service.notEquals=" + DEFAULT_SERVICE);

        // Get all the tarifLivraisonList where service not equals to UPDATED_SERVICE
        defaultTarifLivraisonShouldBeFound("service.notEquals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service in DEFAULT_SERVICE or UPDATED_SERVICE
        defaultTarifLivraisonShouldBeFound("service.in=" + DEFAULT_SERVICE + "," + UPDATED_SERVICE);

        // Get all the tarifLivraisonList where service equals to UPDATED_SERVICE
        defaultTarifLivraisonShouldNotBeFound("service.in=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service is not null
        defaultTarifLivraisonShouldBeFound("service.specified=true");

        // Get all the tarifLivraisonList where service is null
        defaultTarifLivraisonShouldNotBeFound("service.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceContainsSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service contains DEFAULT_SERVICE
        defaultTarifLivraisonShouldBeFound("service.contains=" + DEFAULT_SERVICE);

        // Get all the tarifLivraisonList where service contains UPDATED_SERVICE
        defaultTarifLivraisonShouldNotBeFound("service.contains=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByServiceNotContainsSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where service does not contain DEFAULT_SERVICE
        defaultTarifLivraisonShouldNotBeFound("service.doesNotContain=" + DEFAULT_SERVICE);

        // Get all the tarifLivraisonList where service does not contain UPDATED_SERVICE
        defaultTarifLivraisonShouldBeFound("service.doesNotContain=" + UPDATED_SERVICE);
    }


    @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet equals to DEFAULT_OBJET
        defaultTarifLivraisonShouldBeFound("objet.equals=" + DEFAULT_OBJET);

        // Get all the tarifLivraisonList where objet equals to UPDATED_OBJET
        defaultTarifLivraisonShouldNotBeFound("objet.equals=" + UPDATED_OBJET);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet not equals to DEFAULT_OBJET
        defaultTarifLivraisonShouldNotBeFound("objet.notEquals=" + DEFAULT_OBJET);

        // Get all the tarifLivraisonList where objet not equals to UPDATED_OBJET
        defaultTarifLivraisonShouldBeFound("objet.notEquals=" + UPDATED_OBJET);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetIsInShouldWork() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet in DEFAULT_OBJET or UPDATED_OBJET
        defaultTarifLivraisonShouldBeFound("objet.in=" + DEFAULT_OBJET + "," + UPDATED_OBJET);

        // Get all the tarifLivraisonList where objet equals to UPDATED_OBJET
        defaultTarifLivraisonShouldNotBeFound("objet.in=" + UPDATED_OBJET);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet is not null
        defaultTarifLivraisonShouldBeFound("objet.specified=true");

        // Get all the tarifLivraisonList where objet is null
        defaultTarifLivraisonShouldNotBeFound("objet.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetContainsSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet contains DEFAULT_OBJET
        defaultTarifLivraisonShouldBeFound("objet.contains=" + DEFAULT_OBJET);

        // Get all the tarifLivraisonList where objet contains UPDATED_OBJET
        defaultTarifLivraisonShouldNotBeFound("objet.contains=" + UPDATED_OBJET);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByObjetNotContainsSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where objet does not contain DEFAULT_OBJET
        defaultTarifLivraisonShouldNotBeFound("objet.doesNotContain=" + DEFAULT_OBJET);

        // Get all the tarifLivraisonList where objet does not contain UPDATED_OBJET
        defaultTarifLivraisonShouldBeFound("objet.doesNotContain=" + UPDATED_OBJET);
    }


    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance equals to DEFAULT_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance equals to UPDATED_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance not equals to DEFAULT_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.notEquals=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance not equals to UPDATED_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.notEquals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the tarifLivraisonList where distance equals to UPDATED_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance is not null
        defaultTarifLivraisonShouldBeFound("distance.specified=true");

        // Get all the tarifLivraisonList where distance is null
        defaultTarifLivraisonShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance is greater than or equal to DEFAULT_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.greaterThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance is greater than or equal to UPDATED_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.greaterThanOrEqual=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance is less than or equal to DEFAULT_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.lessThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance is less than or equal to SMALLER_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.lessThanOrEqual=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance is less than DEFAULT_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.lessThan=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance is less than UPDATED_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.lessThan=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByDistanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where distance is greater than DEFAULT_DISTANCE
        defaultTarifLivraisonShouldNotBeFound("distance.greaterThan=" + DEFAULT_DISTANCE);

        // Get all the tarifLivraisonList where distance is greater than SMALLER_DISTANCE
        defaultTarifLivraisonShouldBeFound("distance.greaterThan=" + SMALLER_DISTANCE);
    }


    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix equals to DEFAULT_PRIX
        defaultTarifLivraisonShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix equals to UPDATED_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix not equals to DEFAULT_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix not equals to UPDATED_PRIX
        defaultTarifLivraisonShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifLivraisonShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifLivraisonList where prix equals to UPDATED_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix is not null
        defaultTarifLivraisonShouldBeFound("prix.specified=true");

        // Get all the tarifLivraisonList where prix is null
        defaultTarifLivraisonShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifLivraisonShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifLivraisonShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix is less than or equal to SMALLER_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix is less than DEFAULT_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix is less than UPDATED_PRIX
        defaultTarifLivraisonShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifLivraisonsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifLivraisonRepository.saveAndFlush(tarifLivraison);

        // Get all the tarifLivraisonList where prix is greater than DEFAULT_PRIX
        defaultTarifLivraisonShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifLivraisonList where prix is greater than SMALLER_PRIX
        defaultTarifLivraisonShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifLivraisonShouldBeFound(String filter) throws Exception {
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifLivraisonShouldNotBeFound(String filter) throws Exception {
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifLivraison() throws Exception {
        // Get the tarifLivraison
        restTarifLivraisonMockMvc.perform(get("/api/tarif-livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifLivraison() throws Exception {
        // Initialize the database
        tarifLivraisonService.save(tarifLivraison);

        int databaseSizeBeforeUpdate = tarifLivraisonRepository.findAll().size();

        // Update the tarifLivraison
        TarifLivraison updatedTarifLivraison = tarifLivraisonRepository.findById(tarifLivraison.getId()).get();
        // Disconnect from session so that the updates on updatedTarifLivraison are not directly saved in db
        em.detach(updatedTarifLivraison);
        updatedTarifLivraison
            .service(UPDATED_SERVICE)
            .objet(UPDATED_OBJET)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);

        restTarifLivraisonMockMvc.perform(put("/api/tarif-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifLivraison)))
            .andExpect(status().isOk());

        // Validate the TarifLivraison in the database
        List<TarifLivraison> tarifLivraisonList = tarifLivraisonRepository.findAll();
        assertThat(tarifLivraisonList).hasSize(databaseSizeBeforeUpdate);
        TarifLivraison testTarifLivraison = tarifLivraisonList.get(tarifLivraisonList.size() - 1);
        assertThat(testTarifLivraison.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testTarifLivraison.getObjet()).isEqualTo(UPDATED_OBJET);
        assertThat(testTarifLivraison.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testTarifLivraison.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifLivraison() throws Exception {
        int databaseSizeBeforeUpdate = tarifLivraisonRepository.findAll().size();

        // Create the TarifLivraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifLivraisonMockMvc.perform(put("/api/tarif-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the TarifLivraison in the database
        List<TarifLivraison> tarifLivraisonList = tarifLivraisonRepository.findAll();
        assertThat(tarifLivraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifLivraison() throws Exception {
        // Initialize the database
        tarifLivraisonService.save(tarifLivraison);

        int databaseSizeBeforeDelete = tarifLivraisonRepository.findAll().size();

        // Delete the tarifLivraison
        restTarifLivraisonMockMvc.perform(delete("/api/tarif-livraisons/{id}", tarifLivraison.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifLivraison> tarifLivraisonList = tarifLivraisonRepository.findAll();
        assertThat(tarifLivraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
