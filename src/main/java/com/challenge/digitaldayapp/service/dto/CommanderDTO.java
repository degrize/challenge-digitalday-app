package com.challenge.digitaldayapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.challenge.digitaldayapp.domain.Commander} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommanderDTO implements Serializable {

    private Long id;

    private Instant dateCommande;

    private Double montantAchat;

    private Boolean aCredit;

    private FournisseurDTO fournisseur;

    private ArticleDTO article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantAchat() {
        return montantAchat;
    }

    public void setMontantAchat(Double montantAchat) {
        this.montantAchat = montantAchat;
    }

    public Boolean getaCredit() {
        return aCredit;
    }

    public void setaCredit(Boolean aCredit) {
        this.aCredit = aCredit;
    }

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
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
        if (!(o instanceof CommanderDTO)) {
            return false;
        }

        CommanderDTO commanderDTO = (CommanderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commanderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommanderDTO{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", montantAchat=" + getMontantAchat() +
            ", aCredit='" + getaCredit() + "'" +
            ", fournisseur=" + getFournisseur() +
            ", article=" + getArticle() +
            "}";
    }
}
