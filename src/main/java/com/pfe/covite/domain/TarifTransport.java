package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TarifTransport.
 */
@Entity
@Table(name = "tarif_transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifTransport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service")
    private String service;

    @Column(name = "vehicule")
    private String vehicule;

    @Column(name = "nombre_de_personne")
    private Integer nombreDePersonne;

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

    public TarifTransport service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVehicule() {
        return vehicule;
    }

    public TarifTransport vehicule(String vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public Integer getNombreDePersonne() {
        return nombreDePersonne;
    }

    public TarifTransport nombreDePersonne(Integer nombreDePersonne) {
        this.nombreDePersonne = nombreDePersonne;
        return this;
    }

    public void setNombreDePersonne(Integer nombreDePersonne) {
        this.nombreDePersonne = nombreDePersonne;
    }

    public Float getDistance() {
        return distance;
    }

    public TarifTransport distance(Float distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifTransport prix(Float prix) {
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
        if (!(o instanceof TarifTransport)) {
            return false;
        }
        return id != null && id.equals(((TarifTransport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifTransport{" +
            "id=" + getId() +
            ", service='" + getService() + "'" +
            ", vehicule='" + getVehicule() + "'" +
            ", nombreDePersonne=" + getNombreDePersonne() +
            ", distance=" + getDistance() +
            ", prix=" + getPrix() +
            "}";
    }
}
