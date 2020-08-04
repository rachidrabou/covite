package com.pfe.covite.web.rest;

import com.pfe.covite.domain.CommandeTransport;
import com.pfe.covite.domain.Notification;
import com.pfe.covite.repository.CommandeTransportRepository;
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
 * REST controller for managing {@link com.pfe.covite.domain.CommandeTransport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommandeTransportResource {

    private final Logger log = LoggerFactory.getLogger(CommandeTransportResource.class);

    private static final String ENTITY_NAME = "commandeTransport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    NotificationRepository notificationRepository;

    private final CommandeTransportRepository commandeTransportRepository;

    public CommandeTransportResource(CommandeTransportRepository commandeTransportRepository) {
        this.commandeTransportRepository = commandeTransportRepository;
    }

    /**
     * {@code POST  /commande-transports} : Create a new commandeTransport.
     *
     * @param commandeTransport the commandeTransport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeTransport, or with status {@code 400 (Bad Request)} if the commandeTransport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-transports")
    public ResponseEntity<CommandeTransport> createCommandeTransport(@Valid @RequestBody CommandeTransport commandeTransport) throws URISyntaxException {

        notificationRepository.save(new Notification("Commande service transport", commandeTransport.getClient(), commandeTransport));

        log.debug("REST request to save CommandeTransport : {}", commandeTransport);
        if (commandeTransport.getId() != null) {
            throw new BadRequestAlertException("A new commandeTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeTransport result = commandeTransportRepository.save(commandeTransport);
        return ResponseEntity.created(new URI("/api/commande-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-transports} : Updates an existing commandeTransport.
     *
     * @param commandeTransport the commandeTransport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeTransport,
     * or with status {@code 400 (Bad Request)} if the commandeTransport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeTransport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-transports")
    public ResponseEntity<CommandeTransport> updateCommandeTransport(@Valid @RequestBody CommandeTransport commandeTransport) throws URISyntaxException {
        log.debug("REST request to update CommandeTransport : {}", commandeTransport);
        if (commandeTransport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeTransport result = commandeTransportRepository.save(commandeTransport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeTransport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-transports} : get all the commandeTransports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeTransports in body.
     */
    @GetMapping("/commande-transports")
    public ResponseEntity<List<CommandeTransport>> getAllCommandeTransports(Pageable pageable) {
        log.debug("REST request to get a page of CommandeTransports");
        Page<CommandeTransport> page = commandeTransportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-transports/:id} : get the "id" commandeTransport.
     *
     * @param id the id of the commandeTransport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeTransport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-transports/{id}")
    public ResponseEntity<CommandeTransport> getCommandeTransport(@PathVariable Long id) {
        log.debug("REST request to get CommandeTransport : {}", id);
        Optional<CommandeTransport> commandeTransport = commandeTransportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commandeTransport);
    }

    /**
     * {@code DELETE  /commande-transports/:id} : delete the "id" commandeTransport.
     *
     * @param id the id of the commandeTransport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-transports/{id}")
    public ResponseEntity<Void> deleteCommandeTransport(@PathVariable Long id) {
        log.debug("REST request to delete CommandeTransport : {}", id);
        commandeTransportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
