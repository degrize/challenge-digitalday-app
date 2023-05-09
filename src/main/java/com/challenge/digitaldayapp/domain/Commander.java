package com.challenge.digitaldayapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commander.
 */
@Entity
@Table(name = "commander")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commander implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_commande")
    private Instant dateCommande;

    @Column(name = "montant_achat")
    private Double montantAchat;

    @Column(name = "a_credit")
    private Boolean aCredit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quartier" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categorieArticles" }, allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commander id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return this.dateCommande;
    }

    public Commander dateCommande(Instant dateCommande) {
        this.setDateCommande(dateCommande);
        return this;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantAchat() {
        return this.montantAchat;
    }

    public Commander montantAchat(Double montantAchat) {
        this.setMontantAchat(montantAchat);
        return this;
    }

    public void setMontantAchat(Double montantAchat) {
        this.montantAchat = montantAchat;
    }

    public Boolean getaCredit() {
        return this.aCredit;
    }

    public Commander aCredit(Boolean aCredit) {
        this.setaCredit(aCredit);
        return this;
    }

    public void setaCredit(Boolean aCredit) {
        this.aCredit = aCredit;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Commander fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Commander article(Article article) {
        this.setArticle(article);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commander)) {
            return false;
        }
        return id != null && id.equals(((Commander) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commander{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", montantAchat=" + getMontantAchat() +
            ", aCredit='" + getaCredit() + "'" +
            "}";
    }
}
