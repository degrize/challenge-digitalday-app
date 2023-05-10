package com.challenge.digitaldayapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.challenge.digitaldayapp.domain.Vente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VenteDTO implements Serializable {

    private Long id;

    private Instant dateVente;

    private Double remiseRabais;

    private Double montantRecu;

    private Boolean aCredit;

    private ClientDTO client;

    private ArticleDTO article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateVente() {
        return dateVente;
    }

    public void setDateVente(Instant dateVente) {
        this.dateVente = dateVente;
    }

    public Double getRemiseRabais() {
        return remiseRabais;
    }

    public void setRemiseRabais(Double remiseRabais) {
        this.remiseRabais = remiseRabais;
    }

    public Double getMontantRecu() {
        return montantRecu;
    }

    public void setMontantRecu(Double montantRecu) {
        this.montantRecu = montantRecu;
    }

    public Boolean getaCredit() {
        return aCredit;
    }

    public void setaCredit(Boolean aCredit) {
        this.aCredit = aCredit;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VenteDTO)) {
            return false;
        }

        VenteDTO venteDTO = (VenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, venteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VenteDTO{" +
            "id=" + getId() +
            ", dateVente='" + getDateVente() + "'" +
            ", remiseRabais=" + getRemiseRabais() +
            ", montantRecu=" + getMontantRecu() +
            ", aCredit='" + getaCredit() + "'" +
            ", client=" + getClient() +
            ", article=" + getArticle() +
            "}";
    }
}
