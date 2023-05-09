package com.challenge.digitaldayapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.challenge.digitaldayapp.domain.Article} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Double prixVente;

    @NotNull
    private Integer qte;

    @NotNull
    private LocalDate dateExpiration;

    @NotNull
    private LocalDate dateFabrication;

    private String codeBar;

    @NotNull
    private Double prixAchat;

    private Double seuilSecurite;

    private Double seuilMinimal;

    private Double seuilAlerte;

    private String emplacement;

    private String description;

    @Lob
    private byte[] photoPrincipale;

    private String photoPrincipaleContentType;

    @Lob
    private byte[] photoSecondaire;

    private String photoSecondaireContentType;

    @Lob
    private byte[] photoTertaire;

    private String photoTertaireContentType;
    private Set<CategorieArticleDTO> categorieArticles = new HashSet<>();

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

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDate getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public String getCodeBar() {
        return codeBar;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getSeuilSecurite() {
        return seuilSecurite;
    }

    public void setSeuilSecurite(Double seuilSecurite) {
        this.seuilSecurite = seuilSecurite;
    }

    public Double getSeuilMinimal() {
        return seuilMinimal;
    }

    public void setSeuilMinimal(Double seuilMinimal) {
        this.seuilMinimal = seuilMinimal;
    }

    public Double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhotoPrincipale() {
        return photoPrincipale;
    }

    public void setPhotoPrincipale(byte[] photoPrincipale) {
        this.photoPrincipale = photoPrincipale;
    }

    public String getPhotoPrincipaleContentType() {
        return photoPrincipaleContentType;
    }

    public void setPhotoPrincipaleContentType(String photoPrincipaleContentType) {
        this.photoPrincipaleContentType = photoPrincipaleContentType;
    }

    public byte[] getPhotoSecondaire() {
        return photoSecondaire;
    }

    public void setPhotoSecondaire(byte[] photoSecondaire) {
        this.photoSecondaire = photoSecondaire;
    }

    public String getPhotoSecondaireContentType() {
        return photoSecondaireContentType;
    }

    public void setPhotoSecondaireContentType(String photoSecondaireContentType) {
        this.photoSecondaireContentType = photoSecondaireContentType;
    }

    public byte[] getPhotoTertaire() {
        return photoTertaire;
    }

    public void setPhotoTertaire(byte[] photoTertaire) {
        this.photoTertaire = photoTertaire;
    }

    public String getPhotoTertaireContentType() {
        return photoTertaireContentType;
    }

    public void setPhotoTertaireContentType(String photoTertaireContentType) {
        this.photoTertaireContentType = photoTertaireContentType;
    }

    public Set<CategorieArticleDTO> getCategorieArticles() {
        return categorieArticles;
    }

    public void setCategorieArticles(Set<CategorieArticleDTO> categorieArticles) {
        this.categorieArticles = categorieArticles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
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
            ", photoSecondaire='" + getPhotoSecondaire() + "'" +
            ", photoTertaire='" + getPhotoTertaire() + "'" +
            ", categorieArticles=" + getCategorieArticles() +
            "}";
    }
}
