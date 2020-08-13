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
 * Criteria class for the {@link com.pfe.covite.domain.TarifLivraison} entity. This class is used
 * in {@link com.pfe.covite.web.rest.TarifLivraisonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarif-livraisons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TarifLivraisonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter service;

    private StringFilter objet;

    private FloatFilter distance;

    private FloatFilter prix;

    public TarifLivraisonCriteria() {
    }

    public TarifLivraisonCriteria(TarifLivraisonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.service = other.service == null ? null : other.service.copy();
        this.objet = other.objet == null ? null : other.objet.copy();
        this.distance = other.distance == null ? null : other.distance.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
    }

    @Override
    public TarifLivraisonCriteria copy() {
        return new TarifLivraisonCriteria(this);
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

    public StringFilter getObjet() {
        return objet;
    }

    public void setObjet(StringFilter objet) {
        this.objet = objet;
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
        final TarifLivraisonCriteria that = (TarifLivraisonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(service, that.service) &&
            Objects.equals(objet, that.objet) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(prix, that.prix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        service,
        objet,
        distance,
        prix
        );
    }

    @Override
    public String toString() {
        return "TarifLivraisonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (service != null ? "service=" + service + ", " : "") +
                (objet != null ? "objet=" + objet + ", " : "") +
                (distance != null ? "distance=" + distance + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
            "}";
    }

}
