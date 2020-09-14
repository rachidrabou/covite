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
    @Column(name = "animal", nullable = false)
    private String animal;

    @Column(name = "moyen_de_transport")
    private String moyenDeTransport;

    @Column(name = "numero_client")
    private String numeroClient;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "dateheure")
    private ZonedDateTime dateheure;

    @Column(name = "cvalider")
    private Boolean cvalider;

    @ManyToOne
    @JsonIgnoreProperties("commandeLivraisonAnimals")
    private User client;

    @ManyToOne
    @JsonIgnoreProperties("commandeLivraisonAnimals")
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

    public ZonedDateTime getDateheure() {
        return dateheure;
    }

    public CommandeLivraisonAnimal dateheure(ZonedDateTime dateheure) {
        this.dateheure = dateheure;
        return this;
    }

    public void setDateheure(ZonedDateTime dateheure) {
        this.dateheure = dateheure;
    }

    public Boolean isCvalider() {
        return cvalider;
    }

    public CommandeLivraisonAnimal cvalider(Boolean cvalider) {
        this.cvalider = cvalider;
        return this;
    }

    public void setCvalider(Boolean cvalider) {
        this.cvalider = cvalider;
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
            ", animal='" + getAnimal() + "'" +
            ", moyenDeTransport='" + getMoyenDeTransport() + "'" +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", prix=" + getPrix() +
            ", dateheure='" + getDateheure() + "'" +
            ", cvalider='" + isCvalider() + "'" +
            "}";
    }
}
