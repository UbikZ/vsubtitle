package org.ubikz.vsubtitle.api.core.layer.entity.helper;

import org.ubikz.vsubtitle.api.core.layer.dto.AbstractDto;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

abstract public class AbstractEntityHelper {
    /**
     * @param data
     * @param dto
     * @return
     */
    public AbstractDto getBaseDtoFromDal(Map<String, Object> data, AbstractDto dto) {
        if (data.containsKey("id")) {
            dto.setId((int) data.get("id"));
        }

        if (data.containsKey("date")) {
            Object date = data.get("date");
            if (date != null) {
                Timestamp timestamp = (Timestamp) date;
                long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
                dto.setDate(new Date(milliseconds));
            }
        }

        if (data.containsKey("label")) {
            dto.setLabel((String) data.get("label"));
        }

        if (data.containsKey("enabled")) {
            dto.setEnabled((Boolean) data.get("enabled"));
        }

        return dto;
    }

    /**
     * @param dataList
     * @param attr
     * @return
     */
    final public List<AbstractDto> getDtoListFromReturnDal(List<Object> dataList, String attr) {
        List<AbstractDto> abstractDtoList = new ArrayList<>();

        for (Object element : dataList) {
            abstractDtoList.add(this.getDtoFromDal(new HashMap<String, Object>() {{
                put(attr, element);
            }}));
        }

        return abstractDtoList;
    }

    /**
     * @param dataList
     * @return
     */
    final public List<AbstractDto> getDtoListFromDal(List<Map<String, Object>> dataList) {
        return dataList.stream().map(this::getDtoFromDal).collect(Collectors.toList());
    }

    /**
     * @param data
     * @return
     */
    abstract public AbstractDto getDtoFromDal(Map<String, Object> data);
}