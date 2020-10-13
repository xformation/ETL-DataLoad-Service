package com.brc.etl.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A ETLRules.
 */
@Entity
@Table(name = "etl_rules")
public class ETLRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "cut_off_day")
    private String cutOffDay;

    @Column(name = "cut_off_time")
    private String cutOffTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ETLRules name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public ETLRules type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequency() {
        return frequency;
    }

    public ETLRules frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCutOffDay() {
        return cutOffDay;
    }

    public ETLRules cutOffDay(String cutOffDay) {
        this.cutOffDay = cutOffDay;
        return this;
    }

    public void setCutOffDay(String cutOffDay) {
        this.cutOffDay = cutOffDay;
    }

    public String getCutOffTime() {
        return cutOffTime;
    }

    public ETLRules cutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
        return this;
    }

    public void setCutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ETLRules)) {
            return false;
        }
        return id != null && id.equals(((ETLRules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ETLRules{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", cutOffDay='" + getCutOffDay() + "'" +
            ", cutOffTime='" + getCutOffTime() + "'" +
            "}";
    }
}
