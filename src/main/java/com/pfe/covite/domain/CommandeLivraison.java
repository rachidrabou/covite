package com.pfe.covite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A CommandeLivraison.
 */
@Entity
@Table(name = "commande_livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandeLivraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "adresse_depart", nullable = false)
    private String adresseDepart;

    @NotNull
    @Column(name = "adresse_arrivee", nullable = false)
    private String adresseArrivee;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "numero_client")
    private String numeroClient;

    @NotNull
    @Column(name = "objet", nullable = false)
    private String objet;

    @Column(name = "cin")
    private String cin;

    @Column(name = "dateheure")
    private ZonedDateTime dateheure;

    @Column(name = "cvalider")
    private Boolean cvalider;

    @ManyToOne
    @JsonIgnoreProperties("commandeLivraisons")
    private User client;

    @ManyToOne
    @JsonIgnoreProperties("commandeLivraisons")
    private User livreur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public CommandeLivraison adresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
        return this;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public CommandeLivraison adresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
        return this;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public Double getPrix() {
        return prix;
    }

    public CommandeLivraison prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public CommandeLivraison numeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
        return this;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public String getObjet() {
        return objet;
    }

    public CommandeLivraison objet(String objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getCin() {
        return cin;
    }

    public CommandeLivraison cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public ZonedDateTime getDateheure() {
        return dateheure;
    }

    public CommandeLivraison dateheure(ZonedDateTime dateheure) {
        this.dateheure = dateheure;
        return this;
    }

    public void setDateheure(ZonedDateTime dateheure) {
        this.dateheure = dateheure;
    }

    public Boolean isCvalider() {
        return cvalider;
    }

    public CommandeLivraison cvalider(Boolean cvalider) {
        this.cvalider = cvalider;
        return this;
    }

    public void setCvalider(Boolean cvalider) {
        this.cvalider = cvalider;
    }

    public User getClient() {
        return client;
    }

    public CommandeLivraison client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public User getLivreur() {
        return livreur;
    }

    public CommandeLivraison livreur(User user) {
        this.livreur = user;
        return this;
    }

    public void setLivreur(User user) {
        this.livreur = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeLivraison)) {
            return false;
        }
        return id != null && id.equals(((CommandeLivraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommandeLivraison{" +
            "id=" + getId() +
            ", adresseDepart='" + getAdresseDepart() + "'" +
            ", adresseArrivee='" + getAdresseArrivee() + "'" +
            ", prix=" + getPrix() +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", objet='" + getObjet() + "'" +
            ", cin='" + getCin() + "'" +
            ", dateheure='" + getDateheure() + "'" +
            ", cvalider='" + isCvalider() + "'" +
            "}";
    }
}
