package org.ubikz.vsubtitle.api.core.layer.dto;

import java.util.Date;

abstract public class AbstractDto {
    private Integer id;
    private String label;
    private Boolean isEnabled;
    private Date date;

    public AbstractDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AbstractDto{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", isEnabled=" + isEnabled +
                ", date=" + date +
                '}';
    }
}