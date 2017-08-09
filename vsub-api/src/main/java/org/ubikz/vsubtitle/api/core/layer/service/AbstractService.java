package org.ubikz.vsubtitle.api.core.layer.service;

import org.ubikz.vsubtitle.api.core.layer.dto.AbstractDto;
import org.ubikz.vsubtitle.api.core.layer.entity.AbstractEntity;
import org.ubikz.vsubtitle.api.core.layer.entity.filter.AbstractEntityFilter;
import org.ubikz.vsubtitle.api.core.layer.entity.request.AbstractEntityRequest;
import org.ubikz.vsubtitle.api.core.layer.service.filter.AbstractServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.request.AbstractServiceRequest;

import java.util.List;

abstract public class AbstractService {
    protected AbstractEntity entity;

    /**
     * @param request
     * @return
     */
    abstract protected AbstractEntityRequest parseServiceToEntityRequest(AbstractServiceRequest request);

    /**
     * @param filter
     * @return
     */
    abstract protected AbstractEntityFilter parseServiceToEntityFilter(AbstractServiceFilter filter);

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public List<AbstractDto> getAll(AbstractServiceFilter filter) throws Exception {
        return this.entity.getAll(this.parseServiceToEntityFilter(filter));
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public int count(AbstractServiceFilter filter) throws Exception {
        return this.entity.count(this.parseServiceToEntityFilter(filter));
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public AbstractDto get(AbstractServiceFilter filter) throws Exception {
        return this.entity.get(this.parseServiceToEntityFilter(filter));
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public int delete(AbstractServiceFilter filter) throws Exception {
        return this.entity.delete(this.parseServiceToEntityFilter(filter));
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public int create(AbstractServiceRequest request) throws Exception {
        return this.entity.create(this.parseServiceToEntityRequest(request));
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public int update(AbstractServiceRequest request) throws Exception {
        return this.entity.update(this.parseServiceToEntityRequest(request));
    }

    /**
     * @param sRequest
     * @param eRequest
     * @return
     */
    final protected void parseBaseServiceToEntityRequest(AbstractServiceRequest sRequest, AbstractEntityRequest eRequest) {
        eRequest.setId(sRequest.getId());
        eRequest.setLabel(sRequest.getLabel());
        eRequest.setEnabled(sRequest.getEnabled());
    }

    /**
     * @param sFilter
     * @param eFilter
     * @return
     */
    final protected void parseBaseServiceToEntityFilter(AbstractServiceFilter sFilter, AbstractEntityFilter eFilter) {
        eFilter.setId(sFilter.getId());
        eFilter.setLabel(sFilter.getLabel());
        eFilter.setSearch(sFilter.getSearch());
        eFilter.setEnabled(sFilter.getEnabled());
        eFilter.setLazy(sFilter.isLazy());
        eFilter.setStartDate(sFilter.getStartDate());
        eFilter.setOffset(sFilter.getOffset());
        eFilter.setLimit(sFilter.getLimit());
    }
}