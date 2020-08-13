package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TarifTransportAnimal.
 */
@Entity
@Table(name = "tarif_transport_animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifTransportAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service")
    private String service;

    @Column(name = "animal")
    private String animal;

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

    public TarifTransportAnimal service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAnimal() {
        return animal;
    }

    public TarifTransportAnimal animal(String animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public Float getDistance() {
        return distance;
    }

    public TarifTransportAnimal distance(Float distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifTransportAnimal prix(Float prix) {
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
        if (!(o instanceof TarifTransportAnimal)) {
            return false;
        }
        return id != null && id.equals(((TarifTransportAnimal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifTransportAnimal{" +
            "id=" + getId() +
            ", service='" + getService() + "'" +
            ", animal='" + getAnimal() + "'" +
            ", distance=" + getDistance() +
            ", prix=" + getPrix() +
            "}";
    }
}
