package com.ig.crud.repository;

import com.ig.crud.domain.BreadCrumb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BreadCrumb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreadCrumbRepository extends JpaRepository<BreadCrumb, Long> {

}
