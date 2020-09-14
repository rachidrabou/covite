package com.pfe.covite.web.rest;

import com.pfe.covite.CoviteApp;
import com.pfe.covite.domain.CommandeTransport;
import com.pfe.covite.repository.CommandeTransportRepository;

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
 * Integration tests for the {@link CommandeTransportResource} REST controller.
 */
@SpringBootTest(classes = CoviteApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommandeTransportResourceIT {

    private static final String DEFAULT_ADRESSE_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_DEPART = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_ARRIVEE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_ARRIVEE = "BBBBBBBBBB";

    private static final String DEFAULT_MOYEN_DE_TRANSPORT = "AAAAAAAAAA";
    private static final String UPDATED_MOYEN_DE_TRANSPORT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Integer DEFAULT_NOMBRE_DE_PERSONNES = 1;
    private static final Integer UPDATED_NOMBRE_DE_PERSONNES = 2;

    private static final String DEFAULT_NUMERO_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CLIENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEHEURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEHEURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommandeTransportRepository commandeTransportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeTransportMockMvc;

    private CommandeTransport commandeTransport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeTransport createEntity(EntityManager em) {
        CommandeTransport commandeTransport = new CommandeTransport()
            .adresseDepart(DEFAULT_ADRESSE_DEPART)
            .adresseArrivee(DEFAULT_ADRESSE_ARRIVEE)
            .moyenDeTransport(DEFAULT_MOYEN_DE_TRANSPORT)
            .prix(DEFAULT_PRIX)
            .nombreDePersonnes(DEFAULT_NOMBRE_DE_PERSONNES)
            .numeroClient(DEFAULT_NUMERO_CLIENT)
            .dateheure(DEFAULT_DATEHEURE);
        return commandeTransport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeTransport createUpdatedEntity(EntityManager em) {
        CommandeTransport commandeTransport = new CommandeTransport()
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .moyenDeTransport(UPDATED_MOYEN_DE_TRANSPORT)
            .prix(UPDATED_PRIX)
            .nombreDePersonnes(UPDATED_NOMBRE_DE_PERSONNES)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .dateheure(UPDATED_DATEHEURE);
        return commandeTransport;
    }

    @BeforeEach
    public void initTest() {
        commandeTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeTransport() throws Exception {
        int databaseSizeBeforeCreate = commandeTransportRepository.findAll().size();

        // Create the CommandeTransport
        restCommandeTransportMockMvc.perform(post("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeTransport)))
            .andExpect(status().isCreated());

        // Validate the CommandeTransport in the database
        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeTransport testCommandeTransport = commandeTransportList.get(commandeTransportList.size() - 1);
        assertThat(testCommandeTransport.getAdresseDepart()).isEqualTo(DEFAULT_ADRESSE_DEPART);
        assertThat(testCommandeTransport.getAdresseArrivee()).isEqualTo(DEFAULT_ADRESSE_ARRIVEE);
        assertThat(testCommandeTransport.getMoyenDeTransport()).isEqualTo(DEFAULT_MOYEN_DE_TRANSPORT);
        assertThat(testCommandeTransport.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testCommandeTransport.getNombreDePersonnes()).isEqualTo(DEFAULT_NOMBRE_DE_PERSONNES);
        assertThat(testCommandeTransport.getNumeroClient()).isEqualTo(DEFAULT_NUMERO_CLIENT);
        assertThat(testCommandeTransport.getDateheure()).isEqualTo(DEFAULT_DATEHEURE);
    }

    @Test
    @Transactional
    public void createCommandeTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeTransportRepository.findAll().size();

        // Create the CommandeTransport with an existing ID
        commandeTransport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeTransportMockMvc.perform(post("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeTransport)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeTransport in the database
        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAdresseDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeTransportRepository.findAll().size();
        // set the field null
        commandeTransport.setAdresseDepart(null);

        // Create the CommandeTransport, which fails.

        restCommandeTransportMockMvc.perform(post("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeTransport)))
            .andExpect(status().isBadRequest());

        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseArriveeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeTransportRepository.findAll().size();
        // set the field null
        commandeTransport.setAdresseArrivee(null);

        // Create the CommandeTransport, which fails.

        restCommandeTransportMockMvc.perform(post("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeTransport)))
            .andExpect(status().isBadRequest());

        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandeTransports() throws Exception {
        // Initialize the database
        commandeTransportRepository.saveAndFlush(commandeTransport);

        // Get all the commandeTransportList
        restCommandeTransportMockMvc.perform(get("/api/commande-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresseDepart").value(hasItem(DEFAULT_ADRESSE_DEPART)))
            .andExpect(jsonPath("$.[*].adresseArrivee").value(hasItem(DEFAULT_ADRESSE_ARRIVEE)))
            .andExpect(jsonPath("$.[*].moyenDeTransport").value(hasItem(DEFAULT_MOYEN_DE_TRANSPORT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreDePersonnes").value(hasItem(DEFAULT_NOMBRE_DE_PERSONNES)))
            .andExpect(jsonPath("$.[*].numeroClient").value(hasItem(DEFAULT_NUMERO_CLIENT)))
            .andExpect(jsonPath("$.[*].dateheure").value(hasItem(sameInstant(DEFAULT_DATEHEURE))));
    }
    
    @Test
    @Transactional
    public void getCommandeTransport() throws Exception {
        // Initialize the database
        commandeTransportRepository.saveAndFlush(commandeTransport);

        // Get the commandeTransport
        restCommandeTransportMockMvc.perform(get("/api/commande-transports/{id}", commandeTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeTransport.getId().intValue()))
            .andExpect(jsonPath("$.adresseDepart").value(DEFAULT_ADRESSE_DEPART))
            .andExpect(jsonPath("$.adresseArrivee").value(DEFAULT_ADRESSE_ARRIVEE))
            .andExpect(jsonPath("$.moyenDeTransport").value(DEFAULT_MOYEN_DE_TRANSPORT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.nombreDePersonnes").value(DEFAULT_NOMBRE_DE_PERSONNES))
            .andExpect(jsonPath("$.numeroClient").value(DEFAULT_NUMERO_CLIENT))
            .andExpect(jsonPath("$.dateheure").value(sameInstant(DEFAULT_DATEHEURE)));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeTransport() throws Exception {
        // Get the commandeTransport
        restCommandeTransportMockMvc.perform(get("/api/commande-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeTransport() throws Exception {
        // Initialize the database
        commandeTransportRepository.saveAndFlush(commandeTransport);

        int databaseSizeBeforeUpdate = commandeTransportRepository.findAll().size();

        // Update the commandeTransport
        CommandeTransport updatedCommandeTransport = commandeTransportRepository.findById(commandeTransport.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeTransport are not directly saved in db
        em.detach(updatedCommandeTransport);
        updatedCommandeTransport
            .adresseDepart(UPDATED_ADRESSE_DEPART)
            .adresseArrivee(UPDATED_ADRESSE_ARRIVEE)
            .moyenDeTransport(UPDATED_MOYEN_DE_TRANSPORT)
            .prix(UPDATED_PRIX)
            .nombreDePersonnes(UPDATED_NOMBRE_DE_PERSONNES)
            .numeroClient(UPDATED_NUMERO_CLIENT)
            .dateheure(UPDATED_DATEHEURE);

        restCommandeTransportMockMvc.perform(put("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandeTransport)))
            .andExpect(status().isOk());

        // Validate the CommandeTransport in the database
        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeUpdate);
        CommandeTransport testCommandeTransport = commandeTransportList.get(commandeTransportList.size() - 1);
        assertThat(testCommandeTransport.getAdresseDepart()).isEqualTo(UPDATED_ADRESSE_DEPART);
        assertThat(testCommandeTransport.getAdresseArrivee()).isEqualTo(UPDATED_ADRESSE_ARRIVEE);
        assertThat(testCommandeTransport.getMoyenDeTransport()).isEqualTo(UPDATED_MOYEN_DE_TRANSPORT);
        assertThat(testCommandeTransport.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testCommandeTransport.getNombreDePersonnes()).isEqualTo(UPDATED_NOMBRE_DE_PERSONNES);
        assertThat(testCommandeTransport.getNumeroClient()).isEqualTo(UPDATED_NUMERO_CLIENT);
        assertThat(testCommandeTransport.getDateheure()).isEqualTo(UPDATED_DATEHEURE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeTransport() throws Exception {
        int databaseSizeBeforeUpdate = commandeTransportRepository.findAll().size();

        // Create the CommandeTransport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeTransportMockMvc.perform(put("/api/commande-transports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeTransport)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeTransport in the database
        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeTransport() throws Exception {
        // Initialize the database
        commandeTransportRepository.saveAndFlush(commandeTransport);

        int databaseSizeBeforeDelete = commandeTransportRepository.findAll().size();

        // Delete the commandeTransport
        restCommandeTransportMockMvc.perform(delete("/api/commande-transports/{id}", commandeTransport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeTransport> commandeTransportList = commandeTransportRepository.findAll();
        assertThat(commandeTransportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
