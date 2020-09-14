package com.pfe.covite.web.rest;

import com.pfe.covite.domain.Vehicule;
import com.pfe.covite.service.VehiculeService;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;
import com.pfe.covite.service.dto.VehiculeCriteria;
import com.pfe.covite.service.VehiculeQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.pfe.covite.domain.Vehicule}.
 */
@RestController
@RequestMapping("/api")
public class VehiculeResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeResource.class);

    private static final String ENTITY_NAME = "vehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeService vehiculeService;

    private final VehiculeQueryService vehiculeQueryService;

    public VehiculeResource(VehiculeService vehiculeService, VehiculeQueryService vehiculeQueryService) {
        this.vehiculeService = vehiculeService;
        this.vehiculeQueryService = vehiculeQueryService;
    }

    /**
     * {@code POST  /vehicules} : Create a new vehicule.
     *
     * @param vehicule the vehicule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicule, or with status {@code 400 (Bad Request)} if the vehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicules")
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) throws URISyntaxException {
        log.debug("REST request to save Vehicule : {}", vehicule);
        if (vehicule.getId() != null) {
            throw new BadRequestAlertException("A new vehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vehicule result = vehiculeService.save(vehicule);
        return ResponseEntity.created(new URI("/api/vehicules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicules} : Updates an existing vehicule.
     *
     * @param vehicule the vehicule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicule,
     * or with status {@code 400 (Bad Request)} if the vehicule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicules")
    public ResponseEntity<Vehicule> updateVehicule(@RequestBody Vehicule vehicule) throws URISyntaxException {
        log.debug("REST request to update Vehicule : {}", vehicule);
        if (vehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vehicule result = vehiculeService.save(vehicule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicules} : get all the vehicules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicules in body.
     */
    @GetMapping("/vehicules")
    public ResponseEntity<List<Vehicule>> getAllVehicules(VehiculeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vehicules by criteria: {}", criteria);
        Page<Vehicule> page = vehiculeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicules/count} : count all the vehicules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicules/count")
    public ResponseEntity<Long> countVehicules(VehiculeCriteria criteria) {
        log.debug("REST request to count Vehicules by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehiculeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicules/:id} : get the "id" vehicule.
     *
     * @param id the id of the vehicule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicules/{id}")
    public ResponseEntity<Vehicule> getVehicule(@PathVariable Long id) {
        log.debug("REST request to get Vehicule : {}", id);
        Optional<Vehicule> vehicule = vehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicule);
    }

    /**
     * {@code DELETE  /vehicules/:id} : delete the "id" vehicule.
     *
     * @param id the id of the vehicule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicules/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        log.debug("REST request to delete Vehicule : {}", id);
        vehiculeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
