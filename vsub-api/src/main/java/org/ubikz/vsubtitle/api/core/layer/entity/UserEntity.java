package org.ubikz.vsubtitle.api.core.layer.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ubikz.vsubtitle.api.core.layer.dal.UserDal;
import org.ubikz.vsubtitle.api.core.layer.dal.filter.AbstractDalFilter;
import org.ubikz.vsubtitle.api.core.layer.dal.filter.UserDalFilter;
import org.ubikz.vsubtitle.api.core.layer.dal.request.AbstractDalRequest;
import org.ubikz.vsubtitle.api.core.layer.dal.request.UserDalRequest;
import org.ubikz.vsubtitle.api.core.layer.dto.AbstractDto;
import org.ubikz.vsubtitle.api.core.layer.entity.filter.AbstractEntityFilter;
import org.ubikz.vsubtitle.api.core.layer.entity.filter.UserEntityFilter;
import org.ubikz.vsubtitle.api.core.layer.entity.helper.UserEntityHelper;
import org.ubikz.vsubtitle.api.core.layer.entity.request.AbstractEntityRequest;
import org.ubikz.vsubtitle.api.core.layer.entity.request.UserEntityRequest;

import java.util.List;
import java.util.Map;

@Component
public class UserEntity extends AbstractEntity {
    @Autowired
    public UserEntity(UserDal userDal) {
        this.dal = userDal;
        this.helper = new UserEntityHelper();
    }

    @Override
    protected void computeLoading(List<AbstractDto> dtoList) throws Exception {
    }

    @Override
    protected void computeLoading(Map<Object, AbstractDto> dtoList) throws Exception {
    }

    /**
     * @param request
     * @return
     */
    @Override
    protected AbstractDalRequest parseEntityToDalRequest(AbstractEntityRequest request) {
        UserDalRequest userDalRequest = new UserDalRequest();
        UserEntityRequest userEntityRequest = (UserEntityRequest) request;

        userDalRequest = (UserDalRequest) this.parseBaseEntityToDalRequest(userEntityRequest, userDalRequest);
        userDalRequest.setUsername(userEntityRequest.getUsername());
        userDalRequest.setFirstname(userEntityRequest.getFirstname());
        userDalRequest.setLastname(userEntityRequest.getLastname());
        userDalRequest.setPassword(userEntityRequest.getPassword());

        return userDalRequest;
    }

    /**
     * @param filter
     * @return
     */
    @Override
    protected AbstractDalFilter parseEntityToDalFilter(AbstractEntityFilter filter) {
        UserDalFilter userDalFilter = new UserDalFilter();
        UserEntityFilter userEntityFilter = (UserEntityFilter) filter;

        userDalFilter = (UserDalFilter) this.parseBaseEntityToDalFilter(userEntityFilter, userDalFilter);

        userDalFilter.setUsername(userEntityFilter.getUsername());
        userDalFilter.setPassword(userEntityFilter.getPassword());

        return userDalFilter;
    }
}