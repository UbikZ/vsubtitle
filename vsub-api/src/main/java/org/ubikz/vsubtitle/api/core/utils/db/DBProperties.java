package org.ubikz.vsubtitle.api.core.utils.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdbc")
class DBProperties {
    private String host;
    private int port;
    private String dbName;
    private String username;
    private String password;

    public String toString() {
        return this.getClass().getName() + " = " +
                "host : " + this.host + ", " +
                "port : " + this.port + ", " +
                "dbName : " + this.dbName + ", " +
                "username : " + this.username + ", " +
                "password " + this.password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}