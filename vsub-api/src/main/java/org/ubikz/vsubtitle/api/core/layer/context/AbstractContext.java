package org.ubikz.vsubtitle.api.core.layer.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.ubikz.vsubtitle.api.controller.filter.AbstractFilterBody;
import org.ubikz.vsubtitle.api.controller.request.AbstractRequestBody;
import org.ubikz.vsubtitle.api.core.exception.MissingParameterException;
import org.ubikz.vsubtitle.api.core.layer.service.AbstractService;
import org.ubikz.vsubtitle.api.core.layer.service.filter.AbstractServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.message.BaseMessage;
import org.ubikz.vsubtitle.api.core.layer.service.message.ErrorMessage;
import org.ubikz.vsubtitle.api.core.layer.service.request.AbstractServiceRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

abstract public class AbstractContext {
    protected final Logger logger = LoggerFactory.getLogger(AbstractContext.class);

    final private int CODE_ERROR = -1;
    final private int CODE_DATA_NOT_FOUND = -2;

    protected AbstractService service;
    protected AbstractServiceRequest serviceRequest;
    protected AbstractServiceFilter serviceFilter;
    protected AbstractFilterBody filterBody;

    protected int CREATED;
    protected int UPDATED;
    protected int GET_ONE;
    protected int GET_ALL;
    protected int DELETE;

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public BaseMessage create(AbstractRequestBody request) {
        return this.handle(() -> this.service.create(
                this.parseRequest(request, this.serviceRequest)
        ), HttpStatus.CREATED, this.CREATED);
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public BaseMessage update(Integer id, AbstractRequestBody request) {
        return this.handle(() -> {
            AbstractServiceRequest serviceRequest = this.serviceRequest;
            serviceRequest.setId(id);

            if (id == null) {
                throw new MissingParameterException();
            }

            return this.service.update(this.parseRequest(request, serviceRequest));
        }, HttpStatus.OK, this.UPDATED);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public BaseMessage getById(int id) {
        AbstractFilterBody filter = this.filterBody;
        filter.setId(id);

        return this.handle(() -> this.service.get(
                this.parseFilter(filter, this.serviceFilter)
        ), HttpStatus.OK, this.GET_ONE);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public BaseMessage deleteById(int id) {
        AbstractFilterBody filter = this.filterBody;
        filter.setId(id);

        return this.handle(() -> this.service.delete(
                this.parseFilter(filter, this.serviceFilter)
        ), HttpStatus.OK, this.DELETE);
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public BaseMessage getAll(AbstractFilterBody filter) {
        return this.handle(() -> this.service.getAll(
                this.parseFilter(filter, this.serviceFilter)
        ), HttpStatus.OK, this.GET_ALL);
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    public int count(AbstractFilterBody filter) throws Exception {
        return this.service.count(this.parseFilter(filter, this.serviceFilter));
    }

    /**
     * @param callable
     * @return
     * @throws Exception
     */
    final protected BaseMessage handle(Callable callable) {
        return this.handle(callable, HttpStatus.OK, 1);
    }

    /**
     * @param callable
     * @param defaultStatus
     * @param defaultCode
     * @return
     * @throws Exception
     */
    final protected BaseMessage handle(Callable callable, HttpStatus defaultStatus, int defaultCode) {
        BaseMessage result = new BaseMessage();
        ErrorMessage errorMessage = new ErrorMessage();
        Exception exception = null;

        Object data = null;
        boolean isSuccess = false;
        int size = 1;
        int code = defaultCode;
        int status = defaultStatus.value();

        try {
            data = callable.call();
            if (data instanceof List) {
                size = ((List) data).size();
            }
            isSuccess = true;
        } catch (EmptyResultDataAccessException e) {
            exception = e;
            code = CODE_DATA_NOT_FOUND;
            status = HttpStatus.NOT_FOUND.value();
            errorMessage.setTitle("Not found.");
            errorMessage.setDetail("Bad query. No result returned.");
            data = errorMessage;
        } catch (Exception e) {
            exception = e;
            code = CODE_ERROR;
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String detail = e.getMessage() + "\n";
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                detail += stackTraceElement + "\n";
            }
            errorMessage.setTitle("An exception (" + e.getClass().getName() + ") occurred.");
            errorMessage.setDetail(detail);
            data = errorMessage;
        } finally {
            result.setSize(size);
            result.setData(data);
            result.setCode(code);
            result.setStatus(status);
            result.setSuccess(isSuccess);
            if (exception != null) {
                List<String> errorLog = new ArrayList<>();
                errorLog.add(exception.getMessage());
                for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                    errorLog.add("      " + stackTraceElement);
                }
                this.logger.error("Exception : " + errorLog.stream().collect(Collectors.joining("\n")));
            }
        }

        return result;
    }

    /**
     * @param request
     * @return
     */
    abstract protected AbstractServiceRequest parseRequest(AbstractRequestBody data, AbstractServiceRequest request);

    /**
     * @param filter
     * @return
     */
    abstract protected AbstractServiceFilter parseFilter(AbstractFilterBody data, AbstractServiceFilter filter) throws Exception;

    /**
     * @param data
     * @param request
     * @return
     */
    final protected AbstractServiceRequest parseBaseRequest(AbstractRequestBody data, AbstractServiceRequest request) {
        request.setLabel(data.getLabel());
        request.setEnabled(data.getEnabled());

        return request;
    }

    /**
     * @param data
     * @param filter
     * @return
     */
    final protected AbstractServiceFilter parseBaseFilter(AbstractFilterBody data, AbstractServiceFilter filter) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        filter.setId(data.getId());
        filter.setLabel(data.getLabel());
        filter.setSearch(data.getSearch());
        filter.setEnabled(data.getEnabled());
        filter.setLazy(data.isLazy());
        filter.setStartDate(data.getStartDate() == null ? null : formatter.parse(data.getStartDate()));
        filter.setEndDate(data.getEndDate() == null ? null : formatter.parse(data.getEndDate()));
        filter.setLimit(data.getLimit());
        filter.setOffset(data.getOffset());

        return filter;
    }
}