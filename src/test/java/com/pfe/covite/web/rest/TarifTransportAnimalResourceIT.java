package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.TarifTransportAnimal;
import com.pfe.covite.repository.TarifTransportAnimalRepository;
import com.pfe.covite.service.TarifTransportAnimalService;
import com.pfe.covite.service.dto.TarifTransportAnimalCriteria;
import com.pfe.covite.service.TarifTransportAnimalQueryService;

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
 * Integration tests for the {@link TarifTransportAnimalResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TarifTransportAnimalResourceIT {

    private static final String DEFAULT_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_ANIMAL = "AAAAAAAAAA";
    private static final String UPDATED_ANIMAL = "BBBBBBBBBB";

    private static final Float DEFAULT_DISTANCE = 1F;
    private static final Float UPDATED_DISTANCE = 2F;
    private static final Float SMALLER_DISTANCE = 1F - 1F;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifTransportAnimalRepository tarifTransportAnimalRepository;

    @Autowired
    private TarifTransportAnimalService tarifTransportAnimalService;

    @Autowired
    private TarifTransportAnimalQueryService tarifTransportAnimalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifTransportAnimalMockMvc;

    private TarifTransportAnimal tarifTransportAnimal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransportAnimal createEntity(EntityManager em) {
        TarifTransportAnimal tarifTransportAnimal = new TarifTransportAnimal()
            .service(DEFAULT_SERVICE)
            .animal(DEFAULT_ANIMAL)
            .distance(DEFAULT_DISTANCE)
            .prix(DEFAULT_PRIX);
        return tarifTransportAnimal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransportAnimal createUpdatedEntity(EntityManager em) {
        TarifTransportAnimal tarifTransportAnimal = new TarifTransportAnimal()
            .service(UPDATED_SERVICE)
            .animal(UPDATED_ANIMAL)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);
        return tarifTransportAnimal;
    }

    @BeforeEach
    public void initTest() {
        tarifTransportAnimal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifTransportAnimal() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportAnimalRepository.findAll().size();

        // Create the TarifTransportAnimal
        restTarifTransportAnimalMockMvc.perform(post("/api/tarif-transport-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransportAnimal)))
            .andExpect(status().isCreated());

        // Validate the TarifTransportAnimal in the database
        List<TarifTransportAnimal> tarifTransportAnimalList = tarifTransportAnimalRepository.findAll();
        assertThat(tarifTransportAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        TarifTransportAnimal testTarifTransportAnimal = tarifTransportAnimalList.get(tarifTransportAnimalList.size() - 1);
        assertThat(testTarifTransportAnimal.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testTarifTransportAnimal.getAnimal()).isEqualTo(DEFAULT_ANIMAL);
        assertThat(testTarifTransportAnimal.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testTarifTransportAnimal.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifTransportAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportAnimalRepository.findAll().size();

        // Create the TarifTransportAnimal with an existing ID
        tarifTransportAnimal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifTransportAnimalMockMvc.perform(post("/api/tarif-transport-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransportAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransportAnimal in the database
        List<TarifTransportAnimal> tarifTransportAnimalList = tarifTransportAnimalRepository.findAll();
        assertThat(tarifTransportAnimalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifTransportAnimals() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransportAnimal.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].animal").value(hasItem(DEFAULT_ANIMAL)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifTransportAnimal() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get the tarifTransportAnimal
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals/{id}", tarifTransportAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifTransportAnimal.getId().intValue()))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE))
            .andExpect(jsonPath("$.animal").value(DEFAULT_ANIMAL))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifTransportAnimalsByIdFiltering() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        Long id = tarifTransportAnimal.getId();

        defaultTarifTransportAnimalShouldBeFound("id.equals=" + id);
        defaultTarifTransportAnimalShouldNotBeFound("id.notEquals=" + id);

        defaultTarifTransportAnimalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifTransportAnimalShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifTransportAnimalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifTransportAnimalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service equals to DEFAULT_SERVICE
        defaultTarifTransportAnimalShouldBeFound("service.equals=" + DEFAULT_SERVICE);

        // Get all the tarifTransportAnimalList where service equals to UPDATED_SERVICE
        defaultTarifTransportAnimalShouldNotBeFound("service.equals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service not equals to DEFAULT_SERVICE
        defaultTarifTransportAnimalShouldNotBeFound("service.notEquals=" + DEFAULT_SERVICE);

        // Get all the tarifTransportAnimalList where service not equals to UPDATED_SERVICE
        defaultTarifTransportAnimalShouldBeFound("service.notEquals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service in DEFAULT_SERVICE or UPDATED_SERVICE
        defaultTarifTransportAnimalShouldBeFound("service.in=" + DEFAULT_SERVICE + "," + UPDATED_SERVICE);

        // Get all the tarifTransportAnimalList where service equals to UPDATED_SERVICE
        defaultTarifTransportAnimalShouldNotBeFound("service.in=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service is not null
        defaultTarifTransportAnimalShouldBeFound("service.specified=true");

        // Get all the tarifTransportAnimalList where service is null
        defaultTarifTransportAnimalShouldNotBeFound("service.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service contains DEFAULT_SERVICE
        defaultTarifTransportAnimalShouldBeFound("service.contains=" + DEFAULT_SERVICE);

        // Get all the tarifTransportAnimalList where service contains UPDATED_SERVICE
        defaultTarifTransportAnimalShouldNotBeFound("service.contains=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByServiceNotContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where service does not contain DEFAULT_SERVICE
        defaultTarifTransportAnimalShouldNotBeFound("service.doesNotContain=" + DEFAULT_SERVICE);

        // Get all the tarifTransportAnimalList where service does not contain UPDATED_SERVICE
        defaultTarifTransportAnimalShouldBeFound("service.doesNotContain=" + UPDATED_SERVICE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal equals to DEFAULT_ANIMAL
        defaultTarifTransportAnimalShouldBeFound("animal.equals=" + DEFAULT_ANIMAL);

        // Get all the tarifTransportAnimalList where animal equals to UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldNotBeFound("animal.equals=" + UPDATED_ANIMAL);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal not equals to DEFAULT_ANIMAL
        defaultTarifTransportAnimalShouldNotBeFound("animal.notEquals=" + DEFAULT_ANIMAL);

        // Get all the tarifTransportAnimalList where animal not equals to UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldBeFound("animal.notEquals=" + UPDATED_ANIMAL);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal in DEFAULT_ANIMAL or UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldBeFound("animal.in=" + DEFAULT_ANIMAL + "," + UPDATED_ANIMAL);

        // Get all the tarifTransportAnimalList where animal equals to UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldNotBeFound("animal.in=" + UPDATED_ANIMAL);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal is not null
        defaultTarifTransportAnimalShouldBeFound("animal.specified=true");

        // Get all the tarifTransportAnimalList where animal is null
        defaultTarifTransportAnimalShouldNotBeFound("animal.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal contains DEFAULT_ANIMAL
        defaultTarifTransportAnimalShouldBeFound("animal.contains=" + DEFAULT_ANIMAL);

        // Get all the tarifTransportAnimalList where animal contains UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldNotBeFound("animal.contains=" + UPDATED_ANIMAL);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByAnimalNotContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where animal does not contain DEFAULT_ANIMAL
        defaultTarifTransportAnimalShouldNotBeFound("animal.doesNotContain=" + DEFAULT_ANIMAL);

        // Get all the tarifTransportAnimalList where animal does not contain UPDATED_ANIMAL
        defaultTarifTransportAnimalShouldBeFound("animal.doesNotContain=" + UPDATED_ANIMAL);
    }


    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance equals to DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance equals to UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance not equals to DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.notEquals=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance not equals to UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.notEquals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the tarifTransportAnimalList where distance equals to UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance is not null
        defaultTarifTransportAnimalShouldBeFound("distance.specified=true");

        // Get all the tarifTransportAnimalList where distance is null
        defaultTarifTransportAnimalShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance is greater than or equal to DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.greaterThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance is greater than or equal to UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.greaterThanOrEqual=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance is less than or equal to DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.lessThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance is less than or equal to SMALLER_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.lessThanOrEqual=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance is less than DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.lessThan=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance is less than UPDATED_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.lessThan=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByDistanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where distance is greater than DEFAULT_DISTANCE
        defaultTarifTransportAnimalShouldNotBeFound("distance.greaterThan=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportAnimalList where distance is greater than SMALLER_DISTANCE
        defaultTarifTransportAnimalShouldBeFound("distance.greaterThan=" + SMALLER_DISTANCE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix equals to DEFAULT_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix equals to UPDATED_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix not equals to DEFAULT_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix not equals to UPDATED_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifTransportAnimalList where prix equals to UPDATED_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix is not null
        defaultTarifTransportAnimalShouldBeFound("prix.specified=true");

        // Get all the tarifTransportAnimalList where prix is null
        defaultTarifTransportAnimalShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix is less than or equal to SMALLER_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix is less than DEFAULT_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix is less than UPDATED_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportAnimalsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportAnimalRepository.saveAndFlush(tarifTransportAnimal);

        // Get all the tarifTransportAnimalList where prix is greater than DEFAULT_PRIX
        defaultTarifTransportAnimalShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportAnimalList where prix is greater than SMALLER_PRIX
        defaultTarifTransportAnimalShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifTransportAnimalShouldBeFound(String filter) throws Exception {
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransportAnimal.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].animal").value(hasItem(DEFAULT_ANIMAL)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifTransportAnimalShouldNotBeFound(String filter) throws Exception {
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifTransportAnimal() throws Exception {
        // Get the tarifTransportAnimal
        restTarifTransportAnimalMockMvc.perform(get("/api/tarif-transport-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifTransportAnimal() throws Exception {
        // Initialize the database
        tarifTransportAnimalService.save(tarifTransportAnimal);

        int databaseSizeBeforeUpdate = tarifTransportAnimalRepository.findAll().size();

        // Update the tarifTransportAnimal
        TarifTransportAnimal updatedTarifTransportAnimal = tarifTransportAnimalRepository.findById(tarifTransportAnimal.getId()).get();
        // Disconnect from session so that the updates on updatedTarifTransportAnimal are not directly saved in db
        em.detach(updatedTarifTransportAnimal);
        updatedTarifTransportAnimal
            .service(UPDATED_SERVICE)
            .animal(UPDATED_ANIMAL)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);

        restTarifTransportAnimalMockMvc.perform(put("/api/tarif-transport-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifTransportAnimal)))
            .andExpect(status().isOk());

        // Validate the TarifTransportAnimal in the database
        List<TarifTransportAnimal> tarifTransportAnimalList = tarifTransportAnimalRepository.findAll();
        assertThat(tarifTransportAnimalList).hasSize(databaseSizeBeforeUpdate);
        TarifTransportAnimal testTarifTransportAnimal = tarifTransportAnimalList.get(tarifTransportAnimalList.size() - 1);
        assertThat(testTarifTransportAnimal.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testTarifTransportAnimal.getAnimal()).isEqualTo(UPDATED_ANIMAL);
        assertThat(testTarifTransportAnimal.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testTarifTransportAnimal.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifTransportAnimal() throws Exception {
        int databaseSizeBeforeUpdate = tarifTransportAnimalRepository.findAll().size();

        // Create the TarifTransportAnimal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifTransportAnimalMockMvc.perform(put("/api/tarif-transport-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransportAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransportAnimal in the database
        List<TarifTransportAnimal> tarifTransportAnimalList = tarifTransportAnimalRepository.findAll();
        assertThat(tarifTransportAnimalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifTransportAnimal() throws Exception {
        // Initialize the database
        tarifTransportAnimalService.save(tarifTransportAnimal);

        int databaseSizeBeforeDelete = tarifTransportAnimalRepository.findAll().size();

        // Delete the tarifTransportAnimal
        restTarifTransportAnimalMockMvc.perform(delete("/api/tarif-transport-animals/{id}", tarifTransportAnimal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifTransportAnimal> tarifTransportAnimalList = tarifTransportAnimalRepository.findAll();
        assertThat(tarifTransportAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
