package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

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

    @NotNull
    @Column(name = "date_heure", nullable = false)
    private LocalDate dateHeure;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "numero_client")
    private String numeroClient;

    @NotNull
    @Column(name = "objet", nullable = false)
    private String objet;

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

    public LocalDate getDateHeure() {
        return dateHeure;
    }

    public CommandeLivraison dateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
        return this;
    }

    public void setDateHeure(LocalDate dateHeure) {
        this.dateHeure = dateHeure;
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
            ", dateHeure='" + getDateHeure() + "'" +
            ", prix=" + getPrix() +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", objet='" + getObjet() + "'" +
            "}";
    }
}
