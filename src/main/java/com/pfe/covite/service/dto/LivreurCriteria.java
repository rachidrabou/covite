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
 * Criteria class for the {@link com.pfe.covite.domain.Livreur} entity. This class is used
 * in {@link com.pfe.covite.web.rest.LivreurResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /livreurs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivreurCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter telephone;

    private FloatFilter solde;

    private StringFilter cin;

    private LongFilter userId;

    private LongFilter vehiculeId;

    public LivreurCriteria() {
    }

    public LivreurCriteria(LivreurCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.solde = other.solde == null ? null : other.solde.copy();
        this.cin = other.cin == null ? null : other.cin.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.vehiculeId = other.vehiculeId == null ? null : other.vehiculeId.copy();
    }

    @Override
    public LivreurCriteria copy() {
        return new LivreurCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public FloatFilter getSolde() {
        return solde;
    }

    public void setSolde(FloatFilter solde) {
        this.solde = solde;
    }

    public StringFilter getCin() {
        return cin;
    }

    public void setCin(StringFilter cin) {
        this.cin = cin;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(LongFilter vehiculeId) {
        this.vehiculeId = vehiculeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LivreurCriteria that = (LivreurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(solde, that.solde) &&
            Objects.equals(cin, that.cin) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(vehiculeId, that.vehiculeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        telephone,
        solde,
        cin,
        userId,
        vehiculeId
        );
    }

    @Override
    public String toString() {
        return "LivreurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (solde != null ? "solde=" + solde + ", " : "") +
                (cin != null ? "cin=" + cin + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (vehiculeId != null ? "vehiculeId=" + vehiculeId + ", " : "") +
            "}";
    }

}
