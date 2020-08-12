package com.pfe.covite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Livreur.
 */
@Entity
@Table(name = "livreur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Livreur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "solde")
    private Float solde;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public Livreur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Float getSolde() {
        return solde;
    }

    public Livreur solde(Float solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public User getUser() {
        return user;
    }

    public Livreur user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livreur)) {
            return false;
        }
        return id != null && id.equals(((Livreur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Livreur{" +
            "id=" + getId() +
            ", telephone='" + getTelephone() + "'" +
            ", solde=" + getSolde() +
            "}";
    }
}
