package com.pfe.covite.web.rest;

import com.pfe.covite.domain.TarifTransportAnimal;
import com.pfe.covite.service.TarifTransportAnimalService;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;
import com.pfe.covite.service.dto.TarifTransportAnimalCriteria;
import com.pfe.covite.service.TarifTransportAnimalQueryService;

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
 * REST controller for managing {@link com.pfe.covite.domain.TarifTransportAnimal}.
 */
@RestController
@RequestMapping("/api")
public class TarifTransportAnimalResource {

    private final Logger log = LoggerFactory.getLogger(TarifTransportAnimalResource.class);

    private static final String ENTITY_NAME = "tarifTransportAnimal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifTransportAnimalService tarifTransportAnimalService;

    private final TarifTransportAnimalQueryService tarifTransportAnimalQueryService;

    public TarifTransportAnimalResource(TarifTransportAnimalService tarifTransportAnimalService, TarifTransportAnimalQueryService tarifTransportAnimalQueryService) {
        this.tarifTransportAnimalService = tarifTransportAnimalService;
        this.tarifTransportAnimalQueryService = tarifTransportAnimalQueryService;
    }

    /**
     * {@code POST  /tarif-transport-animals} : Create a new tarifTransportAnimal.
     *
     * @param tarifTransportAnimal the tarifTransportAnimal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifTransportAnimal, or with status {@code 400 (Bad Request)} if the tarifTransportAnimal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-transport-animals")
    public ResponseEntity<TarifTransportAnimal> createTarifTransportAnimal(@RequestBody TarifTransportAnimal tarifTransportAnimal) throws URISyntaxException {
        log.debug("REST request to save TarifTransportAnimal : {}", tarifTransportAnimal);
        if (tarifTransportAnimal.getId() != null) {
            throw new BadRequestAlertException("A new tarifTransportAnimal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifTransportAnimal result = tarifTransportAnimalService.save(tarifTransportAnimal);
        return ResponseEntity.created(new URI("/api/tarif-transport-animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-transport-animals} : Updates an existing tarifTransportAnimal.
     *
     * @param tarifTransportAnimal the tarifTransportAnimal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifTransportAnimal,
     * or with status {@code 400 (Bad Request)} if the tarifTransportAnimal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifTransportAnimal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-transport-animals")
    public ResponseEntity<TarifTransportAnimal> updateTarifTransportAnimal(@RequestBody TarifTransportAnimal tarifTransportAnimal) throws URISyntaxException {
        log.debug("REST request to update TarifTransportAnimal : {}", tarifTransportAnimal);
        if (tarifTransportAnimal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifTransportAnimal result = tarifTransportAnimalService.save(tarifTransportAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifTransportAnimal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-transport-animals} : get all the tarifTransportAnimals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifTransportAnimals in body.
     */
    @GetMapping("/tarif-transport-animals")
    public ResponseEntity<List<TarifTransportAnimal>> getAllTarifTransportAnimals(TarifTransportAnimalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TarifTransportAnimals by criteria: {}", criteria);
        Page<TarifTransportAnimal> page = tarifTransportAnimalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarif-transport-animals/count} : count all the tarifTransportAnimals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tarif-transport-animals/count")
    public ResponseEntity<Long> countTarifTransportAnimals(TarifTransportAnimalCriteria criteria) {
        log.debug("REST request to count TarifTransportAnimals by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarifTransportAnimalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarif-transport-animals/:id} : get the "id" tarifTransportAnimal.
     *
     * @param id the id of the tarifTransportAnimal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifTransportAnimal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-transport-animals/{id}")
    public ResponseEntity<TarifTransportAnimal> getTarifTransportAnimal(@PathVariable Long id) {
        log.debug("REST request to get TarifTransportAnimal : {}", id);
        Optional<TarifTransportAnimal> tarifTransportAnimal = tarifTransportAnimalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarifTransportAnimal);
    }

    /**
     * {@code DELETE  /tarif-transport-animals/:id} : delete the "id" tarifTransportAnimal.
     *
     * @param id the id of the tarifTransportAnimal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-transport-animals/{id}")
    public ResponseEntity<Void> deleteTarifTransportAnimal(@PathVariable Long id) {
        log.debug("REST request to delete TarifTransportAnimal : {}", id);
        tarifTransportAnimalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
