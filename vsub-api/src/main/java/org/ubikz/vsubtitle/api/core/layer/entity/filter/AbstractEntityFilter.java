package org.ubikz.vsubtitle.api.core.layer.entity.filter;

import java.util.Date;
import java.util.List;

abstract public class AbstractEntityFilter {
    private Integer id;
    private List<Integer> idList;
    private String label;
    private String search;
    private Date startDate;
    private Date endDate;
    private Integer offset;
    private Integer limit;
    private Boolean isEnabled;
    private boolean isLazy = false;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "AbstractEntityFilter{" +
                "id=" + id +
                ", idList=" + idList +
                ", label='" + label + '\'' +
                ", search='" + search + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", offset=" + offset +
                ", limit=" + limit +
                ", isEnabled=" + isEnabled +
                ", isLazy=" + isLazy +
                '}';
    }
}

