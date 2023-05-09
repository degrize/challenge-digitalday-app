package com.challenge.digitaldayapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.challenge.digitaldayapp.domain.CategorieArticle} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieArticleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieArticleDTO)) {
            return false;
        }

        CategorieArticleDTO categorieArticleDTO = (CategorieArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieArticleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieArticleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
