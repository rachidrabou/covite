package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.Livreur;
import com.pfe.covite.domain.User;
import com.pfe.covite.repository.LivreurRepository;
import com.pfe.covite.service.LivreurService;
import com.pfe.covite.service.dto.LivreurCriteria;
import com.pfe.covite.service.LivreurQueryService;

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
 * Integration tests for the {@link LivreurResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LivreurResourceIT {

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Float DEFAULT_SOLDE = 1F;
    private static final Float UPDATED_SOLDE = 2F;
    private static final Float SMALLER_SOLDE = 1F - 1F;

    @Autowired
    private LivreurRepository livreurRepository;

    @Autowired
    private LivreurService livreurService;

    @Autowired
    private LivreurQueryService livreurQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivreurMockMvc;

    private Livreur livreur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livreur createEntity(EntityManager em) {
        Livreur livreur = new Livreur()
            .telephone(DEFAULT_TELEPHONE)
            .solde(DEFAULT_SOLDE);
        return livreur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livreur createUpdatedEntity(EntityManager em) {
        Livreur livreur = new Livreur()
            .telephone(UPDATED_TELEPHONE)
            .solde(UPDATED_SOLDE);
        return livreur;
    }

    @BeforeEach
    public void initTest() {
        livreur = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivreur() throws Exception {
        int databaseSizeBeforeCreate = livreurRepository.findAll().size();

        // Create the Livreur
        restLivreurMockMvc.perform(post("/api/livreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isCreated());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeCreate + 1);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testLivreur.getSolde()).isEqualTo(DEFAULT_SOLDE);
    }

    @Test
    @Transactional
    public void createLivreurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livreurRepository.findAll().size();

        // Create the Livreur with an existing ID
        livreur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivreurMockMvc.perform(post("/api/livreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLivreurs() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList
        restLivreurMockMvc.perform(get("/api/livreurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLivreur() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get the livreur
        restLivreurMockMvc.perform(get("/api/livreurs/{id}", livreur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livreur.getId().intValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()));
    }


    @Test
    @Transactional
    public void getLivreursByIdFiltering() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        Long id = livreur.getId();

        defaultLivreurShouldBeFound("id.equals=" + id);
        defaultLivreurShouldNotBeFound("id.notEquals=" + id);

        defaultLivreurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLivreurShouldNotBeFound("id.greaterThan=" + id);

        defaultLivreurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLivreurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLivreursByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone equals to DEFAULT_TELEPHONE
        defaultLivreurShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the livreurList where telephone equals to UPDATED_TELEPHONE
        defaultLivreurShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllLivreursByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone not equals to DEFAULT_TELEPHONE
        defaultLivreurShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the livreurList where telephone not equals to UPDATED_TELEPHONE
        defaultLivreurShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllLivreursByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultLivreurShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the livreurList where telephone equals to UPDATED_TELEPHONE
        defaultLivreurShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllLivreursByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone is not null
        defaultLivreurShouldBeFound("telephone.specified=true");

        // Get all the livreurList where telephone is null
        defaultLivreurShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivreursByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone contains DEFAULT_TELEPHONE
        defaultLivreurShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the livreurList where telephone contains UPDATED_TELEPHONE
        defaultLivreurShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllLivreursByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where telephone does not contain DEFAULT_TELEPHONE
        defaultLivreurShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the livreurList where telephone does not contain UPDATED_TELEPHONE
        defaultLivreurShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllLivreursBySoldeIsEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde equals to DEFAULT_SOLDE
        defaultLivreurShouldBeFound("solde.equals=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde equals to UPDATED_SOLDE
        defaultLivreurShouldNotBeFound("solde.equals=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde not equals to DEFAULT_SOLDE
        defaultLivreurShouldNotBeFound("solde.notEquals=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde not equals to UPDATED_SOLDE
        defaultLivreurShouldBeFound("solde.notEquals=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsInShouldWork() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde in DEFAULT_SOLDE or UPDATED_SOLDE
        defaultLivreurShouldBeFound("solde.in=" + DEFAULT_SOLDE + "," + UPDATED_SOLDE);

        // Get all the livreurList where solde equals to UPDATED_SOLDE
        defaultLivreurShouldNotBeFound("solde.in=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde is not null
        defaultLivreurShouldBeFound("solde.specified=true");

        // Get all the livreurList where solde is null
        defaultLivreurShouldNotBeFound("solde.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde is greater than or equal to DEFAULT_SOLDE
        defaultLivreurShouldBeFound("solde.greaterThanOrEqual=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde is greater than or equal to UPDATED_SOLDE
        defaultLivreurShouldNotBeFound("solde.greaterThanOrEqual=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde is less than or equal to DEFAULT_SOLDE
        defaultLivreurShouldBeFound("solde.lessThanOrEqual=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde is less than or equal to SMALLER_SOLDE
        defaultLivreurShouldNotBeFound("solde.lessThanOrEqual=" + SMALLER_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsLessThanSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde is less than DEFAULT_SOLDE
        defaultLivreurShouldNotBeFound("solde.lessThan=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde is less than UPDATED_SOLDE
        defaultLivreurShouldBeFound("solde.lessThan=" + UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void getAllLivreursBySoldeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList where solde is greater than DEFAULT_SOLDE
        defaultLivreurShouldNotBeFound("solde.greaterThan=" + DEFAULT_SOLDE);

        // Get all the livreurList where solde is greater than SMALLER_SOLDE
        defaultLivreurShouldBeFound("solde.greaterThan=" + SMALLER_SOLDE);
    }


    @Test
    @Transactional
    public void getAllLivreursByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        livreur.setUser(user);
        livreurRepository.saveAndFlush(livreur);
        Long userId = user.getId();

        // Get all the livreurList where user equals to userId
        defaultLivreurShouldBeFound("userId.equals=" + userId);

        // Get all the livreurList where user equals to userId + 1
        defaultLivreurShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLivreurShouldBeFound(String filter) throws Exception {
        restLivreurMockMvc.perform(get("/api/livreurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())));

        // Check, that the count call also returns 1
        restLivreurMockMvc.perform(get("/api/livreurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLivreurShouldNotBeFound(String filter) throws Exception {
        restLivreurMockMvc.perform(get("/api/livreurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivreurMockMvc.perform(get("/api/livreurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLivreur() throws Exception {
        // Get the livreur
        restLivreurMockMvc.perform(get("/api/livreurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivreur() throws Exception {
        // Initialize the database
        livreurService.save(livreur);

        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();

        // Update the livreur
        Livreur updatedLivreur = livreurRepository.findById(livreur.getId()).get();
        // Disconnect from session so that the updates on updatedLivreur are not directly saved in db
        em.detach(updatedLivreur);
        updatedLivreur
            .telephone(UPDATED_TELEPHONE)
            .solde(UPDATED_SOLDE);

        restLivreurMockMvc.perform(put("/api/livreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivreur)))
            .andExpect(status().isOk());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testLivreur.getSolde()).isEqualTo(UPDATED_SOLDE);
    }

    @Test
    @Transactional
    public void updateNonExistingLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();

        // Create the Livreur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivreurMockMvc.perform(put("/api/livreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivreur() throws Exception {
        // Initialize the database
        livreurService.save(livreur);

        int databaseSizeBeforeDelete = livreurRepository.findAll().size();

        // Delete the livreur
        restLivreurMockMvc.perform(delete("/api/livreurs/{id}", livreur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
