package org.ubikz.vsubtitle.api.core.layer.service.filter;

import java.util.Date;

abstract public class AbstractServiceFilter {
    private Integer id;
    private String label;
    private String search;
    private Date startDate;
    private Date endDate;
    private Integer limit = null;
    private Integer offset = null;
    private Boolean isEnabled;
    private boolean isLazy = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "AbstractServiceFilter{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", search='" + search + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", limit=" + limit +
                ", offset=" + offset +
                ", isEnabled=" + isEnabled +
                ", isLazy=" + isLazy +
                '}';
    }
}

