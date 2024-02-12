/**
 * 
 */
package ru.urvanov.virtualpets.server.controller.site.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author fedya
 * 
 */
public class StatisticsParams {
    @NotNull
    @Min(1)
    @Max(1000)
    private Integer maxRecordsCount;

    public enum StatisticsType {
        LAST_REGISTERED_USERS, LAST_CREATED_PETS
    }
    
    @NotNull
    private StatisticsType type;

    public Integer getMaxRecordsCount() {
        return maxRecordsCount;
    }

    public void setMaxRecordsCount(Integer maxRecordsCount) {
        this.maxRecordsCount = maxRecordsCount;
    }

    public StatisticsType getType() {
        return type;
    }

    public void setType(StatisticsType type) {
        this.type = type;
    }

}
