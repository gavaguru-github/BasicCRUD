package com.ig.crud.service.impl;

import com.ig.crud.service.BreadCrumbService;
import com.ig.crud.domain.BreadCrumb;
import com.ig.crud.repository.BreadCrumbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BreadCrumb}.
 */
@Service
@Transactional
public class BreadCrumbServiceImpl implements BreadCrumbService {

    private final Logger log = LoggerFactory.getLogger(BreadCrumbServiceImpl.class);

    private final BreadCrumbRepository breadCrumbRepository;

    public BreadCrumbServiceImpl(BreadCrumbRepository breadCrumbRepository) {
        this.breadCrumbRepository = breadCrumbRepository;
    }

    /**
     * Save a breadCrumb.
     *
     * @param breadCrumb the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BreadCrumb save(BreadCrumb breadCrumb) {
        log.debug("Request to save BreadCrumb : {}", breadCrumb);
        return breadCrumbRepository.save(breadCrumb);
    }

    /**
     * Get all the breadCrumbs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BreadCrumb> findAll(Pageable pageable) {
        log.debug("Request to get all BreadCrumbs");
        return breadCrumbRepository.findAll(pageable);
    }


    /**
     * Get one breadCrumb by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BreadCrumb> findOne(Long id) {
        log.debug("Request to get BreadCrumb : {}", id);
        return breadCrumbRepository.findById(id);
    }

    /**
     * Delete the breadCrumb by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BreadCrumb : {}", id);
        breadCrumbRepository.deleteById(id);
    }
}
