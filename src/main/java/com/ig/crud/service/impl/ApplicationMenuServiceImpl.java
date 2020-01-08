package com.ig.crud.service.impl;

import com.ig.crud.service.ApplicationMenuService;
import com.ig.crud.domain.ApplicationMenu;
import com.ig.crud.repository.ApplicationMenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationMenu}.
 */
@Service
@Transactional
public class ApplicationMenuServiceImpl implements ApplicationMenuService {

    private final Logger log = LoggerFactory.getLogger(ApplicationMenuServiceImpl.class);

    private final ApplicationMenuRepository applicationMenuRepository;

    public ApplicationMenuServiceImpl(ApplicationMenuRepository applicationMenuRepository) {
        this.applicationMenuRepository = applicationMenuRepository;
    }

    /**
     * Save a applicationMenu.
     *
     * @param applicationMenu the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationMenu save(ApplicationMenu applicationMenu) {
        log.debug("Request to save ApplicationMenu : {}", applicationMenu);
        return applicationMenuRepository.save(applicationMenu);
    }

    /**
     * Get all the applicationMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationMenu> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationMenus");
        return applicationMenuRepository.findAll(pageable);
    }


    /**
     * Get one applicationMenu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationMenu> findOne(Long id) {
        log.debug("Request to get ApplicationMenu : {}", id);
        return applicationMenuRepository.findById(id);
    }

    /**
     * Delete the applicationMenu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationMenu : {}", id);
        applicationMenuRepository.deleteById(id);
    }
}
