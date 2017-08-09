package org.ubikz.vsubtitle.api.core.layer.entity.helper;

import org.ubikz.vsubtitle.api.core.layer.dto.UserDto;

import java.util.Map;

public class UserEntityHelper extends AbstractEntityHelper {
    /**
     * @param data
     * @return
     */
    public UserDto getDtoFromDal(Map<String, Object> data) {
        UserDto userDto = (UserDto) this.getBaseDtoFromDal(data, new UserDto());

        if (data.containsKey("username")) {
            userDto.setUsername((String) data.get("username"));
        }

        if (data.containsKey("firstname")) {
            userDto.setFirstname((String) data.get("firstname"));
        }

        if (data.containsKey("lastname")) {
            userDto.setLastname((String) data.get("lastname"));
        }

        if (data.containsKey("password")) {
            userDto.setPassword((String) data.get("password"));
        }

        if (data.containsKey("email")) {
            userDto.setEmail((String) data.get("email"));
        }

        if (data.containsKey("role")) {
            userDto.setRole((int) data.get("role"));
        }

        return userDto;
    }
}