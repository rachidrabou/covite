package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A CommandeTransport.
 */
@Entity
@Table(name = "commande_transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandeTransport implements Serializable {

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

    @NotNull
    @Column(name = "date_heure", nullable = false)
    private LocalDate dateHeure;

    @Column(name = "moyen_de_transport")
    private String moyenDeTransport;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "nombre_de_personnes")
    private Integer nombreDePersonnes;

    @Column(name = "numero_client")
    private String numeroClient;

    @Column(name = "validated")
    private Boolean validated;

    @OneToOne
    @JoinColumn(unique = true)
    private User client;

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

    public CommandeTransport adresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
        return this;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public CommandeTransport adresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
        return this;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public LocalDate getDateHeure() {
        return dateHeure;
    }

    public CommandeTransport dateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
        return this;
    }

    public void setDateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getMoyenDeTransport() {
        return moyenDeTransport;
    }

    public CommandeTransport moyenDeTransport(String moyenDeTransport) {
        this.moyenDeTransport = moyenDeTransport;
        return this;
    }

    public void setMoyenDeTransport(String moyenDeTransport) {
        this.moyenDeTransport = moyenDeTransport;
    }

    public Double getPrix() {
        return prix;
    }

    public CommandeTransport prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getNombreDePersonnes() {
        return nombreDePersonnes;
    }

    public CommandeTransport nombreDePersonnes(Integer nombreDePersonnes) {
        this.nombreDePersonnes = nombreDePersonnes;
        return this;
    }

    public void setNombreDePersonnes(Integer nombreDePersonnes) {
        this.nombreDePersonnes = nombreDePersonnes;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public CommandeTransport numeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
        return this;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public Boolean isValidated() {
        return validated;
    }

    public CommandeTransport validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public User getClient() {
        return client;
    }

    public CommandeTransport client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeTransport)) {
            return false;
        }
        return id != null && id.equals(((CommandeTransport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommandeTransport{" +
            "id=" + getId() +
            ", adresseDepart='" + getAdresseDepart() + "'" +
            ", adresseArrivee='" + getAdresseArrivee() + "'" +
            ", dateHeure='" + getDateHeure() + "'" +
            ", moyenDeTransport='" + getMoyenDeTransport() + "'" +
            ", prix=" + getPrix() +
            ", nombreDePersonnes=" + getNombreDePersonnes() +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", validated='" + isValidated() + "'" +
            "}";
    }
}
