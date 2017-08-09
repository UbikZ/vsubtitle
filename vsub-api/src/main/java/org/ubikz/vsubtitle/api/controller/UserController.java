package org.ubikz.vsubtitle.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ubikz.vsubtitle.api.controller.filter.UserFilterBody;
import org.ubikz.vsubtitle.api.controller.request.UserRequestBody;
import org.ubikz.vsubtitle.api.core.layer.context.UserContext;
import org.ubikz.vsubtitle.api.core.layer.service.message.BaseMessage;

@RestController
public class UserController extends ApiController {
    private final String uriPath = "/user";
    private UserContext userContext;

    /**
     * @param userContext
     */
    @Autowired
    public UserController(UserContext userContext) {
        this.userContext = userContext;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = uriPath, method = RequestMethod.POST, produces = "application/json")
    public String create(@RequestBody final UserRequestBody request) throws Exception {
        return this.sendResponse(this.userContext.create(request));
    }

    /**
     * @param filter
     * @return
     * @throws Exception
     */
    @RequestMapping(value = uriPath, method = RequestMethod.GET, produces = "application/json")
    public String get(final UserFilterBody filter) throws Exception {
        BaseMessage msg = this.userContext.getAll(filter);
        msg.setTotal(this.userContext.count(filter));

        return this.sendResponse(msg);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = uriPath + "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public String update(@PathVariable("id") final int id, @RequestBody final UserRequestBody request) throws Exception {
        return this.sendResponse(this.userContext.update(id, request));
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = uriPath + "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getById(@PathVariable("id") final int id) throws Exception {
        return this.sendResponse(this.userContext.getById(id));
    }
}