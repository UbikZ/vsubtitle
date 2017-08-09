package org.ubikz.vsubtitle.api.core.utils.db.qb;


import java.util.*;
import java.util.stream.Collectors;

public class Insert extends Edit {
    protected String KEY_VALUES = "values";
    protected String KEY_RETURNING = "returning";
    protected String KEY_ON_CONFLICT = "onconflict";
    protected String KEY_ON_CONSTRAINT = "onconstraint";
    protected String KEY_DO = "do";

    private String SQL_INSERT = "INSERT INTO";
    private String SQL_VALUES = "VALUES";
    private String SQL_RETURNING = "RETURNING";
    private String SQL_ON_CONFLICT = "ON CONFLICT";
    private String SQL_ON_CONSTRAINT = "ON CONSTRAINT";
    private String SQL_DO = "DO";

    public Insert(String table) {
        super(table);
        this.table = table;
    }

    public Insert values(List<Map<String, Object>> listValues) {
        List<List<String>> namedParametersList = new ArrayList<>();

        for (int index = 0; index < listValues.size(); index++) {
            List<String> namedParameters = new ArrayList<>();
            for (Map.Entry<String, Object> value : listValues.get(index).entrySet()) {
                String column = value.getKey();
                String namedParam = column + index;
                this.columns.add(column);
                this.parameters.put(namedParam, value.getValue());
                namedParameters.add(":" + namedParam);
            }
            namedParametersList.add(namedParameters);
        }

        this.parts.put(KEY_VALUES, namedParametersList);

        return this;
    }

    private Insert on(String key, String... checks) {
        List<String> checkList = new ArrayList<>();
        if (checks != null) {
            checkList = new ArrayList<>(Arrays.asList(checks));
        }
        this.parts.put(key, checkList);
        return this;
    }

    public Insert onConflict(String... conflicts) {
        return this.on(KEY_ON_CONFLICT, conflicts);
    }

    public Insert onConstraint(String... constraints) {
        return this.on(KEY_ON_CONSTRAINT, constraints);
    }

    public Insert onDo(String action) {
        this.parts.put(KEY_DO, action);
        return this;
    }

    public Insert returning(String column) {
        this.parts.put(KEY_RETURNING, column);
        return this;
    }

    public Insert values(Map<String, Object> values) {
        return this.values(Collections.singletonList(values));
    }

    @Override
    public Insert where(String where) {
        return this;
    }

    @Override
    public Insert where(String column, Object value) {
        return this;
    }

    @Override
    public Insert where(String column, String op, Object value) {
        return this;
    }

    @Override
    public Insert where(String column, String op, Object value, String cast) {
        return this;
    }

    @Override
    public Insert orWhere(String where) {
        return this;
    }

    @Override
    public Insert orWhere(String column, Object value) {
        return this;
    }

    @Override
    public Insert orWhere(String column, String op, Object value) {
        return this;
    }

    @Override
    public Insert orWhere(String column, String op, Object value, String cast) {
        return this;
    }

    @Override
    public void build() {
        super.build();

        this.sql.add(SQL_INSERT);
        this.sql.add(this.table);
        this.sql.add("(" + this.columns.stream().collect(Collectors.joining(",")) + ")");
        this.sql.add(SQL_VALUES);
        this.sql.add(
                ((List<List<String>>) this.parts.get(KEY_VALUES))
                        .stream()
                        .map(this::buildListToString)
                        .collect(Collectors.joining(","))
        );

        List<String> conflictList = (List<String>) this.parts.get(KEY_ON_CONFLICT);
        if (conflictList != null) {
            this.sql.add(SQL_ON_CONFLICT);
            this.sql.add(this.buildListToString(conflictList, false));
        }

        List<String> constraintList = (List<String>) this.parts.get(KEY_ON_CONSTRAINT);
        if (constraintList != null && constraintList.size() > 0) {
            this.sql.add(SQL_ON_CONSTRAINT);
            this.sql.add(this.buildListToString(constraintList, false));
        }

        String onDo = (String) this.parts.get(KEY_DO);
        if (onDo != null) {
            this.sql.add(SQL_DO);
            this.sql.add(onDo);
        }

        String returningColumn = (String) this.parts.get(KEY_RETURNING);
        if (returningColumn != null) {
            this.sql.add(SQL_RETURNING);
            this.sql.add(returningColumn);
        }
    }

    private String buildListToString(List<String> elementList) {
        return this.buildListToString(elementList, true);
    }

    private String buildListToString(List<String> elementList, boolean forValue) {
        String result = "";

        if (elementList != null && elementList.size() > 0) {
            result = elementList.stream().collect(Collectors.joining(","));
            if (forValue) {
                result = "(" + result + ")";
            }
        }

        return result;
    }

    @Override
    protected void initParts() {
        super.initParts();
        this.parts.put(KEY_COLUMNS, new HashSet<>());
        this.parts.put(KEY_VALUES, new ArrayList<>());
        this.parts.put(KEY_RETURNING, null);
        this.parts.put(KEY_ON_CONFLICT, null);
        this.parts.put(KEY_ON_CONSTRAINT, null);
    }
}