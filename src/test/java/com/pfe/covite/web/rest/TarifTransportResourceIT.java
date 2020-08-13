package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.TarifTransport;
import com.pfe.covite.repository.TarifTransportRepository;
import com.pfe.covite.service.TarifTransportService;
import com.pfe.covite.service.dto.TarifTransportCriteria;
import com.pfe.covite.service.TarifTransportQueryService;

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
 * Integration tests for the {@link TarifTransportResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TarifTransportResourceIT {

    private static final String DEFAULT_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICULE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICULE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_DE_PERSONNE = 1;
    private static final Integer UPDATED_NOMBRE_DE_PERSONNE = 2;
    private static final Integer SMALLER_NOMBRE_DE_PERSONNE = 1 - 1;

    private static final Float DEFAULT_DISTANCE = 1F;
    private static final Float UPDATED_DISTANCE = 2F;
    private static final Float SMALLER_DISTANCE = 1F - 1F;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifTransportRepository tarifTransportRepository;

    @Autowired
    private TarifTransportService tarifTransportService;

    @Autowired
    private TarifTransportQueryService tarifTransportQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifTransportMockMvc;

    private TarifTransport tarifTransport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransport createEntity(EntityManager em) {
        TarifTransport tarifTransport = new TarifTransport()
            .service(DEFAULT_SERVICE)
            .vehicule(DEFAULT_VEHICULE)
            .nombreDePersonne(DEFAULT_NOMBRE_DE_PERSONNE)
            .distance(DEFAULT_DISTANCE)
            .prix(DEFAULT_PRIX);
        return tarifTransport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransport createUpdatedEntity(EntityManager em) {
        TarifTransport tarifTransport = new TarifTransport()
            .service(UPDATED_SERVICE)
            .vehicule(UPDATED_VEHICULE)
            .nombreDePersonne(UPDATED_NOMBRE_DE_PERSONNE)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);
        return tarifTransport;
    }

    @BeforeEach
    public void initTest() {
        tarifTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifTransport() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport
        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isCreated());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeCreate + 1);
        TarifTransport testTarifTransport = tarifTransportList.get(tarifTransportList.size() - 1);
        assertThat(testTarifTransport.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testTarifTransport.getVehicule()).isEqualTo(DEFAULT_VEHICULE);
        assertThat(testTarifTransport.getNombreDePersonne()).isEqualTo(DEFAULT_NOMBRE_DE_PERSONNE);
        assertThat(testTarifTransport.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testTarifTransport.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport with an existing ID
        tarifTransport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifTransports() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].vehicule").value(hasItem(DEFAULT_VEHICULE)))
            .andExpect(jsonPath("$.[*].nombreDePersonne").value(hasItem(DEFAULT_NOMBRE_DE_PERSONNE)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get the tarifTransport
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/{id}", tarifTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifTransport.getId().intValue()))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE))
            .andExpect(jsonPath("$.vehicule").value(DEFAULT_VEHICULE))
            .andExpect(jsonPath("$.nombreDePersonne").value(DEFAULT_NOMBRE_DE_PERSONNE))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifTransportsByIdFiltering() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        Long id = tarifTransport.getId();

        defaultTarifTransportShouldBeFound("id.equals=" + id);
        defaultTarifTransportShouldNotBeFound("id.notEquals=" + id);

        defaultTarifTransportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifTransportShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifTransportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifTransportShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service equals to DEFAULT_SERVICE
        defaultTarifTransportShouldBeFound("service.equals=" + DEFAULT_SERVICE);

        // Get all the tarifTransportList where service equals to UPDATED_SERVICE
        defaultTarifTransportShouldNotBeFound("service.equals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service not equals to DEFAULT_SERVICE
        defaultTarifTransportShouldNotBeFound("service.notEquals=" + DEFAULT_SERVICE);

        // Get all the tarifTransportList where service not equals to UPDATED_SERVICE
        defaultTarifTransportShouldBeFound("service.notEquals=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByServiceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service in DEFAULT_SERVICE or UPDATED_SERVICE
        defaultTarifTransportShouldBeFound("service.in=" + DEFAULT_SERVICE + "," + UPDATED_SERVICE);

        // Get all the tarifTransportList where service equals to UPDATED_SERVICE
        defaultTarifTransportShouldNotBeFound("service.in=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service is not null
        defaultTarifTransportShouldBeFound("service.specified=true");

        // Get all the tarifTransportList where service is null
        defaultTarifTransportShouldNotBeFound("service.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifTransportsByServiceContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service contains DEFAULT_SERVICE
        defaultTarifTransportShouldBeFound("service.contains=" + DEFAULT_SERVICE);

        // Get all the tarifTransportList where service contains UPDATED_SERVICE
        defaultTarifTransportShouldNotBeFound("service.contains=" + UPDATED_SERVICE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByServiceNotContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where service does not contain DEFAULT_SERVICE
        defaultTarifTransportShouldNotBeFound("service.doesNotContain=" + DEFAULT_SERVICE);

        // Get all the tarifTransportList where service does not contain UPDATED_SERVICE
        defaultTarifTransportShouldBeFound("service.doesNotContain=" + UPDATED_SERVICE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule equals to DEFAULT_VEHICULE
        defaultTarifTransportShouldBeFound("vehicule.equals=" + DEFAULT_VEHICULE);

        // Get all the tarifTransportList where vehicule equals to UPDATED_VEHICULE
        defaultTarifTransportShouldNotBeFound("vehicule.equals=" + UPDATED_VEHICULE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule not equals to DEFAULT_VEHICULE
        defaultTarifTransportShouldNotBeFound("vehicule.notEquals=" + DEFAULT_VEHICULE);

        // Get all the tarifTransportList where vehicule not equals to UPDATED_VEHICULE
        defaultTarifTransportShouldBeFound("vehicule.notEquals=" + UPDATED_VEHICULE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule in DEFAULT_VEHICULE or UPDATED_VEHICULE
        defaultTarifTransportShouldBeFound("vehicule.in=" + DEFAULT_VEHICULE + "," + UPDATED_VEHICULE);

        // Get all the tarifTransportList where vehicule equals to UPDATED_VEHICULE
        defaultTarifTransportShouldNotBeFound("vehicule.in=" + UPDATED_VEHICULE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule is not null
        defaultTarifTransportShouldBeFound("vehicule.specified=true");

        // Get all the tarifTransportList where vehicule is null
        defaultTarifTransportShouldNotBeFound("vehicule.specified=false");
    }
                @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule contains DEFAULT_VEHICULE
        defaultTarifTransportShouldBeFound("vehicule.contains=" + DEFAULT_VEHICULE);

        // Get all the tarifTransportList where vehicule contains UPDATED_VEHICULE
        defaultTarifTransportShouldNotBeFound("vehicule.contains=" + UPDATED_VEHICULE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByVehiculeNotContainsSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where vehicule does not contain DEFAULT_VEHICULE
        defaultTarifTransportShouldNotBeFound("vehicule.doesNotContain=" + DEFAULT_VEHICULE);

        // Get all the tarifTransportList where vehicule does not contain UPDATED_VEHICULE
        defaultTarifTransportShouldBeFound("vehicule.doesNotContain=" + UPDATED_VEHICULE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne equals to DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.equals=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne equals to UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.equals=" + UPDATED_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne not equals to DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.notEquals=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne not equals to UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.notEquals=" + UPDATED_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne in DEFAULT_NOMBRE_DE_PERSONNE or UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.in=" + DEFAULT_NOMBRE_DE_PERSONNE + "," + UPDATED_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne equals to UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.in=" + UPDATED_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne is not null
        defaultTarifTransportShouldBeFound("nombreDePersonne.specified=true");

        // Get all the tarifTransportList where nombreDePersonne is null
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne is greater than or equal to DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.greaterThanOrEqual=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne is greater than or equal to UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.greaterThanOrEqual=" + UPDATED_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne is less than or equal to DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.lessThanOrEqual=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne is less than or equal to SMALLER_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.lessThanOrEqual=" + SMALLER_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne is less than DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.lessThan=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne is less than UPDATED_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.lessThan=" + UPDATED_NOMBRE_DE_PERSONNE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByNombreDePersonneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where nombreDePersonne is greater than DEFAULT_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldNotBeFound("nombreDePersonne.greaterThan=" + DEFAULT_NOMBRE_DE_PERSONNE);

        // Get all the tarifTransportList where nombreDePersonne is greater than SMALLER_NOMBRE_DE_PERSONNE
        defaultTarifTransportShouldBeFound("nombreDePersonne.greaterThan=" + SMALLER_NOMBRE_DE_PERSONNE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance equals to DEFAULT_DISTANCE
        defaultTarifTransportShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance equals to UPDATED_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance not equals to DEFAULT_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.notEquals=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance not equals to UPDATED_DISTANCE
        defaultTarifTransportShouldBeFound("distance.notEquals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultTarifTransportShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the tarifTransportList where distance equals to UPDATED_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance is not null
        defaultTarifTransportShouldBeFound("distance.specified=true");

        // Get all the tarifTransportList where distance is null
        defaultTarifTransportShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance is greater than or equal to DEFAULT_DISTANCE
        defaultTarifTransportShouldBeFound("distance.greaterThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance is greater than or equal to UPDATED_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.greaterThanOrEqual=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance is less than or equal to DEFAULT_DISTANCE
        defaultTarifTransportShouldBeFound("distance.lessThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance is less than or equal to SMALLER_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.lessThanOrEqual=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance is less than DEFAULT_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.lessThan=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance is less than UPDATED_DISTANCE
        defaultTarifTransportShouldBeFound("distance.lessThan=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByDistanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where distance is greater than DEFAULT_DISTANCE
        defaultTarifTransportShouldNotBeFound("distance.greaterThan=" + DEFAULT_DISTANCE);

        // Get all the tarifTransportList where distance is greater than SMALLER_DISTANCE
        defaultTarifTransportShouldBeFound("distance.greaterThan=" + SMALLER_DISTANCE);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix equals to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix equals to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix not equals to DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix not equals to UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifTransportList where prix equals to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is not null
        defaultTarifTransportShouldBeFound("prix.specified=true");

        // Get all the tarifTransportList where prix is null
        defaultTarifTransportShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is less than or equal to SMALLER_PRIX
        defaultTarifTransportShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is less than DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is less than UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is greater than DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is greater than SMALLER_PRIX
        defaultTarifTransportShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifTransportShouldBeFound(String filter) throws Exception {
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].vehicule").value(hasItem(DEFAULT_VEHICULE)))
            .andExpect(jsonPath("$.[*].nombreDePersonne").value(hasItem(DEFAULT_NOMBRE_DE_PERSONNE)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifTransportShouldNotBeFound(String filter) throws Exception {
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifTransport() throws Exception {
        // Get the tarifTransport
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportService.save(tarifTransport);

        int databaseSizeBeforeUpdate = tarifTransportRepository.findAll().size();

        // Update the tarifTransport
        TarifTransport updatedTarifTransport = tarifTransportRepository.findById(tarifTransport.getId()).get();
        // Disconnect from session so that the updates on updatedTarifTransport are not directly saved in db
        em.detach(updatedTarifTransport);
        updatedTarifTransport
            .service(UPDATED_SERVICE)
            .vehicule(UPDATED_VEHICULE)
            .nombreDePersonne(UPDATED_NOMBRE_DE_PERSONNE)
            .distance(UPDATED_DISTANCE)
            .prix(UPDATED_PRIX);

        restTarifTransportMockMvc.perform(put("/api/tarif-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifTransport)))
            .andExpect(status().isOk());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeUpdate);
        TarifTransport testTarifTransport = tarifTransportList.get(tarifTransportList.size() - 1);
        assertThat(testTarifTransport.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testTarifTransport.getVehicule()).isEqualTo(UPDATED_VEHICULE);
        assertThat(testTarifTransport.getNombreDePersonne()).isEqualTo(UPDATED_NOMBRE_DE_PERSONNE);
        assertThat(testTarifTransport.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testTarifTransport.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifTransport() throws Exception {
        int databaseSizeBeforeUpdate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifTransportMockMvc.perform(put("/api/tarif-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportService.save(tarifTransport);

        int databaseSizeBeforeDelete = tarifTransportRepository.findAll().size();

        // Delete the tarifTransport
        restTarifTransportMockMvc.perform(delete("/api/tarif-transports/{id}", tarifTransport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
