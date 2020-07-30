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

    @OneToOne
    @JoinColumn(unique = true)
    private User client;

    @OneToOne
    @JoinColumn(unique = true)
    private CommandeLivraison commandeLivraison;

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

    public Notification(String titre, User client) {
        this.titre = titre;
        this.client = client;
    }

    public Notification(String titre, User client, CommandeLivraison commandeLivraison) {
        this.titre = titre;
        this.client = client;
        this.commandeLivraison = commandeLivraison;
    }

    public Notification() {
    }

    public void setTitre(String titre) {
        this.titre = titre;
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
            "}";
    }
}
