package com.brc.etl.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A ETLDataLoad.
 */
@Entity
@Table(name = "etl_data_load")
public class ETLDataLoad implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;

    @Id
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "data_flow_type")
    private String dataFlowType;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "last_data_load_date")
    private String lastDataLoadDate;

    @Column(name = "record_counts")
    private Long recordCounts;

    @Column(name = "data_load_frequency")
    private String dataLoadFrequency;

    @Column(name = "update_ts")
    private String updateTs;

    // jhipster-needle-entity-add-field - JHipster will add fields here
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Long getRecordId() {
        return recordId;
    }

    public ETLDataLoad recordId(Long recordId) {
        this.recordId = recordId;
        return this;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getDataFlowType() {
        return dataFlowType;
    }

    public ETLDataLoad dataFlowType(String dataFlowType) {
        this.dataFlowType = dataFlowType;
        return this;
    }

    public void setDataFlowType(String dataFlowType) {
        this.dataFlowType = dataFlowType;
    }

    public String getDataType() {
        return dataType;
    }

    public ETLDataLoad dataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLastDataLoadDate() {
        return lastDataLoadDate;
    }

    public ETLDataLoad lastDataLoadDate(String lastDataLoadDate) {
        this.lastDataLoadDate = lastDataLoadDate;
        return this;
    }

    public void setLastDataLoadDate(String lastDataLoadDate) {
        this.lastDataLoadDate = lastDataLoadDate;
    }

    public Long getRecordCounts() {
        return recordCounts;
    }

    public ETLDataLoad recordCounts(Long recordCounts) {
        this.recordCounts = recordCounts;
        return this;
    }

    public void setRecordCounts(Long recordCounts) {
        this.recordCounts = recordCounts;
    }

    public String getDataLoadFrequency() {
        return dataLoadFrequency;
    }

    public ETLDataLoad dataLoadFrequency(String dataLoadFrequency) {
        this.dataLoadFrequency = dataLoadFrequency;
        return this;
    }

    public void setDataLoadFrequency(String dataLoadFrequency) {
        this.dataLoadFrequency = dataLoadFrequency;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public ETLDataLoad updateTs(String updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ETLDataLoad)) {
            return false;
        }
        return recordId != null && recordId.equals(((ETLDataLoad) o).recordId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ETLDataLoad{" +
            "recordId=" + getRecordId() +
            ", recordId=" + getRecordId() +
            ", dataFlowType='" + getDataFlowType() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", lastDataLoadDate='" + getLastDataLoadDate() + "'" +
            ", recordCounts=" + getRecordCounts() +
            ", dataLoadFrequency='" + getDataLoadFrequency() + "'" +
            ", updateTs='" + getUpdateTs() + "'" +
            "}";
    }
}
