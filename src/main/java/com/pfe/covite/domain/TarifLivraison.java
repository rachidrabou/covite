package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TarifLivraison.
 */
@Entity
@Table(name = "tarif_livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifLivraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service")
    private String service;

    @Column(name = "objet")
    private String objet;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "prix")
    private Float prix;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public TarifLivraison service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getObjet() {
        return objet;
    }

    public TarifLivraison objet(String objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Float getDistance() {
        return distance;
    }

    public TarifLivraison distance(Float distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifLivraison prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarifLivraison)) {
            return false;
        }
        return id != null && id.equals(((TarifLivraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifLivraison{" +
            "id=" + getId() +
            ", service='" + getService() + "'" +
            ", objet='" + getObjet() + "'" +
            ", distance=" + getDistance() +
            ", prix=" + getPrix() +
            "}";
    }
}
