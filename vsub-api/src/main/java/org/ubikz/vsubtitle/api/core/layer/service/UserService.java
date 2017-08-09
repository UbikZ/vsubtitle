package org.ubikz.vsubtitle.api.core.layer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ubikz.vsubtitle.api.core.layer.entity.UserEntity;
import org.ubikz.vsubtitle.api.core.layer.entity.filter.AbstractEntityFilter;
import org.ubikz.vsubtitle.api.core.layer.entity.filter.UserEntityFilter;
import org.ubikz.vsubtitle.api.core.layer.entity.request.AbstractEntityRequest;
import org.ubikz.vsubtitle.api.core.layer.entity.request.UserEntityRequest;
import org.ubikz.vsubtitle.api.core.layer.service.filter.AbstractServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.filter.UserServiceFilter;
import org.ubikz.vsubtitle.api.core.layer.service.request.AbstractServiceRequest;
import org.ubikz.vsubtitle.api.core.layer.service.request.UserServiceRequest;

@Component
public class UserService extends AbstractService {
    @Autowired
    public UserService(UserEntity userEntity) {
        this.entity = userEntity;
    }

    /**
     * @param request
     * @return
     */
    @Override
    protected AbstractEntityRequest parseServiceToEntityRequest(AbstractServiceRequest request) {
        UserEntityRequest userEntityRequest = new UserEntityRequest();
        UserServiceRequest userServiceRequest = (UserServiceRequest) request;

        this.parseBaseServiceToEntityRequest(userServiceRequest, userEntityRequest);
        userEntityRequest.setUsername(userServiceRequest.getUsername());
        userEntityRequest.setFirstname(userServiceRequest.getFirstname());
        userEntityRequest.setLastname(userServiceRequest.getLastname());
        userEntityRequest.setPassword(userServiceRequest.getPassword());

        return userEntityRequest;
    }

    /**
     * @param filter
     * @return
     */
    @Override
    protected AbstractEntityFilter parseServiceToEntityFilter(AbstractServiceFilter filter) {
        UserEntityFilter userEntityFilter = new UserEntityFilter();
        UserServiceFilter userServiceFilter = (UserServiceFilter) filter;

        this.parseBaseServiceToEntityFilter(userServiceFilter, userEntityFilter);

        userEntityFilter.setUsername(userServiceFilter.getUsername());
        userEntityFilter.setPassword(userServiceFilter.getPassword());

        return userEntityFilter;
    }
}