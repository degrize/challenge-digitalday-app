package com.challenge.digitaldayapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prix_vente", nullable = false)
    private Double prixVente;

    @NotNull
    @Column(name = "qte", nullable = false)
    private Integer qte;

    @NotNull
    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;

    @NotNull
    @Column(name = "date_fabrication", nullable = false)
    private LocalDate dateFabrication;

    @Column(name = "code_bar")
    private String codeBar;

    @NotNull
    @Column(name = "prix_achat", nullable = false)
    private Double prixAchat;

    @Column(name = "seuil_securite")
    private Double seuilSecurite;

    @Column(name = "seuil_minimal")
    private Double seuilMinimal;

    @Column(name = "seuil_alerte")
    private Double seuilAlerte;

    @Column(name = "emplacement")
    private String emplacement;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo_principale")
    private byte[] photoPrincipale;

    @Column(name = "photo_principale_content_type")
    private String photoPrincipaleContentType;

    @Lob
    @Column(name = "photo_secondaire")
    private byte[] photoSecondaire;

    @Column(name = "photo_secondaire_content_type")
    private String photoSecondaireContentType;

    @Lob
    @Column(name = "photo_tertaire")
    private byte[] photoTertaire;

    @Column(name = "photo_tertaire_content_type")
    private String photoTertaireContentType;

    @ManyToMany
    @JoinTable(
        name = "rel_article__categorie_article",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_article_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articles" }, allowSetters = true)
    private Set<CategorieArticle> categorieArticles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Article nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixVente() {
        return this.prixVente;
    }

    public Article prixVente(Double prixVente) {
        this.setPrixVente(prixVente);
        return this;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Integer getQte() {
        return this.qte;
    }

    public Article qte(Integer qte) {
        this.setQte(qte);
        return this;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public LocalDate getDateExpiration() {
        return this.dateExpiration;
    }

    public Article dateExpiration(LocalDate dateExpiration) {
        this.setDateExpiration(dateExpiration);
        return this;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDate getDateFabrication() {
        return this.dateFabrication;
    }

    public Article dateFabrication(LocalDate dateFabrication) {
        this.setDateFabrication(dateFabrication);
        return this;
    }

    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public String getCodeBar() {
        return this.codeBar;
    }

    public Article codeBar(String codeBar) {
        this.setCodeBar(codeBar);
        return this;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }

    public Double getPrixAchat() {
        return this.prixAchat;
    }

    public Article prixAchat(Double prixAchat) {
        this.setPrixAchat(prixAchat);
        return this;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getSeuilSecurite() {
        return this.seuilSecurite;
    }

    public Article seuilSecurite(Double seuilSecurite) {
        this.setSeuilSecurite(seuilSecurite);
        return this;
    }

    public void setSeuilSecurite(Double seuilSecurite) {
        this.seuilSecurite = seuilSecurite;
    }

    public Double getSeuilMinimal() {
        return this.seuilMinimal;
    }

    public Article seuilMinimal(Double seuilMinimal) {
        this.setSeuilMinimal(seuilMinimal);
        return this;
    }

    public void setSeuilMinimal(Double seuilMinimal) {
        this.seuilMinimal = seuilMinimal;
    }

    public Double getSeuilAlerte() {
        return this.seuilAlerte;
    }

    public Article seuilAlerte(Double seuilAlerte) {
        this.setSeuilAlerte(seuilAlerte);
        return this;
    }

    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public String getEmplacement() {
        return this.emplacement;
    }

    public Article emplacement(String emplacement) {
        this.setEmplacement(emplacement);
        return this;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getDescription() {
        return this.description;
    }

    public Article description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhotoPrincipale() {
        return this.photoPrincipale;
    }

    public Article photoPrincipale(byte[] photoPrincipale) {
        this.setPhotoPrincipale(photoPrincipale);
        return this;
    }

    public void setPhotoPrincipale(byte[] photoPrincipale) {
        this.photoPrincipale = photoPrincipale;
    }

    public String getPhotoPrincipaleContentType() {
        return this.photoPrincipaleContentType;
    }

    public Article photoPrincipaleContentType(String photoPrincipaleContentType) {
        this.photoPrincipaleContentType = photoPrincipaleContentType;
        return this;
    }

    public void setPhotoPrincipaleContentType(String photoPrincipaleContentType) {
        this.photoPrincipaleContentType = photoPrincipaleContentType;
    }

    public byte[] getPhotoSecondaire() {
        return this.photoSecondaire;
    }

    public Article photoSecondaire(byte[] photoSecondaire) {
        this.setPhotoSecondaire(photoSecondaire);
        return this;
    }

    public void setPhotoSecondaire(byte[] photoSecondaire) {
        this.photoSecondaire = photoSecondaire;
    }

    public String getPhotoSecondaireContentType() {
        return this.photoSecondaireContentType;
    }

    public Article photoSecondaireContentType(String photoSecondaireContentType) {
        this.photoSecondaireContentType = photoSecondaireContentType;
        return this;
    }

    public void setPhotoSecondaireContentType(String photoSecondaireContentType) {
        this.photoSecondaireContentType = photoSecondaireContentType;
    }

    public byte[] getPhotoTertaire() {
        return this.photoTertaire;
    }

    public Article photoTertaire(byte[] photoTertaire) {
        this.setPhotoTertaire(photoTertaire);
        return this;
    }

    public void setPhotoTertaire(byte[] photoTertaire) {
        this.photoTertaire = photoTertaire;
    }

    public String getPhotoTertaireContentType() {
        return this.photoTertaireContentType;
    }

    public Article photoTertaireContentType(String photoTertaireContentType) {
        this.photoTertaireContentType = photoTertaireContentType;
        return this;
    }

    public void setPhotoTertaireContentType(String photoTertaireContentType) {
        this.photoTertaireContentType = photoTertaireContentType;
    }

    public Set<CategorieArticle> getCategorieArticles() {
        return this.categorieArticles;
    }

    public void setCategorieArticles(Set<CategorieArticle> categorieArticles) {
        this.categorieArticles = categorieArticles;
    }

    public Article categorieArticles(Set<CategorieArticle> categorieArticles) {
        this.setCategorieArticles(categorieArticles);
        return this;
    }

    public Article addCategorieArticle(CategorieArticle categorieArticle) {
        this.categorieArticles.add(categorieArticle);
        categorieArticle.getArticles().add(this);
        return this;
    }

    public Article removeCategorieArticle(CategorieArticle categorieArticle) {
        this.categorieArticles.remove(categorieArticle);
        categorieArticle.getArticles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prixVente=" + getPrixVente() +
            ", qte=" + getQte() +
            ", dateExpiration='" + getDateExpiration() + "'" +
            ", dateFabrication='" + getDateFabrication() + "'" +
            ", codeBar='" + getCodeBar() + "'" +
            ", prixAchat=" + getPrixAchat() +
            ", seuilSecurite=" + getSeuilSecurite() +
            ", seuilMinimal=" + getSeuilMinimal() +
            ", seuilAlerte=" + getSeuilAlerte() +
            ", emplacement='" + getEmplacement() + "'" +
            ", description='" + getDescription() + "'" +
            ", photoPrincipale='" + getPhotoPrincipale() + "'" +
            ", photoPrincipaleContentType='" + getPhotoPrincipaleContentType() + "'" +
            ", photoSecondaire='" + getPhotoSecondaire() + "'" +
            ", photoSecondaireContentType='" + getPhotoSecondaireContentType() + "'" +
            ", photoTertaire='" + getPhotoTertaire() + "'" +
            ", photoTertaireContentType='" + getPhotoTertaireContentType() + "'" +
            "}";
    }
}
