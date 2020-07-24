package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.Vehicule;
import com.pfe.covite.repository.VehiculeRepository;
import com.pfe.covite.service.VehiculeService;
import com.pfe.covite.service.dto.VehiculeCriteria;
import com.pfe.covite.service.VehiculeQueryService;

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

import com.pfe.covite.domain.enumeration.Typevehicule;
/**
 * Integration tests for the {@link VehiculeResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VehiculeResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final Typevehicule DEFAULT_TYPE = Typevehicule.VOITURE;
    private static final Typevehicule UPDATED_TYPE = Typevehicule.CAMION;

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;
    private static final Integer SMALLER_CAPACITE = 1 - 1;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private VehiculeQueryService vehiculeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculeMockMvc;

    private Vehicule vehicule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicule createEntity(EntityManager em) {
        Vehicule vehicule = new Vehicule()
            .matricule(DEFAULT_MATRICULE)
            .type(DEFAULT_TYPE)
            .capacite(DEFAULT_CAPACITE);
        return vehicule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicule createUpdatedEntity(EntityManager em) {
        Vehicule vehicule = new Vehicule()
            .matricule(UPDATED_MATRICULE)
            .type(UPDATED_TYPE)
            .capacite(UPDATED_CAPACITE);
        return vehicule;
    }

    @BeforeEach
    public void initTest() {
        vehicule = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicule() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isCreated());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testVehicule.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVehicule.getCapacite()).isEqualTo(DEFAULT_CAPACITE);
    }

    @Test
    @Transactional
    public void createVehiculeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeRepository.findAll().size();

        // Create the Vehicule with an existing ID
        vehicule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeMockMvc.perform(post("/api/vehicules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehicules() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList
        restVehiculeMockMvc.perform(get("/api/vehicules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)));
    }
    
    @Test
    @Transactional
    public void getVehicule() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", vehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicule.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE));
    }


    @Test
    @Transactional
    public void getVehiculesByIdFiltering() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        Long id = vehicule.getId();

        defaultVehiculeShouldBeFound("id.equals=" + id);
        defaultVehiculeShouldNotBeFound("id.notEquals=" + id);

        defaultVehiculeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehiculeShouldNotBeFound("id.greaterThan=" + id);

        defaultVehiculeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehiculeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVehiculesByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule equals to DEFAULT_MATRICULE
        defaultVehiculeShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the vehiculeList where matricule equals to UPDATED_MATRICULE
        defaultVehiculeShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule not equals to DEFAULT_MATRICULE
        defaultVehiculeShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the vehiculeList where matricule not equals to UPDATED_MATRICULE
        defaultVehiculeShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultVehiculeShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the vehiculeList where matricule equals to UPDATED_MATRICULE
        defaultVehiculeShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule is not null
        defaultVehiculeShouldBeFound("matricule.specified=true");

        // Get all the vehiculeList where matricule is null
        defaultVehiculeShouldNotBeFound("matricule.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiculesByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule contains DEFAULT_MATRICULE
        defaultVehiculeShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the vehiculeList where matricule contains UPDATED_MATRICULE
        defaultVehiculeShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where matricule does not contain DEFAULT_MATRICULE
        defaultVehiculeShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the vehiculeList where matricule does not contain UPDATED_MATRICULE
        defaultVehiculeShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllVehiculesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where type equals to DEFAULT_TYPE
        defaultVehiculeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the vehiculeList where type equals to UPDATED_TYPE
        defaultVehiculeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where type not equals to DEFAULT_TYPE
        defaultVehiculeShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the vehiculeList where type not equals to UPDATED_TYPE
        defaultVehiculeShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultVehiculeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the vehiculeList where type equals to UPDATED_TYPE
        defaultVehiculeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where type is not null
        defaultVehiculeShouldBeFound("type.specified=true");

        // Get all the vehiculeList where type is null
        defaultVehiculeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite equals to DEFAULT_CAPACITE
        defaultVehiculeShouldBeFound("capacite.equals=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite equals to UPDATED_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.equals=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite not equals to DEFAULT_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.notEquals=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite not equals to UPDATED_CAPACITE
        defaultVehiculeShouldBeFound("capacite.notEquals=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsInShouldWork() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite in DEFAULT_CAPACITE or UPDATED_CAPACITE
        defaultVehiculeShouldBeFound("capacite.in=" + DEFAULT_CAPACITE + "," + UPDATED_CAPACITE);

        // Get all the vehiculeList where capacite equals to UPDATED_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.in=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite is not null
        defaultVehiculeShouldBeFound("capacite.specified=true");

        // Get all the vehiculeList where capacite is null
        defaultVehiculeShouldNotBeFound("capacite.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite is greater than or equal to DEFAULT_CAPACITE
        defaultVehiculeShouldBeFound("capacite.greaterThanOrEqual=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite is greater than or equal to UPDATED_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.greaterThanOrEqual=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite is less than or equal to DEFAULT_CAPACITE
        defaultVehiculeShouldBeFound("capacite.lessThanOrEqual=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite is less than or equal to SMALLER_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.lessThanOrEqual=" + SMALLER_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsLessThanSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite is less than DEFAULT_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.lessThan=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite is less than UPDATED_CAPACITE
        defaultVehiculeShouldBeFound("capacite.lessThan=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void getAllVehiculesByCapaciteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList where capacite is greater than DEFAULT_CAPACITE
        defaultVehiculeShouldNotBeFound("capacite.greaterThan=" + DEFAULT_CAPACITE);

        // Get all the vehiculeList where capacite is greater than SMALLER_CAPACITE
        defaultVehiculeShouldBeFound("capacite.greaterThan=" + SMALLER_CAPACITE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehiculeShouldBeFound(String filter) throws Exception {
        restVehiculeMockMvc.perform(get("/api/vehicules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)));

        // Check, that the count call also returns 1
        restVehiculeMockMvc.perform(get("/api/vehicules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehiculeShouldNotBeFound(String filter) throws Exception {
        restVehiculeMockMvc.perform(get("/api/vehicules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehiculeMockMvc.perform(get("/api/vehicules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVehicule() throws Exception {
        // Get the vehicule
        restVehiculeMockMvc.perform(get("/api/vehicules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicule() throws Exception {
        // Initialize the database
        vehiculeService.save(vehicule);

        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Update the vehicule
        Vehicule updatedVehicule = vehiculeRepository.findById(vehicule.getId()).get();
        // Disconnect from session so that the updates on updatedVehicule are not directly saved in db
        em.detach(updatedVehicule);
        updatedVehicule
            .matricule(UPDATED_MATRICULE)
            .type(UPDATED_TYPE)
            .capacite(UPDATED_CAPACITE);

        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicule)))
            .andExpect(status().isOk());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
        Vehicule testVehicule = vehiculeList.get(vehiculeList.size() - 1);
        assertThat(testVehicule.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testVehicule.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVehicule.getCapacite()).isEqualTo(UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicule() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeRepository.findAll().size();

        // Create the Vehicule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeMockMvc.perform(put("/api/vehicules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicule)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicule() throws Exception {
        // Initialize the database
        vehiculeService.save(vehicule);

        int databaseSizeBeforeDelete = vehiculeRepository.findAll().size();

        // Delete the vehicule
        restVehiculeMockMvc.perform(delete("/api/vehicules/{id}", vehicule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        assertThat(vehiculeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
