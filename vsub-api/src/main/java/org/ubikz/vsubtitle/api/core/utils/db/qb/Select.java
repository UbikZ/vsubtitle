package org.ubikz.vsubtitle.api.core.utils.db.qb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Select extends AbstractQuery {
    protected final String SQL_AS = "AS";

    // Key use within maps
    private final String KEY_DISTINCT = "distinct";
    private final String KEY_ORDER = "order";
    private final String KEY_DIRECTION = "direction";
    private final String KEY_GROUP = "group";
    private final String KEY_JOIN = "inner_join";
    private final String KEY_LIMIT = "limit";
    private final String KEY_OFFSET = "offset";

    // SQL elements
    private final String SQL_WILDCARD = "*";
    private final String SQL_SELECT = "SELECT";
    private final String SQL_FROM = "FROM";
    private final String SQL_DISTINCT = "DISTINCT";
    private final String SQL_ORDER_BY = "ORDER BY";
    private final String SQL_GROUP_BY = "GROUP BY";
    private final String SQL_ON = "ON";
    private final String SQL_ASC = "ASC";
    private final String SQL_DESC = "DESC";
    private final String SQL_LIMIT = "LIMIT";
    private final String SQL_OFFSET = "OFFSET";

    public Select(String... columns) {
        super();
        this.parseColumns(columns);
    }

    /**
     * @param columns
     * @return
     */
    public Select columns(String... columns) {
        this.parseColumns(columns);
        return this;
    }

    /**
     * @param table
     * @return
     */
    public Select from(String table) {
        this.from(table, null);
        return this;
    }

    /**
     * @param table
     * @param alias
     * @return
     */
    public Select from(String table, String alias) {
        this.parts.put(KEY_FROM, this.buildAlias(table, alias));
        return this;
    }

    /**
     * @param table
     * @param on
     * @return
     */
    public Select join(String table, String on) {
        this.join(table, null, on);
        return this;
    }

    /**
     * @param table
     * @param alias
     * @param on
     * @return
     */
    public Select join(String table, String alias, String on) {
        return this.join(table, alias, on, null);
    }

    /**
     * @param table
     * @param alias
     * @param on
     * @return
     */
    public Select joinLeft(String table, String alias, String on) {
        return this.join(table, alias, on, "LEFT");
    }

    /**
     * @param table
     * @param alias
     * @param on
     * @return
     */
    public Select join(String table, String alias, String on, String type) {
        @SuppressWarnings("unchecked")
        List<String> existingJoins = (List<String>) this.parts.get(KEY_JOIN);
        existingJoins.add((type == null ? "" : type + " ") + "JOIN " + this.buildAlias(table, alias) + " " +
                SQL_ON + " " + on);
        this.parts.put(KEY_JOIN, existingJoins);
        return this;
    }

    /**
     * @param table
     * @param alias
     * @return
     */
    private String buildAlias(String table, String alias) {
        List<String> aliasedTable = new ArrayList<>();
        aliasedTable.add(table);

        if (alias != null) {
            aliasedTable.add(SQL_AS);
            aliasedTable.add(alias);
        }

        return aliasedTable.stream().collect(Collectors.joining(" "));
    }

    /**
     * @param columns
     */
    private void parseColumns(String... columns) {
        this.parts.put(KEY_COLUMNS, new ArrayList<>(Arrays.asList(columns)));
    }

    /**
     * @param column
     * @return
     */
    public Select addColumn(String column) {
        List<String> columnList = (List<String>) this.parts.getOrDefault(KEY_COLUMNS, new ArrayList<>());
        columnList.add(column);
        return this;
    }

    /**
     * @param columns
     * @return
     */
    public Select groupBy(String... columns) {
        this.parts.put(KEY_GROUP, Arrays.asList(columns));
        return this;
    }

    /**
     * @param column
     * @return
     */
    public Select orderBy(String column) {
        return this.orderBy(column, true);
    }

    /**
     * @param column
     * @param isAsc
     * @return
     */
    public Select orderBy(String column, boolean isAsc) {
        this.parts.put(KEY_ORDER, column);
        this.parts.put(KEY_DIRECTION, isAsc ? SQL_ASC : SQL_DESC);
        return this;
    }

    @Override
    public Select where(String where) {
        return (Select) super.where(where);
    }

    @Override
    public Select where(String column, Object value) {
        return (Select) super.where(column, value);
    }

    @Override
    public Select where(String column, String op, Object value) {
        return (Select) super.where(column, op, value, null);
    }

    @Override
    public Select where(String column, String op, Object value, String cast) {
        return (Select) super.where(column, op, value, cast);
    }

    @Override
    public Select orWhere(String where) {
        return (Select) super.orWhere(where);
    }

    @Override
    public Select orWhere(String column, Object value) {
        return (Select) super.orWhere(column, value);
    }

    @Override
    public Select orWhere(String column, String op, Object value) {
        return (Select) super.orWhere(column, op, value, null);
    }

    @Override
    public Select orWhere(String column, String op, Object value, String cast) {
        return (Select) super.orWhere(column, op, value, cast);
    }

    /**
     * @param number
     * @return
     */
    public Select limit(int number) {
        this.parts.put(KEY_LIMIT, number);
        return this;
    }

    /**
     * @param number
     * @return
     */
    public Select offset(int number) {
        this.parts.put(KEY_OFFSET, number);
        return this;
    }

    @Override
    public void build() {
        super.build();

        this.sql.add(SQL_SELECT);

        boolean distinct = (boolean) this.parts.get(KEY_DISTINCT);
        if (distinct) {
            this.sql.add(SQL_DISTINCT);
        }

        List<String> columns = (List<String>) this.parts.get(KEY_COLUMNS);
        if (columns.size() > 0) {
            this.sql.add(columns.stream().collect(Collectors.joining(", ")));
        } else {
            this.sql.add(SQL_WILDCARD);
        }

        this.sql.add(SQL_FROM);
        this.sql.add((String) this.parts.get(KEY_FROM));

        List<String> joins = (List<String>) this.parts.get(KEY_JOIN);
        if (joins.size() > 0) {
            this.sql.add(joins.stream().collect(Collectors.joining(" ")));
        }

        this.handleWhereClauses();

        List<String> groupBy = (List<String>) this.parts.get(KEY_GROUP);
        if (groupBy.size() > 0) {
            this.sql.add(SQL_GROUP_BY);
            this.sql.add("(" + groupBy.stream().collect(Collectors.joining(",")) + ")");
        }

        String orderBy = (String) this.parts.get(KEY_ORDER);
        if (orderBy != null) {
            this.sql.add(SQL_ORDER_BY);
            this.sql.add(orderBy);
            this.sql.add((String) this.parts.get(KEY_DIRECTION));
        }

        Integer limit = (Integer) this.parts.get(KEY_LIMIT);
        if (limit != null) {
            this.sql.add(SQL_LIMIT);
            this.sql.add(limit.toString());
        }

        Integer offset = (Integer) this.parts.get(KEY_OFFSET);
        if (offset != null) {
            this.sql.add(SQL_OFFSET);
            this.sql.add(offset.toString());
        }
    }

    @Override
    protected void initParts() {
        this.parts.put(KEY_DISTINCT, false);
        this.parts.put(KEY_COLUMNS, new ArrayList<>());
        this.parts.put(KEY_FROM, null);
        this.parts.put(KEY_JOIN, new ArrayList<>());
        this.parts.put(KEY_WHERE, new ArrayList<>());
        this.parts.put(KEY_OR_WHERE, new ArrayList<>());
        this.parts.put(KEY_ORDER, null);
        this.parts.put(KEY_DIRECTION, null);
        this.parts.put(KEY_GROUP, new ArrayList<>());
        this.parts.put(KEY_LIMIT, null);
        this.parts.put(KEY_OFFSET, null);
    }

    @Override
    public String toString() {
        return this.getSQL();
    }
}