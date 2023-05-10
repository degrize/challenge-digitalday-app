package com.challenge.digitaldayapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.challenge.digitaldayapp.domain.Quartier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuartierDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private Set<VilleDTO> villes = new HashSet<>();

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

    public Set<VilleDTO> getVilles() {
        return villes;
    }

    public void setVilles(Set<VilleDTO> villes) {
        this.villes = villes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuartierDTO)) {
            return false;
        }

        QuartierDTO quartierDTO = (QuartierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quartierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuartierDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", villes=" + getVilles() +
            "}";
    }
}
