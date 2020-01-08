package com.ig.crud.web.rest;

import com.ig.crud.domain.ApplicationMenu;
import com.ig.crud.service.ApplicationMenuService;
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
 * REST controller for managing {@link com.ig.crud.domain.ApplicationMenu}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationMenuResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationMenuResource.class);

    private static final String ENTITY_NAME = "applicationMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationMenuService applicationMenuService;

    public ApplicationMenuResource(ApplicationMenuService applicationMenuService) {
        this.applicationMenuService = applicationMenuService;
    }

    /**
     * {@code POST  /application-menus} : Create a new applicationMenu.
     *
     * @param applicationMenu the applicationMenu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationMenu, or with status {@code 400 (Bad Request)} if the applicationMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-menus")
    public ResponseEntity<ApplicationMenu> createApplicationMenu(@RequestBody ApplicationMenu applicationMenu) throws URISyntaxException {
        log.debug("REST request to save ApplicationMenu : {}", applicationMenu);
        if (applicationMenu.getId() != null) {
            throw new BadRequestAlertException("A new applicationMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationMenu result = applicationMenuService.save(applicationMenu);
        return ResponseEntity.created(new URI("/api/application-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-menus} : Updates an existing applicationMenu.
     *
     * @param applicationMenu the applicationMenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationMenu,
     * or with status {@code 400 (Bad Request)} if the applicationMenu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationMenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-menus")
    public ResponseEntity<ApplicationMenu> updateApplicationMenu(@RequestBody ApplicationMenu applicationMenu) throws URISyntaxException {
        log.debug("REST request to update ApplicationMenu : {}", applicationMenu);
        if (applicationMenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationMenu result = applicationMenuService.save(applicationMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationMenu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-menus} : get all the applicationMenus.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationMenus in body.
     */
    @GetMapping("/application-menus")
    public ResponseEntity<List<ApplicationMenu>> getAllApplicationMenus(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationMenus");
        Page<ApplicationMenu> page = applicationMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-menus/:id} : get the "id" applicationMenu.
     *
     * @param id the id of the applicationMenu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationMenu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-menus/{id}")
    public ResponseEntity<ApplicationMenu> getApplicationMenu(@PathVariable Long id) {
        log.debug("REST request to get ApplicationMenu : {}", id);
        Optional<ApplicationMenu> applicationMenu = applicationMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationMenu);
    }

    /**
     * {@code DELETE  /application-menus/:id} : delete the "id" applicationMenu.
     *
     * @param id the id of the applicationMenu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-menus/{id}")
    public ResponseEntity<Void> deleteApplicationMenu(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationMenu : {}", id);
        applicationMenuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
