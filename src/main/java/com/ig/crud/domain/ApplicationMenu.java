package com.ig.crud.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ApplicationMenu.
 */
@Entity
@Table(name = "application_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "english_text")
    private String englishText;

    @Column(name = "french_path")
    private String frenchPath;

    @Column(name = "role")
    private Long role;

    @Column(name = "jhi_order")
    private Long order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public ApplicationMenu parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public ApplicationMenu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishText() {
        return englishText;
    }

    public ApplicationMenu englishText(String englishText) {
        this.englishText = englishText;
        return this;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public String getFrenchPath() {
        return frenchPath;
    }

    public ApplicationMenu frenchPath(String frenchPath) {
        this.frenchPath = frenchPath;
        return this;
    }

    public void setFrenchPath(String frenchPath) {
        this.frenchPath = frenchPath;
    }

    public Long getRole() {
        return role;
    }

    public ApplicationMenu role(Long role) {
        this.role = role;
        return this;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getOrder() {
        return order;
    }

    public ApplicationMenu order(Long order) {
        this.order = order;
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationMenu)) {
            return false;
        }
        return id != null && id.equals(((ApplicationMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationMenu{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", name='" + getName() + "'" +
            ", englishText='" + getEnglishText() + "'" +
            ", frenchPath='" + getFrenchPath() + "'" +
            ", role=" + getRole() +
            ", order=" + getOrder() +
            "}";
    }
}
