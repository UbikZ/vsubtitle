package org.ubikz.vsubtitle.api.core.utils.db.qb;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Update extends Edit {
    protected String KEY_SET = "set";

    private String SQL_UPDATE = "UPDATE";
    private String SQL_SET = "SET";

    public Update(String table) {
        super(table);
        this.table = table;
    }

    public Update set(Map<String, Object> values) {
        List<String> namedParameters = new ArrayList<>();
        for (Map.Entry<String, Object> value : values.entrySet()) {
            String column = value.getKey();
            this.parameters.put(column, value.getValue());
            namedParameters.add(column + "=:" + column);
        }
        this.parts.put(KEY_SET, namedParameters);

        return this;
    }

    @Override
    public void build() {
        super.build();

        this.sql.add(SQL_UPDATE);
        this.sql.add(this.table);
        this.sql.add(SQL_SET);
        this.sql.add(((List<String>) this.parts.get(KEY_SET)).stream().collect(Collectors.joining(",")));

        this.handleWhereClauses();
    }

    @Override
    protected void initParts() {
        super.initParts();
        this.parts.put(KEY_SET, new ArrayList<>());
    }
}