package com.pfe.covite.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pfe.covite.domain.TarifTransport} entity. This class is used
 * in {@link com.pfe.covite.web.rest.TarifTransportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarif-transports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TarifTransportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter service;

    private StringFilter vehicule;

    private IntegerFilter nombreDePersonne;

    private FloatFilter distance;

    private FloatFilter prix;

    public TarifTransportCriteria() {
    }

    public TarifTransportCriteria(TarifTransportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.service = other.service == null ? null : other.service.copy();
        this.vehicule = other.vehicule == null ? null : other.vehicule.copy();
        this.nombreDePersonne = other.nombreDePersonne == null ? null : other.nombreDePersonne.copy();
        this.distance = other.distance == null ? null : other.distance.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
    }

    @Override
    public TarifTransportCriteria copy() {
        return new TarifTransportCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getService() {
        return service;
    }

    public void setService(StringFilter service) {
        this.service = service;
    }

    public StringFilter getVehicule() {
        return vehicule;
    }

    public void setVehicule(StringFilter vehicule) {
        this.vehicule = vehicule;
    }

    public IntegerFilter getNombreDePersonne() {
        return nombreDePersonne;
    }

    public void setNombreDePersonne(IntegerFilter nombreDePersonne) {
        this.nombreDePersonne = nombreDePersonne;
    }

    public FloatFilter getDistance() {
        return distance;
    }

    public void setDistance(FloatFilter distance) {
        this.distance = distance;
    }

    public FloatFilter getPrix() {
        return prix;
    }

    public void setPrix(FloatFilter prix) {
        this.prix = prix;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TarifTransportCriteria that = (TarifTransportCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(service, that.service) &&
            Objects.equals(vehicule, that.vehicule) &&
            Objects.equals(nombreDePersonne, that.nombreDePersonne) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(prix, that.prix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        service,
        vehicule,
        nombreDePersonne,
        distance,
        prix
        );
    }

    @Override
    public String toString() {
        return "TarifTransportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (service != null ? "service=" + service + ", " : "") +
                (vehicule != null ? "vehicule=" + vehicule + ", " : "") +
                (nombreDePersonne != null ? "nombreDePersonne=" + nombreDePersonne + ", " : "") +
                (distance != null ? "distance=" + distance + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
            "}";
    }

}
