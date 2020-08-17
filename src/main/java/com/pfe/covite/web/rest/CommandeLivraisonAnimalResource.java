package com.pfe.covite.web.rest;

import com.pfe.covite.domain.CommandeLivraisonAnimal;
import com.pfe.covite.domain.Livreur;
import com.pfe.covite.domain.Notification;
import com.pfe.covite.domain.User;
import com.pfe.covite.repository.CommandeLivraisonAnimalRepository;
import com.pfe.covite.repository.LivreurRepository;
import com.pfe.covite.repository.NotificationRepository;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pfe.covite.domain.CommandeLivraisonAnimal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommandeLivraisonAnimalResource {

    private final Logger log = LoggerFactory.getLogger(CommandeLivraisonAnimalResource.class);

    private static final String ENTITY_NAME = "commandeLivraisonAnimal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    LivreurRepository livreurRepository;

    private final CommandeLivraisonAnimalRepository commandeLivraisonAnimalRepository;

    public CommandeLivraisonAnimalResource(CommandeLivraisonAnimalRepository commandeLivraisonAnimalRepository) {
        this.commandeLivraisonAnimalRepository = commandeLivraisonAnimalRepository;
    }

    /**
     * {@code POST  /commande-livraison-animals} : Create a new commandeLivraisonAnimal.
     *
     * @param commandeLivraisonAnimal the commandeLivraisonAnimal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeLivraisonAnimal, or with status {@code 400 (Bad Request)} if the commandeLivraisonAnimal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-livraison-animals")
    public ResponseEntity<CommandeLivraisonAnimal> createCommandeLivraisonAnimal(@Valid @RequestBody CommandeLivraisonAnimal commandeLivraisonAnimal) throws URISyntaxException {

        User livreur = commandeLivraisonAnimal.getLivreur();
        Livreur livreur1 = livreurRepository.findByUser(livreur);
        float soldeBefore = livreur1.getSolde();
        soldeBefore = (float) (soldeBefore + commandeLivraisonAnimal.getPrix());
        livreur1.setSolde(soldeBefore);
        livreurRepository.save(livreur1);
        notificationRepository.save(new Notification("Commande service transport animaux", commandeLivraisonAnimal.getClient(), commandeLivraisonAnimal, commandeLivraisonAnimal.getLivreur()));

        log.debug("REST request to save CommandeLivraisonAnimal : {}", commandeLivraisonAnimal);
        if (commandeLivraisonAnimal.getId() != null) {
            throw new BadRequestAlertException("A new commandeLivraisonAnimal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeLivraisonAnimal result = commandeLivraisonAnimalRepository.save(commandeLivraisonAnimal);
        return ResponseEntity.created(new URI("/api/commande-livraison-animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-livraison-animals} : Updates an existing commandeLivraisonAnimal.
     *
     * @param commandeLivraisonAnimal the commandeLivraisonAnimal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeLivraisonAnimal,
     * or with status {@code 400 (Bad Request)} if the commandeLivraisonAnimal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeLivraisonAnimal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-livraison-animals")
    public ResponseEntity<CommandeLivraisonAnimal> updateCommandeLivraisonAnimal(@Valid @RequestBody CommandeLivraisonAnimal commandeLivraisonAnimal) throws URISyntaxException {

        User livreur = commandeLivraisonAnimal.getLivreur();
        Livreur livreur1 = livreurRepository.findByUser(livreur);
        float soldeBefore = livreur1.getSolde();
        soldeBefore = (float) (soldeBefore + 0.20*commandeLivraisonAnimal.getPrix());
        livreur1.setSolde(soldeBefore);
        livreurRepository.save(livreur1);

        log.debug("REST request to update CommandeLivraisonAnimal : {}", commandeLivraisonAnimal);
        if (commandeLivraisonAnimal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeLivraisonAnimal result = commandeLivraisonAnimalRepository.save(commandeLivraisonAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeLivraisonAnimal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-livraison-animals} : get all the commandeLivraisonAnimals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeLivraisonAnimals in body.
     */
    @GetMapping("/commande-livraison-animals")
    public ResponseEntity<List<CommandeLivraisonAnimal>> getAllCommandeLivraisonAnimals(Pageable pageable) {
        log.debug("REST request to get a page of CommandeLivraisonAnimals");
        Page<CommandeLivraisonAnimal> page = commandeLivraisonAnimalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-livraison-animals/:id} : get the "id" commandeLivraisonAnimal.
     *
     * @param id the id of the commandeLivraisonAnimal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeLivraisonAnimal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-livraison-animals/{id}")
    public ResponseEntity<CommandeLivraisonAnimal> getCommandeLivraisonAnimal(@PathVariable Long id) {
        log.debug("REST request to get CommandeLivraisonAnimal : {}", id);
        Optional<CommandeLivraisonAnimal> commandeLivraisonAnimal = commandeLivraisonAnimalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commandeLivraisonAnimal);
    }

    /**
     * {@code DELETE  /commande-livraison-animals/:id} : delete the "id" commandeLivraisonAnimal.
     *
     * @param id the id of the commandeLivraisonAnimal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-livraison-animals/{id}")
    public ResponseEntity<Void> deleteCommandeLivraisonAnimal(@PathVariable Long id) {
        log.debug("REST request to delete CommandeLivraisonAnimal : {}", id);
        commandeLivraisonAnimalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
