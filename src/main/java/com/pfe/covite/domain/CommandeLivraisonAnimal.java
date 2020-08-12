package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A CommandeLivraisonAnimal.
 */
@Entity
@Table(name = "commande_livraison_animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandeLivraisonAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresse_depart")
    private String adresseDepart;

    @NotNull
    @Column(name = "adresse_arrivee", nullable = false)
    private String adresseArrivee;

    @NotNull
    @Column(name = "date_heure", nullable = false)
    private LocalDate dateHeure;

    @NotNull
    @Column(name = "animal", nullable = false)
    private String animal;

    @Column(name = "moyen_de_transport")
    private String moyenDeTransport;

    @Column(name = "numero_client")
    private String numeroClient;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "validated")
    private Boolean validated;

    @OneToOne
    @JoinColumn(unique = true)
    private User client;

    @OneToOne
    @JoinColumn(unique = true)
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

    public CommandeLivraisonAnimal adresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
        return this;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public CommandeLivraisonAnimal adresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
        return this;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public LocalDate getDateHeure() {
        return dateHeure;
    }

    public CommandeLivraisonAnimal dateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
        return this;
    }

    public void setDateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getAnimal() {
        return animal;
    }

    public CommandeLivraisonAnimal animal(String animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getMoyenDeTransport() {
        return moyenDeTransport;
    }

    public CommandeLivraisonAnimal moyenDeTransport(String moyenDeTransport) {
        this.moyenDeTransport = moyenDeTransport;
        return this;
    }

    public void setMoyenDeTransport(String moyenDeTransport) {
        this.moyenDeTransport = moyenDeTransport;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public CommandeLivraisonAnimal numeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
        return this;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public Double getPrix() {
        return prix;
    }

    public CommandeLivraisonAnimal prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean isValidated() {
        return validated;
    }

    public CommandeLivraisonAnimal validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public User getClient() {
        return client;
    }

    public CommandeLivraisonAnimal client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public User getLivreur() {
        return livreur;
    }

    public CommandeLivraisonAnimal livreur(User user) {
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
        if (!(o instanceof CommandeLivraisonAnimal)) {
            return false;
        }
        return id != null && id.equals(((CommandeLivraisonAnimal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommandeLivraisonAnimal{" +
            "id=" + getId() +
            ", adresseDepart='" + getAdresseDepart() + "'" +
            ", adresseArrivee='" + getAdresseArrivee() + "'" +
            ", dateHeure='" + getDateHeure() + "'" +
            ", animal='" + getAnimal() + "'" +
            ", moyenDeTransport='" + getMoyenDeTransport() + "'" +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", prix=" + getPrix() +
            ", validated='" + isValidated() + "'" +
            "}";
    }
}
