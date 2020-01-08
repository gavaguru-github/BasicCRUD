package com.ig.crud.service;

import com.ig.crud.domain.BreadCrumb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BreadCrumb}.
 */
public interface BreadCrumbService {

    /**
     * Save a breadCrumb.
     *
     * @param breadCrumb the entity to save.
     * @return the persisted entity.
     */
    BreadCrumb save(BreadCrumb breadCrumb);

    /**
     * Get all the breadCrumbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BreadCrumb> findAll(Pageable pageable);


    /**
     * Get the "id" breadCrumb.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BreadCrumb> findOne(Long id);

    /**
     * Delete the "id" breadCrumb.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
