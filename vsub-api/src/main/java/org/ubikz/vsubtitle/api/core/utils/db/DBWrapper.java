package org.ubikz.vsubtitle.api.core.utils.db;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DBWrapper {
    public NamedParameterJdbcTemplate jdbcTemplate;
    private DataSourceTransactionManager transactionManager;
    private DataSource dataSource;

    @Autowired
    public DBWrapper(DBProperties dbProperties) {
        this.dataSource = this.buildDataSource(dbProperties);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }

    private DataSource buildDataSource(DBProperties dbProperties) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(dbProperties.getHost());
        dataSource.setPortNumber(dbProperties.getPort());
        dataSource.setDatabaseName(dbProperties.getDbName());
        dataSource.setUser(dbProperties.getUsername());
        dataSource.setPassword(dbProperties.getPassword());
        return dataSource;
    }
}