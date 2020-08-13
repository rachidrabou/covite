package com.pfe.covite.web.rest;

import com.pfe.covite.domain.TarifLivraison;
import com.pfe.covite.service.TarifLivraisonService;
import com.pfe.covite.web.rest.errors.BadRequestAlertException;
import com.pfe.covite.service.dto.TarifLivraisonCriteria;
import com.pfe.covite.service.TarifLivraisonQueryService;

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
 * REST controller for managing {@link com.pfe.covite.domain.TarifLivraison}.
 */
@RestController
@RequestMapping("/api")
public class TarifLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(TarifLivraisonResource.class);

    private static final String ENTITY_NAME = "tarifLivraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifLivraisonService tarifLivraisonService;

    private final TarifLivraisonQueryService tarifLivraisonQueryService;

    public TarifLivraisonResource(TarifLivraisonService tarifLivraisonService, TarifLivraisonQueryService tarifLivraisonQueryService) {
        this.tarifLivraisonService = tarifLivraisonService;
        this.tarifLivraisonQueryService = tarifLivraisonQueryService;
    }

    /**
     * {@code POST  /tarif-livraisons} : Create a new tarifLivraison.
     *
     * @param tarifLivraison the tarifLivraison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifLivraison, or with status {@code 400 (Bad Request)} if the tarifLivraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-livraisons")
    public ResponseEntity<TarifLivraison> createTarifLivraison(@RequestBody TarifLivraison tarifLivraison) throws URISyntaxException {
        log.debug("REST request to save TarifLivraison : {}", tarifLivraison);
        if (tarifLivraison.getId() != null) {
            throw new BadRequestAlertException("A new tarifLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifLivraison result = tarifLivraisonService.save(tarifLivraison);
        return ResponseEntity.created(new URI("/api/tarif-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-livraisons} : Updates an existing tarifLivraison.
     *
     * @param tarifLivraison the tarifLivraison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifLivraison,
     * or with status {@code 400 (Bad Request)} if the tarifLivraison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifLivraison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-livraisons")
    public ResponseEntity<TarifLivraison> updateTarifLivraison(@RequestBody TarifLivraison tarifLivraison) throws URISyntaxException {
        log.debug("REST request to update TarifLivraison : {}", tarifLivraison);
        if (tarifLivraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifLivraison result = tarifLivraisonService.save(tarifLivraison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifLivraison.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-livraisons} : get all the tarifLivraisons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifLivraisons in body.
     */
    @GetMapping("/tarif-livraisons")
    public ResponseEntity<List<TarifLivraison>> getAllTarifLivraisons(TarifLivraisonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TarifLivraisons by criteria: {}", criteria);
        Page<TarifLivraison> page = tarifLivraisonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarif-livraisons/count} : count all the tarifLivraisons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tarif-livraisons/count")
    public ResponseEntity<Long> countTarifLivraisons(TarifLivraisonCriteria criteria) {
        log.debug("REST request to count TarifLivraisons by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarifLivraisonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarif-livraisons/:id} : get the "id" tarifLivraison.
     *
     * @param id the id of the tarifLivraison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifLivraison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-livraisons/{id}")
    public ResponseEntity<TarifLivraison> getTarifLivraison(@PathVariable Long id) {
        log.debug("REST request to get TarifLivraison : {}", id);
        Optional<TarifLivraison> tarifLivraison = tarifLivraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarifLivraison);
    }

    /**
     * {@code DELETE  /tarif-livraisons/:id} : delete the "id" tarifLivraison.
     *
     * @param id the id of the tarifLivraison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-livraisons/{id}")
    public ResponseEntity<Void> deleteTarifLivraison(@PathVariable Long id) {
        log.debug("REST request to delete TarifLivraison : {}", id);
        tarifLivraisonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
