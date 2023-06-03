package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AnomalyService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.AnomalyDTO;
import com.mycompany.myapp.service.dto.AnomalyCriteria;
import com.mycompany.myapp.service.AnomalyQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Anomaly}.
 */
@RestController
@RequestMapping("/api")
public class AnomalyResource {

    private final Logger log = LoggerFactory.getLogger(AnomalyResource.class);

    private static final String ENTITY_NAME = "anomaly";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnomalyService anomalyService;

    private final AnomalyQueryService anomalyQueryService;

    public AnomalyResource(AnomalyService anomalyService, AnomalyQueryService anomalyQueryService) {
        this.anomalyService = anomalyService;
        this.anomalyQueryService = anomalyQueryService;
    }

    /**
     * {@code POST  /anomalies} : Create a new anomaly.
     *
     * @param anomalyDTO the anomalyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anomalyDTO, or with status {@code 400 (Bad Request)} if the anomaly has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anomalies")
    public ResponseEntity<AnomalyDTO> createAnomaly(@RequestBody AnomalyDTO anomalyDTO) throws URISyntaxException {
        log.debug("REST request to save Anomaly : {}", anomalyDTO);
        if (anomalyDTO.getId() != null) {
            throw new BadRequestAlertException("A new anomaly cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnomalyDTO result = anomalyService.save(anomalyDTO);
        return ResponseEntity.created(new URI("/api/anomalies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anomalies} : Updates an existing anomaly.
     *
     * @param anomalyDTO the anomalyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anomalyDTO,
     * or with status {@code 400 (Bad Request)} if the anomalyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anomalyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anomalies")
    public ResponseEntity<AnomalyDTO> updateAnomaly(@RequestBody AnomalyDTO anomalyDTO) throws URISyntaxException {
        log.debug("REST request to update Anomaly : {}", anomalyDTO);
        if (anomalyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnomalyDTO result = anomalyService.save(anomalyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anomalyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anomalies} : get all the anomalies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anomalies in body.
     */
    @GetMapping("/anomalies")
    public ResponseEntity<List<AnomalyDTO>> getAllAnomalies(AnomalyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Anomalies by criteria: {}", criteria);
        Page<AnomalyDTO> page = anomalyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anomalies/count} : count all the anomalies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/anomalies/count")
    public ResponseEntity<Long> countAnomalies(AnomalyCriteria criteria) {
        log.debug("REST request to count Anomalies by criteria: {}", criteria);
        return ResponseEntity.ok().body(anomalyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /anomalies/:id} : get the "id" anomaly.
     *
     * @param id the id of the anomalyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anomalyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anomalies/{id}")
    public ResponseEntity<AnomalyDTO> getAnomaly(@PathVariable Long id) {
        log.debug("REST request to get Anomaly : {}", id);
        Optional<AnomalyDTO> anomalyDTO = anomalyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anomalyDTO);
    }

    /**
     * {@code DELETE  /anomalies/:id} : delete the "id" anomaly.
     *
     * @param id the id of the anomalyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anomalies/{id}")
    public ResponseEntity<Void> deleteAnomaly(@PathVariable Long id) {
        log.debug("REST request to delete Anomaly : {}", id);
        anomalyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
