package com.ig.crud.repository;

import com.ig.crud.domain.ApplicationMenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationMenuRepository extends JpaRepository<ApplicationMenu, Long> {

}
