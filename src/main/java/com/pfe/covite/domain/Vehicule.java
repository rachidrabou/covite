package com.pfe.covite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.pfe.covite.domain.enumeration.Typevehicule;

/**
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Typevehicule type;

    @Column(name = "capacite")
    private Integer capacite;

    @OneToOne(mappedBy = "vehicule")
    @JsonIgnore
    private Livreur livreur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Vehicule matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Typevehicule getType() {
        return type;
    }

    public Vehicule type(Typevehicule type) {
        this.type = type;
        return this;
    }

    public void setType(Typevehicule type) {
        this.type = type;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public Vehicule capacite(Integer capacite) {
        this.capacite = capacite;
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public Vehicule livreur(Livreur livreur) {
        this.livreur = livreur;
        return this;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicule)) {
            return false;
        }
        return id != null && id.equals(((Vehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", type='" + getType() + "'" +
            ", capacite=" + getCapacite() +
            "}";
    }
}
