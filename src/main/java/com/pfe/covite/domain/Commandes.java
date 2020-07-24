package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

import com.pfe.covite.domain.enumeration.Categorie;

import com.pfe.covite.domain.enumeration.Service;

/**
 * A Commandes.
 */
@Entity
@Table(name = "commandes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commandes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "prix")
    private Float prix;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Categorie type;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeservice")
    private Service typeservice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Commandes date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getPrix() {
        return prix;
    }

    public Commandes prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Categorie getType() {
        return type;
    }

    public Commandes type(Categorie type) {
        this.type = type;
        return this;
    }

    public void setType(Categorie type) {
        this.type = type;
    }

    public Service getTypeservice() {
        return typeservice;
    }

    public Commandes typeservice(Service typeservice) {
        this.typeservice = typeservice;
        return this;
    }

    public void setTypeservice(Service typeservice) {
        this.typeservice = typeservice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commandes)) {
            return false;
        }
        return id != null && id.equals(((Commandes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commandes{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prix=" + getPrix() +
            ", type='" + getType() + "'" +
            ", typeservice='" + getTypeservice() + "'" +
            "}";
    }
}
