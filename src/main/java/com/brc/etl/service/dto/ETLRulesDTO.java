package com.brc.etl.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.brc.etl.domain.ETLRules} entity.
 */
public class ETLRulesDTO implements Serializable {
    
    private Long id;

    private String name;

    private String type;

    private String frequency;

    private String cutOffDay;

    private String cutOffTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCutOffDay() {
        return cutOffDay;
    }

    public void setCutOffDay(String cutOffDay) {
        this.cutOffDay = cutOffDay;
    }

    public String getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ETLRulesDTO)) {
            return false;
        }

        return id != null && id.equals(((ETLRulesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ETLRulesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", cutOffDay='" + getCutOffDay() + "'" +
            ", cutOffTime='" + getCutOffTime() + "'" +
            "}";
    }
}
