package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.CommandeLivraisonAnimal;
import com.pfe.covite.repository.CommandeLivraisonAnimalRepository;

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

/**
 * Integration tests for the {@link CommandeLivraisonAnimalResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommandeLivraisonAnimalResourceIT {

    private static final String DEFAULT_ADRESSE_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_DEPART = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_ARRIVEE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_ARRIVEE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_HEURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_HEURE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANIMAL = "AAAAAAAAAA";
    private static final String UPDATED_ANIMAL = "BBBBBBBBBB";

    private static final String DEFAULT_MOYEN_DE_TRANSPORT = "AAAAAAAAAA";
    private static final String UPDATED_MOYEN_DE_TRANSPORT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CLIENT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    @Autowired
    private CommandeLivraisonAnimalRepository commandeLivraisonAnimalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeLivraisonAnimalMockMvc;

    private CommandeLivraisonAnimal commandeLivraisonAnimal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLivraisonAnimal createEntity(EntityManager em) {
        CommandeLivraisonAnimal commandeLivraisonAnimal = new CommandeLivraisonAnimal()
            .adresseDepart(DEFAULT_ADRESSE_DEPART)
            .adresseArrivee(DEFAULT_ADRESSE_ARRIVEE)
            .dateHeure(DEFAULT_DATE_HEURE)
            .animal(DEFAULT_ANIMAL)
            .moyenDeTransport(DEFAULT_MOYEN_DE_TRANSPORT)
            .numeroClient(DEFAULT_NUMERO_CLIENT)
            .prix(DEFAULT_PRIX);
        return commandeLivraisonAnimal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLivraisonAnimal createUpdatedEntity(EntityManager em) {
        CommandeLivraisonAnimal commandeLivraisonAnimal = new CommandeLivraisonAnimal()
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .dateHeure(UPDATED_DATE_HEURE)
            .animal(UPDATED_ANIMAL)
            .moyenDeTransport(UPDATED_MOYEN_DE_TRANSPORT)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .prix(UPDATED_PRIX);
        return commandeLivraisonAnimal;
    }

    @BeforeEach
    public void initTest() {
        commandeLivraisonAnimal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeLivraisonAnimal() throws Exception {
        int databaseSizeBeforeCreate = commandeLivraisonAnimalRepository.findAll().size();

        // Create the CommandeLivraisonAnimal
        restCommandeLivraisonAnimalMockMvc.perform(post("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isCreated());

        // Validate the CommandeLivraisonAnimal in the database
        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeLivraisonAnimal testCommandeLivraisonAnimal = commandeLivraisonAnimalList.get(commandeLivraisonAnimalList.size() - 1);
        assertThat(testCommandeLivraisonAnimal.getAdresseDepart()).isEqualTo(DEFAULT_ADRESSE_DEPART);
        assertThat(testCommandeLivraisonAnimal.getAdresseArrivee()).isEqualTo(DEFAULT_ADRESSE_ARRIVEE);
        assertThat(testCommandeLivraisonAnimal.getDateHeure()).isEqualTo(DEFAULT_DATE_HEURE);
        assertThat(testCommandeLivraisonAnimal.getAnimal()).isEqualTo(DEFAULT_ANIMAL);
        assertThat(testCommandeLivraisonAnimal.getMoyenDeTransport()).isEqualTo(DEFAULT_MOYEN_DE_TRANSPORT);
        assertThat(testCommandeLivraisonAnimal.getNumeroClient()).isEqualTo(DEFAULT_NUMERO_CLIENT);
        assertThat(testCommandeLivraisonAnimal.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createCommandeLivraisonAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeLivraisonAnimalRepository.findAll().size();

        // Create the CommandeLivraisonAnimal with an existing ID
        commandeLivraisonAnimal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeLivraisonAnimalMockMvc.perform(post("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLivraisonAnimal in the database
        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAdresseArriveeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonAnimalRepository.findAll().size();
        // set the field null
        commandeLivraisonAnimal.setAdresseArrivee(null);

        // Create the CommandeLivraisonAnimal, which fails.

        restCommandeLivraisonAnimalMockMvc.perform(post("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateHeureIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonAnimalRepository.findAll().size();
        // set the field null
        commandeLivraisonAnimal.setDateHeure(null);

        // Create the CommandeLivraisonAnimal, which fails.

        restCommandeLivraisonAnimalMockMvc.perform(post("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnimalIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeLivraisonAnimalRepository.findAll().size();
        // set the field null
        commandeLivraisonAnimal.setAnimal(null);

        // Create the CommandeLivraisonAnimal, which fails.

        restCommandeLivraisonAnimalMockMvc.perform(post("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isBadRequest());

        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandeLivraisonAnimals() throws Exception {
        // Initialize the database
        commandeLivraisonAnimalRepository.saveAndFlush(commandeLivraisonAnimal);

        // Get all the commandeLivraisonAnimalList
        restCommandeLivraisonAnimalMockMvc.perform(get("/api/commande-livraison-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLivraisonAnimal.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresseDepart").value(hasItem(DEFAULT_ADRESSE_DEPART)))
            .andExpect(jsonPath("$.[*].adresseArrivee").value(hasItem(DEFAULT_ADRESSE_ARRIVEE)))
            .andExpect(jsonPath("$.[*].dateHeure").value(hasItem(DEFAULT_DATE_HEURE.toString())))
            .andExpect(jsonPath("$.[*].animal").value(hasItem(DEFAULT_ANIMAL)))
            .andExpect(jsonPath("$.[*].moyenDeTransport").value(hasItem(DEFAULT_MOYEN_DE_TRANSPORT)))
            .andExpect(jsonPath("$.[*].numeroClient").value(hasItem(DEFAULT_NUMERO_CLIENT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCommandeLivraisonAnimal() throws Exception {
        // Initialize the database
        commandeLivraisonAnimalRepository.saveAndFlush(commandeLivraisonAnimal);

        // Get the commandeLivraisonAnimal
        restCommandeLivraisonAnimalMockMvc.perform(get("/api/commande-livraison-animals/{id}", commandeLivraisonAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeLivraisonAnimal.getId().intValue()))
            .andExpect(jsonPath("$.adresseDepart").value(DEFAULT_ADRESSE_DEPART))
            .andExpect(jsonPath("$.adresseArrivee").value(DEFAULT_ADRESSE_ARRIVEE))
            .andExpect(jsonPath("$.dateHeure").value(DEFAULT_DATE_HEURE.toString()))
            .andExpect(jsonPath("$.animal").value(DEFAULT_ANIMAL))
            .andExpect(jsonPath("$.moyenDeTransport").value(DEFAULT_MOYEN_DE_TRANSPORT))
            .andExpect(jsonPath("$.numeroClient").value(DEFAULT_NUMERO_CLIENT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeLivraisonAnimal() throws Exception {
        // Get the commandeLivraisonAnimal
        restCommandeLivraisonAnimalMockMvc.perform(get("/api/commande-livraison-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeLivraisonAnimal() throws Exception {
        // Initialize the database
        commandeLivraisonAnimalRepository.saveAndFlush(commandeLivraisonAnimal);

        int databaseSizeBeforeUpdate = commandeLivraisonAnimalRepository.findAll().size();

        // Update the commandeLivraisonAnimal
        CommandeLivraisonAnimal updatedCommandeLivraisonAnimal = commandeLivraisonAnimalRepository.findById(commandeLivraisonAnimal.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeLivraisonAnimal are not directly saved in db
        em.detach(updatedCommandeLivraisonAnimal);
        updatedCommandeLivraisonAnimal
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .dateHeure(UPDATED_DATE_HEURE)
            .animal(UPDATED_ANIMAL)
            .moyenDeTransport(UPDATED_MOYEN_DE_TRANSPORT)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .prix(UPDATED_PRIX);

        restCommandeLivraisonAnimalMockMvc.perform(put("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandeLivraisonAnimal)))
            .andExpect(status().isOk());

        // Validate the CommandeLivraisonAnimal in the database
        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeUpdate);
        CommandeLivraisonAnimal testCommandeLivraisonAnimal = commandeLivraisonAnimalList.get(commandeLivraisonAnimalList.size() - 1);
        assertThat(testCommandeLivraisonAnimal.getAdresseDepart()).isEqualTo(UPDATED_ADRESSE_DEPART);
        assertThat(testCommandeLivraisonAnimal.getAdresseArrivee()).isEqualTo(UPDATED_ADRESSE_ARRIVEE);
        assertThat(testCommandeLivraisonAnimal.getDateHeure()).isEqualTo(UPDATED_DATE_HEURE);
        assertThat(testCommandeLivraisonAnimal.getAnimal()).isEqualTo(UPDATED_ANIMAL);
        assertThat(testCommandeLivraisonAnimal.getMoyenDeTransport()).isEqualTo(UPDATED_MOYEN_DE_TRANSPORT);
        assertThat(testCommandeLivraisonAnimal.getNumeroClient()).isEqualTo(UPDATED_NUMERO_CLIENT);
        assertThat(testCommandeLivraisonAnimal.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeLivraisonAnimal() throws Exception {
        int databaseSizeBeforeUpdate = commandeLivraisonAnimalRepository.findAll().size();

        // Create the CommandeLivraisonAnimal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeLivraisonAnimalMockMvc.perform(put("/api/commande-livraison-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLivraisonAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLivraisonAnimal in the database
        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeLivraisonAnimal() throws Exception {
        // Initialize the database
        commandeLivraisonAnimalRepository.saveAndFlush(commandeLivraisonAnimal);

        int databaseSizeBeforeDelete = commandeLivraisonAnimalRepository.findAll().size();

        // Delete the commandeLivraisonAnimal
        restCommandeLivraisonAnimalMockMvc.perform(delete("/api/commande-livraison-animals/{id}", commandeLivraisonAnimal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeLivraisonAnimal> commandeLivraisonAnimalList = commandeLivraisonAnimalRepository.findAll();
        assertThat(commandeLivraisonAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
