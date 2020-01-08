package com.ig.crud.web.rest;

import com.ig.crud.domain.BreadCrumb;
import com.ig.crud.service.BreadCrumbService;
import com.ig.crud.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.ig.crud.domain.BreadCrumb}.
 */
@RestController
@RequestMapping("/api")
public class BreadCrumbResource {

    private final Logger log = LoggerFactory.getLogger(BreadCrumbResource.class);

    private static final String ENTITY_NAME = "breadCrumb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BreadCrumbService breadCrumbService;

    public BreadCrumbResource(BreadCrumbService breadCrumbService) {
        this.breadCrumbService = breadCrumbService;
    }

    /**
     * {@code POST  /bread-crumbs} : Create a new breadCrumb.
     *
     * @param breadCrumb the breadCrumb to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new breadCrumb, or with status {@code 400 (Bad Request)} if the breadCrumb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bread-crumbs")
    public ResponseEntity<BreadCrumb> createBreadCrumb(@RequestBody BreadCrumb breadCrumb) throws URISyntaxException {
        log.debug("REST request to save BreadCrumb : {}", breadCrumb);
        if (breadCrumb.getId() != null) {
            throw new BadRequestAlertException("A new breadCrumb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BreadCrumb result = breadCrumbService.save(breadCrumb);
        return ResponseEntity.created(new URI("/api/bread-crumbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bread-crumbs} : Updates an existing breadCrumb.
     *
     * @param breadCrumb the breadCrumb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breadCrumb,
     * or with status {@code 400 (Bad Request)} if the breadCrumb is not valid,
     * or with status {@code 500 (Internal Server Error)} if the breadCrumb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bread-crumbs")
    public ResponseEntity<BreadCrumb> updateBreadCrumb(@RequestBody BreadCrumb breadCrumb) throws URISyntaxException {
        log.debug("REST request to update BreadCrumb : {}", breadCrumb);
        if (breadCrumb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BreadCrumb result = breadCrumbService.save(breadCrumb);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, breadCrumb.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bread-crumbs} : get all the breadCrumbs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of breadCrumbs in body.
     */
    @GetMapping("/bread-crumbs")
    public ResponseEntity<List<BreadCrumb>> getAllBreadCrumbs(Pageable pageable) {
        log.debug("REST request to get a page of BreadCrumbs");
        Page<BreadCrumb> page = breadCrumbService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bread-crumbs/:id} : get the "id" breadCrumb.
     *
     * @param id the id of the breadCrumb to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the breadCrumb, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bread-crumbs/{id}")
    public ResponseEntity<BreadCrumb> getBreadCrumb(@PathVariable Long id) {
        log.debug("REST request to get BreadCrumb : {}", id);
        Optional<BreadCrumb> breadCrumb = breadCrumbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(breadCrumb);
    }

    /**
     * {@code DELETE  /bread-crumbs/:id} : delete the "id" breadCrumb.
     *
     * @param id the id of the breadCrumb to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bread-crumbs/{id}")
    public ResponseEntity<Void> deleteBreadCrumb(@PathVariable Long id) {
        log.debug("REST request to delete BreadCrumb : {}", id);
        breadCrumbService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
