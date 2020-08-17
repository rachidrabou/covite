package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "prix")
    private Float prix;

    @Column(name = "prix_valider")
    private Boolean prixValider;

    @OneToOne
    @JoinColumn(unique = true)
    private User client;

    @OneToOne
    @JoinColumn(unique = true)
    private CommandeLivraison commandeLivraison;

    @OneToOne
    @JoinColumn(unique = true)
    private CommandeLivraisonAnimal commandeLivraisonAnimal;

    @OneToOne
    @JoinColumn(unique = true)
    private CommandeTransport commandeTransport;

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

    public String getTitre() {
        return titre;
    }

    public Notification titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Notification(String titre, User client) {
        this.titre = titre;
        this.client = client;
    }

    public Notification(String titre, User client, CommandeLivraison commandeLivraison) {
        this.titre = titre;
        this.client = client;
        this.commandeLivraison = commandeLivraison;
    }

    public Notification(String titre, User client, CommandeLivraisonAnimal commandeLivraisonAnimal) {
        this.titre = titre;
        this.client = client;
        this.commandeLivraisonAnimal = commandeLivraisonAnimal;
    }

    public Notification(String titre, User client, CommandeTransport commandeTransport) {
        this.titre = titre;
        this.client = client;
        this.commandeTransport = commandeTransport;
    }

    public Notification(String titre, User client, CommandeLivraison commandeLivraison, User livreur) {
        this.titre = titre;
        this.client = client;
        this.commandeLivraison = commandeLivraison;
        this.livreur = livreur;
    }

    public Notification(String titre, User client, CommandeLivraisonAnimal commandeLivraisonAnimal, User livreur) {
        this.titre = titre;
        this.client = client;
        this.commandeLivraisonAnimal = commandeLivraisonAnimal;
        this.livreur = livreur;
    }

    public Notification(String titre, User client, CommandeTransport commandeTransport, User livreur) {
        this.titre = titre;
        this.client = client;
        this.commandeTransport = commandeTransport;
        this.livreur = livreur;
    }

    public Notification() {
    }



    public Float getPrix() {
        return prix;
    }

    public Notification prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Boolean isPrixValider() {
        return prixValider;
    }

    public Notification prixValider(Boolean prixValider) {
        this.prixValider = prixValider;
        return this;
    }

    public void setPrixValider(Boolean prixValider) {
        this.prixValider = prixValider;
    }

    public User getClient() {
        return client;
    }

    public Notification client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public CommandeLivraison getCommandeLivraison() {
        return commandeLivraison;
    }

    public Notification commandeLivraison(CommandeLivraison commandeLivraison) {
        this.commandeLivraison = commandeLivraison;
        return this;
    }

    public void setCommandeLivraison(CommandeLivraison commandeLivraison) {
        this.commandeLivraison = commandeLivraison;
    }

    public CommandeLivraisonAnimal getCommandeLivraisonAnimal() {
        return commandeLivraisonAnimal;
    }

    public Notification commandeLivraisonAnimal(CommandeLivraisonAnimal commandeLivraisonAnimal) {
        this.commandeLivraisonAnimal = commandeLivraisonAnimal;
        return this;
    }

    public void setCommandeLivraisonAnimal(CommandeLivraisonAnimal commandeLivraisonAnimal) {
        this.commandeLivraisonAnimal = commandeLivraisonAnimal;
    }

    public CommandeTransport getCommandeTransport() {
        return commandeTransport;
    }

    public Notification commandeTransport(CommandeTransport commandeTransport) {
        this.commandeTransport = commandeTransport;
        return this;
    }

    public void setCommandeTransport(CommandeTransport commandeTransport) {
        this.commandeTransport = commandeTransport;
    }

    public User getLivreur() {
        return livreur;
    }

    public Notification livreur(User user) {
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
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", prix=" + getPrix() +
            ", prixValider='" + isPrixValider() + "'" +
            "}";
    }
}
