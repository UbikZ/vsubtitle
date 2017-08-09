package org.ubikz.vsubtitle.api.core.utils.db.qb;

import java.util.Map;

interface IQuery {
    void build();

    String getSQL();

    Map<String, Object> getParameters();
}