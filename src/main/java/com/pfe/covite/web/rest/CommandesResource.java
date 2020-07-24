package com.pfe.covite.web.rest;

import com.pfe.covite.domain.Commandes;
import com.pfe.covite.service.CommandesService;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;
import com.pfe.covite.service.dto.CommandesCriteria;
import com.pfe.covite.service.CommandesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pfe.covite.domain.Commandes}.
 */
@RestController
@RequestMapping("/api")
public class CommandesResource {

    private final Logger log = LoggerFactory.getLogger(CommandesResource.class);

    private static final String ENTITY_NAME = "commandes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandesService commandesService;

    private final CommandesQueryService commandesQueryService;

    public CommandesResource(CommandesService commandesService, CommandesQueryService commandesQueryService) {
        this.commandesService = commandesService;
        this.commandesQueryService = commandesQueryService;
    }

    /**
     * {@code POST  /commandes} : Create a new commandes.
     *
     * @param commandes the commandes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandes, or with status {@code 400 (Bad Request)} if the commandes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commandes")
    public ResponseEntity<Commandes> createCommandes(@RequestBody Commandes commandes) throws URISyntaxException {
        log.debug("REST request to save Commandes : {}", commandes);
        if (commandes.getId() != null) {
            throw new BadRequestAlertException("A new commandes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commandes result = commandesService.save(commandes);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commandes} : Updates an existing commandes.
     *
     * @param commandes the commandes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandes,
     * or with status {@code 400 (Bad Request)} if the commandes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commandes")
    public ResponseEntity<Commandes> updateCommandes(@RequestBody Commandes commandes) throws URISyntaxException {
        log.debug("REST request to update Commandes : {}", commandes);
        if (commandes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commandes result = commandesService.save(commandes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commandes} : get all the commandes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandes in body.
     */
    @GetMapping("/commandes")
    public ResponseEntity<List<Commandes>> getAllCommandes(CommandesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commandes by criteria: {}", criteria);
        Page<Commandes> page = commandesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commandes/count} : count all the commandes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commandes/count")
    public ResponseEntity<Long> countCommandes(CommandesCriteria criteria) {
        log.debug("REST request to count Commandes by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commandes/:id} : get the "id" commandes.
     *
     * @param id the id of the commandes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commandes/{id}")
    public ResponseEntity<Commandes> getCommandes(@PathVariable Long id) {
        log.debug("REST request to get Commandes : {}", id);
        Optional<Commandes> commandes = commandesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandes);
    }

    /**
     * {@code DELETE  /commandes/:id} : delete the "id" commandes.
     *
     * @param id the id of the commandes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<Void> deleteCommandes(@PathVariable Long id) {
        log.debug("REST request to delete Commandes : {}", id);
        commandesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
