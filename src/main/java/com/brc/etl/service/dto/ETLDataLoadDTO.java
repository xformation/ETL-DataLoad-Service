package com.brc.etl.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.brc.etl.domain.ETLDataLoad} entity.
 */
public class ETLDataLoadDTO implements Serializable {
    
    private Long id;

    private Long recordId;

    private String dataFlowType;

    private String dataType;

    private String lastDataLoadDate;

    private Long recordCounts;

    private String dataLoadFrequency;

    private String updateTs;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getDataFlowType() {
        return dataFlowType;
    }

    public void setDataFlowType(String dataFlowType) {
        this.dataFlowType = dataFlowType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLastDataLoadDate() {
        return lastDataLoadDate;
    }

    public void setLastDataLoadDate(String lastDataLoadDate) {
        this.lastDataLoadDate = lastDataLoadDate;
    }

    public Long getRecordCounts() {
        return recordCounts;
    }

    public void setRecordCounts(Long recordCounts) {
        this.recordCounts = recordCounts;
    }

    public String getDataLoadFrequency() {
        return dataLoadFrequency;
    }

    public void setDataLoadFrequency(String dataLoadFrequency) {
        this.dataLoadFrequency = dataLoadFrequency;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ETLDataLoadDTO)) {
            return false;
        }

        return id != null && id.equals(((ETLDataLoadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ETLDataLoadDTO{" +
            "id=" + getId() +
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
