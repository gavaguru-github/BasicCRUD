package com.ig.crud.service;

import com.ig.crud.domain.ApplicationMenu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ApplicationMenu}.
 */
public interface ApplicationMenuService {

    /**
     * Save a applicationMenu.
     *
     * @param applicationMenu the entity to save.
     * @return the persisted entity.
     */
    ApplicationMenu save(ApplicationMenu applicationMenu);

    /**
     * Get all the applicationMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationMenu> findAll(Pageable pageable);


    /**
     * Get the "id" applicationMenu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationMenu> findOne(Long id);

    /**
     * Delete the "id" applicationMenu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
