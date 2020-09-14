package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.CommandeLivraison;
import com.pfe.covite.repository.CommandeLivraisonRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.pfe.covite.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommandeLivraisonResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommandeLivraisonResourceIT {

    private static final String DEFAULT_ADRESSE_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_DEPART = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_ARRIVEE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_ARRIVEE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final String DEFAULT_NUMERO_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_OBJET = "AAAAAAAAAA";
    private static final String UPDATED_OBJET = "BBBBBBBBBB";

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEHEURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEHEURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CVALIDER = false;
    private static final Boolean UPDATED_CVALIDER = true;

    @Autowired
    private CommandeLivraisonRepository commandeLivraisonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeLivraisonMockMvc;

    private CommandeLivraison commandeLivraison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLivraison createEntity(EntityManager em) {
        CommandeLivraison commandeLivraison = new CommandeLivraison()
            .adresseDepart(DEFAULT_ADRESSE_DEPART)
            .adresseArrivee(DEFAULT_ADRESSE_ARRIVEE)
            .prix(DEFAULT_PRIX)
            .numeroClient(DEFAULT_NUMERO_CLIENT)
            .objet(DEFAULT_OBJET)
            .cin(DEFAULT_CIN)
            .dateheure(DEFAULT_DATEHEURE)
            .cvalider(DEFAULT_CVALIDER);
        return commandeLivraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLivraison createUpdatedEntity(EntityManager em) {
        CommandeLivraison commandeLivraison = new CommandeLivraison()
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .prix(UPDATED_PRIX)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .objet(UPDATED_OBJET)
            .cin(UPDATED_CIN)
            .dateheure(UPDATED_DATEHEURE)
            .cvalider(UPDATED_CVALIDER);
        return commandeLivraison;
    }

    @BeforeEach
    public void initTest() {
        commandeLivraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeLivraison() throws Exception {
        int databaseSizeBeforeCreate = commandeLivraisonRepository.findAll().size();

        // Create the CommandeLivraison
        restCommandeLivraisonMockMvc.perform(post("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isCreated());

        // Validate the CommandeLivraison in the database
        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeLivraison testCommandeLivraison = commandeLivraisonList.get(commandeLivraisonList.size() - 1);
        assertThat(testCommandeLivraison.getAdresseDepart()).isEqualTo(DEFAULT_ADRESSE_DEPART);
        assertThat(testCommandeLivraison.getAdresseArrivee()).isEqualTo(DEFAULT_ADRESSE_ARRIVEE);
        assertThat(testCommandeLivraison.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testCommandeLivraison.getNumeroClient()).isEqualTo(DEFAULT_NUMERO_CLIENT);
        assertThat(testCommandeLivraison.getObjet()).isEqualTo(DEFAULT_OBJET);
        assertThat(testCommandeLivraison.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testCommandeLivraison.getDateheure()).isEqualTo(DEFAULT_DATEHEURE);
        assertThat(testCommandeLivraison.isCvalider()).isEqualTo(DEFAULT_CVALIDER);
    }

    @Test
    @Transactional
    public void createCommandeLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeLivraisonRepository.findAll().size();

        // Create the CommandeLivraison with an existing ID
        commandeLivraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeLivraisonMockMvc.perform(post("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLivraison in the database
        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAdresseDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonRepository.findAll().size();
        // set the field null
        commandeLivraison.setAdresseDepart(null);

        // Create the CommandeLivraison, which fails.

        restCommandeLivraisonMockMvc.perform(post("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseArriveeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonRepository.findAll().size();
        // set the field null
        commandeLivraison.setAdresseArrivee(null);

        // Create the CommandeLivraison, which fails.

        restCommandeLivraisonMockMvc.perform(post("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObjetIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonRepository.findAll().size();
        // set the field null
        commandeLivraison.setObjet(null);

        // Create the CommandeLivraison, which fails.

        restCommandeLivraisonMockMvc.perform(post("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandeLivraisons() throws Exception {
        // Initialize the database
        commandeLivraisonRepository.saveAndFlush(commandeLivraison);

        // Get all the commandeLivraisonList
        restCommandeLivraisonMockMvc.perform(get("/api/commande-livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresseDepart").value(hasItem(DEFAULT_ADRESSE_DEPART)))
            .andExpect(jsonPath("$.[*].adresseArrivee").value(hasItem(DEFAULT_ADRESSE_ARRIVEE)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].numeroClient").value(hasItem(DEFAULT_NUMERO_CLIENT)))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].dateheure").value(hasItem(sameInstant(DEFAULT_DATEHEURE))))
            .andExpect(jsonPath("$.[*].cvalider").value(hasItem(DEFAULT_CVALIDER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCommandeLivraison() throws Exception {
        // Initialize the database
        commandeLivraisonRepository.saveAndFlush(commandeLivraison);

        // Get the commandeLivraison
        restCommandeLivraisonMockMvc.perform(get("/api/commande-livraisons/{id}", commandeLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeLivraison.getId().intValue()))
            .andExpect(jsonPath("$.adresseDepart").value(DEFAULT_ADRESSE_DEPART))
            .andExpect(jsonPath("$.adresseArrivee").value(DEFAULT_ADRESSE_ARRIVEE))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.numeroClient").value(DEFAULT_NUMERO_CLIENT))
            .andExpect(jsonPath("$.objet").value(DEFAULT_OBJET))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.dateheure").value(sameInstant(DEFAULT_DATEHEURE)))
            .andExpect(jsonPath("$.cvalider").value(DEFAULT_CVALIDER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeLivraison() throws Exception {
        // Get the commandeLivraison
        restCommandeLivraisonMockMvc.perform(get("/api/commande-livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeLivraison() throws Exception {
        // Initialize the database
        commandeLivraisonRepository.saveAndFlush(commandeLivraison);

        int databaseSizeBeforeUpdate = commandeLivraisonRepository.findAll().size();

        // Update the commandeLivraison
        CommandeLivraison updatedCommandeLivraison = commandeLivraisonRepository.findById(commandeLivraison.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeLivraison are not directly saved in db
        em.detach(updatedCommandeLivraison);
        updatedCommandeLivraison
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .prix(UPDATED_PRIX)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .objet(UPDATED_OBJET)
            .cin(UPDATED_CIN)
            .dateheure(UPDATED_DATEHEURE)
            .cvalider(UPDATED_CVALIDER);

        restCommandeLivraisonMockMvc.perform(put("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandeLivraison)))
            .andExpect(status().isOk());

        // Validate the CommandeLivraison in the database
        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeUpdate);
        CommandeLivraison testCommandeLivraison = commandeLivraisonList.get(commandeLivraisonList.size() - 1);
        assertThat(testCommandeLivraison.getAdresseDepart()).isEqualTo(UPDATED_ADRESSE_DEPART);
        assertThat(testCommandeLivraison.getAdresseArrivee()).isEqualTo(UPDATED_ADRESSE_ARRIVEE);
        assertThat(testCommandeLivraison.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testCommandeLivraison.getNumeroClient()).isEqualTo(UPDATED_NUMERO_CLIENT);
        assertThat(testCommandeLivraison.getObjet()).isEqualTo(UPDATED_OBJET);
        assertThat(testCommandeLivraison.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testCommandeLivraison.getDateheure()).isEqualTo(UPDATED_DATEHEURE);
        assertThat(testCommandeLivraison.isCvalider()).isEqualTo(UPDATED_CVALIDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeLivraison() throws Exception {
        int databaseSizeBeforeUpdate = commandeLivraisonRepository.findAll().size();

        // Create the CommandeLivraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeLivraisonMockMvc.perform(put("/api/commande-livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLivraison in the database
        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeLivraison() throws Exception {
        // Initialize the database
        commandeLivraisonRepository.saveAndFlush(commandeLivraison);

        int databaseSizeBeforeDelete = commandeLivraisonRepository.findAll().size();

        // Delete the commandeLivraison
        restCommandeLivraisonMockMvc.perform(delete("/api/commande-livraisons/{id}", commandeLivraison.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeLivraison> commandeLivraisonList = commandeLivraisonRepository.findAll();
        assertThat(commandeLivraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
