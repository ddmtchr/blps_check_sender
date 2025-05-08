package com.ddmtchr.blps_check_sender.connector.record;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class OneCRecord implements jakarta.resource.cci.Record {

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String description;

    @Override
    public String getRecordName() {
        return name;
    }

    @Override
    public void setRecordName(String name) {
        this.name = name;
    }

    @Override
    public void setRecordShortDescription(String description) {
        this.description = description;
    }

    @Override
    public String getRecordShortDescription() {
        return description;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        OneCRecord clone = (OneCRecord) super.clone();
        clone.setRecordName(this.getRecordName());
        clone.setRecordShortDescription(this.getRecordShortDescription());
        return clone;
    }
}
