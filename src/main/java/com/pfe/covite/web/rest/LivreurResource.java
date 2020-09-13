package com.pfe.covite.web.rest;

import com.pfe.covite.domain.Livreur;
import com.pfe.covite.repository.NotificationRepository;
import com.pfe.covite.service.LivreurService;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;
import com.pfe.covite.service.dto.LivreurCriteria;
import com.pfe.covite.service.LivreurQueryService;

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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pfe.covite.domain.Livreur}.
 */
@RestController
@RequestMapping("/api")
public class LivreurResource {

    private final Logger log = LoggerFactory.getLogger(LivreurResource.class);

    private static final String ENTITY_NAME = "livreur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private final LivreurService livreurService;

    private final LivreurQueryService livreurQueryService;

    public LivreurResource(LivreurService livreurService, LivreurQueryService livreurQueryService) {
        this.livreurService = livreurService;
        this.livreurQueryService = livreurQueryService;
    }

    /**
     * {@code POST  /livreurs} : Create a new livreur.
     *
     * @param livreur the livreur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livreur, or with status {@code 400 (Bad Request)} if the livreur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livreurs")
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur) throws URISyntaxException {
        log.debug("REST request to save Livreur : {}", livreur);
        if (livreur.getId() != null) {
            throw new BadRequestAlertException("A new livreur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livreur result = livreurService.save(livreur);
        return ResponseEntity.created(new URI("/api/livreurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livreurs} : Updates an existing livreur.
     *
     * @param livreur the livreur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livreur,
     * or with status {@code 400 (Bad Request)} if the livreur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livreur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livreurs")
    public ResponseEntity<Livreur> updateLivreur(@RequestBody Livreur livreur) throws URISyntaxException {
        log.debug("REST request to update Livreur : {}", livreur);
        if (livreur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livreur result = livreurService.save(livreur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livreur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livreurs} : get all the livreurs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livreurs in body.
     */
    @GetMapping("/livreurs")
    public ResponseEntity<List<Livreur>> getAllLivreurs(LivreurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Livreurs by criteria: {}", criteria);
        Page<Livreur> page = livreurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /livreurs/count} : count all the livreurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/livreurs/count")
    public ResponseEntity<Long> countLivreurs(LivreurCriteria criteria) {
        log.debug("REST request to count Livreurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(livreurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /livreurs/:id} : get the "id" livreur.
     *
     * @param id the id of the livreur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livreur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livreurs/{id}")
    public ResponseEntity<Livreur> getLivreur(@PathVariable Long id) {
        log.debug("REST request to get Livreur : {}", id);
        Optional<Livreur> livreur = livreurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livreur);
    }

    /**
     * {@code DELETE  /livreurs/:id} : delete the "id" livreur.
     *
     * @param id the id of the livreur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livreurs/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        log.debug("REST request to delete Livreur : {}", id);
        livreurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
