package com.pfe.covite.web.rest;

import com.pfe.covite.domain.CommandeLivraison;
import com.pfe.covite.domain.Livreur;
import com.pfe.covite.domain.Notification;
import com.pfe.covite.domain.User;
import com.pfe.covite.repository.CommandeLivraisonRepository;
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
 * REST controller for managing {@link com.pfe.covite.domain.CommandeLivraison}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommandeLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(CommandeLivraisonResource.class);

    private static final String ENTITY_NAME = "commandeLivraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeLivraisonRepository commandeLivraisonRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    LivreurRepository livreurRepository;

    public CommandeLivraisonResource(CommandeLivraisonRepository commandeLivraisonRepository) {
        this.commandeLivraisonRepository = commandeLivraisonRepository;
    }

    /**
     * {@code POST  /commande-livraisons} : Create a new commandeLivraison.
     *
     * @param commandeLivraison the commandeLivraison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeLivraison, or with status {@code 400 (Bad Request)} if the commandeLivraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-livraisons")
    public ResponseEntity<CommandeLivraison> createCommandeLivraison(@Valid @RequestBody CommandeLivraison commandeLivraison) throws URISyntaxException {

        notificationRepository.save(new Notification("Commande service livraison", commandeLivraison.getClient(), commandeLivraison, commandeLivraison.getLivreur()));

        log.debug("REST request to save CommandeLivraison : {}", commandeLivraison);
        if (commandeLivraison.getId() != null) {
            throw new BadRequestAlertException("A new commandeLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeLivraison result = commandeLivraisonRepository.save(commandeLivraison);
        return ResponseEntity.created(new URI("/api/commande-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-livraisons} : Updates an existing commandeLivraison.
     *
     * @param commandeLivraison the commandeLivraison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeLivraison,
     * or with status {@code 400 (Bad Request)} if the commandeLivraison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeLivraison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-livraisons")
    public ResponseEntity<CommandeLivraison> updateCommandeLivraison(@Valid @RequestBody CommandeLivraison commandeLivraison) throws URISyntaxException {


        log.debug("REST request to update CommandeLivraison : {}", commandeLivraison);
        if (commandeLivraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeLivraison result = commandeLivraisonRepository.save(commandeLivraison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeLivraison.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-livraisons} : get all the commandeLivraisons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeLivraisons in body.
     */
    @GetMapping("/commande-livraisons")
    public ResponseEntity<List<CommandeLivraison>> getAllCommandeLivraisons(Pageable pageable) {
        log.debug("REST request to get a page of CommandeLivraisons");
        Page<CommandeLivraison> page = commandeLivraisonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-livraisons/:id} : get the "id" commandeLivraison.
     *
     * @param id the id of the commandeLivraison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeLivraison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-livraisons/{id}")
    public ResponseEntity<CommandeLivraison> getCommandeLivraison(@PathVariable Long id) {
        log.debug("REST request to get CommandeLivraison : {}", id);
        Optional<CommandeLivraison> commandeLivraison = commandeLivraisonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commandeLivraison);
    }

    /**
     * {@code DELETE  /commande-livraisons/:id} : delete the "id" commandeLivraison.
     *
     * @param id the id of the commandeLivraison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-livraisons/{id}")
    public ResponseEntity<Void> deleteCommandeLivraison(@PathVariable Long id) {
        log.debug("REST request to delete CommandeLivraison : {}", id);
        commandeLivraisonRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
