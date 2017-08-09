package org.ubikz.vsubtitle.api.core.layer.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ubikz.vsubtitle.api.controller.filter.AbstractFilterBody;
import org.ubikz.vsubtitle.api.controller.filter.UserFilterBody;
import org.ubikz.vsubtitle.api.controller.request.AbstractRequestBody;
import org.ubikz.vsubtitle.api.controller.request.UserRequestBody;
import org.ubikz.vsubtitle.api.core.layer.service.UserService;
import org.ubikz.vsubtitle.api.core.layer.service.filter.AbstractServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.filter.UserServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.request.AbstractServiceRequest;
import org.ubikz.vsubtitle.api.core.layer.service.request.UserServiceRequest;

@Component
public class UserContext extends AbstractContext {
    @Autowired
    public UserContext(UserService userService) {
        this.service = userService;
        this.serviceRequest = new UserServiceRequest();
        this.serviceFilter = new UserServiceFilter();
        this.filterBody = new UserFilterBody();

        this.CREATED = 70;
        this.UPDATED = 71;
        this.GET_ONE = 72;
        this.GET_ALL = 73;
        this.DELETE = 74;
    }

    @Override
    protected AbstractServiceRequest parseRequest(AbstractRequestBody data, AbstractServiceRequest request) {
        UserRequestBody requestBody = (UserRequestBody) data;
        UserServiceRequest serviceRequest = (UserServiceRequest) this.parseBaseRequest(requestBody, request);

        serviceRequest.setUsername(requestBody.getUsername());
        serviceRequest.setFirstname(requestBody.getFirstname());
        serviceRequest.setLastname(requestBody.getLastname());
        serviceRequest.setPassword(requestBody.getPassword());

        return serviceRequest;
    }

    @Override
    protected AbstractServiceFilter parseFilter(AbstractFilterBody data, AbstractServiceFilter filter) throws Exception {
        UserFilterBody filterBody = (UserFilterBody) data;
        UserServiceFilter serviceFilter = (UserServiceFilter) this.parseBaseFilter(filterBody, filter);

        serviceFilter.setUsername(filterBody.getUsername());
        serviceFilter.setEmail(filterBody.getEmail());
        serviceFilter.setPassword(filterBody.getPassword());

        return serviceFilter;
    }
}