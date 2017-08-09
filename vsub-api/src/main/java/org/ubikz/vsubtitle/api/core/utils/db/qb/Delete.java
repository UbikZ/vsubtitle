package org.ubikz.vsubtitle.api.core.utils.db.qb;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Delete extends AbstractQuery {
    // SQL elements
    private final String SQL_DELETE = "DELETE";

    public Delete() {
        super();
    }

    public Delete from(String table) {
        this.parts.put(KEY_FROM, table);
        return this;
    }

    @Override
    public Delete where(String where) {
        return (Delete) super.where(where);
    }

    @Override
    public Delete where(String column, Object value) {
        return (Delete) super.where(column, value);
    }

    @Override
    public Delete where(String column, String op, Object value) {
        return (Delete) super.where(column, op, value, null);
    }

    @Override
    public Delete where(String column, String op, Object value, String cast) {
        return (Delete) super.where(column, op, value, cast);
    }

    @Override
    public Delete orWhere(String where) {
        return (Delete) super.orWhere(where);
    }

    @Override
    public Delete orWhere(String column, Object value) {
        return (Delete) super.orWhere(column, value);
    }

    @Override
    public Delete orWhere(String column, String op, Object value) {
        return (Delete) super.orWhere(column, op, value, null);
    }

    @Override
    public Delete orWhere(String column, String op, Object value, String cast) {
        return (Delete) super.orWhere(column, op, value, cast);
    }

    @Override
    public void build() {
        super.build();

        this.sql.add(SQL_DELETE);
        this.sql.add(SQL_FROM);
        this.sql.add((String) this.parts.get(KEY_FROM));

        this.handleWhereClauses();
    }

    @Override
    protected void initParts() {
        this.parts.put(KEY_FROM, null);
        this.parts.put(KEY_WHERE, new ArrayList<>());
        this.parts.put(KEY_OR_WHERE, new ArrayList<>());
    }

    @Override
    public String toString() {
        return this.getSQL();
    }
}