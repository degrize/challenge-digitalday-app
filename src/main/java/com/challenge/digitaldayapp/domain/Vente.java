package com.challenge.digitaldayapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vente.
 */
@Entity
@Table(name = "vente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_vente")
    private Instant dateVente;

    @Column(name = "remise_rabais")
    private Double remiseRabais;

    @Column(name = "montant_recu")
    private Double montantRecu;

    @Column(name = "a_credit")
    private Boolean aCredit;

    @Column(name = "qte")
    private Integer qte;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quartier" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "categorieArticles" }, allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateVente() {
        return this.dateVente;
    }

    public Vente dateVente(Instant dateVente) {
        this.setDateVente(dateVente);
        return this;
    }

    public void setDateVente(Instant dateVente) {
        this.dateVente = dateVente;
    }

    public Double getRemiseRabais() {
        return this.remiseRabais;
    }

    public Vente remiseRabais(Double remiseRabais) {
        this.setRemiseRabais(remiseRabais);
        return this;
    }

    public void setRemiseRabais(Double remiseRabais) {
        this.remiseRabais = remiseRabais;
    }

    public Double getMontantRecu() {
        return this.montantRecu;
    }

    public Vente montantRecu(Double montantRecu) {
        this.setMontantRecu(montantRecu);
        return this;
    }

    public void setMontantRecu(Double montantRecu) {
        this.montantRecu = montantRecu;
    }

    public Boolean getaCredit() {
        return this.aCredit;
    }

    public Vente aCredit(Boolean aCredit) {
        this.setaCredit(aCredit);
        return this;
    }

    public void setaCredit(Boolean aCredit) {
        this.aCredit = aCredit;
    }

    public Integer getQte() {
        return qte;
    }

    public Vente qte(Integer qte) {
        this.setQte(qte);
        return this;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vente client(Client client) {
        this.setClient(client);
        return this;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Vente article(Article article) {
        this.setArticle(article);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vente)) {
            return false;
        }
        return id != null && id.equals(((Vente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vente{" +
            "id=" + getId() +
            ", dateVente='" + getDateVente() + "'" +
            ", remiseRabais=" + getRemiseRabais() +
            ", montantRecu=" + getMontantRecu() +
            ", qte='" + getQte() + "'" +
            ", aCredit='" + getaCredit() + "'" +
            "}";
    }
}
