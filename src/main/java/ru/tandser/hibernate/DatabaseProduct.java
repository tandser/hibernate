package ru.tandser.hibernate;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.hibernate.dialect.PostgreSQL95Dialect;

public enum DatabaseProduct {

    POSTGRESQL((dataSource, connectionUrl) -> {
        if (connectionUrl != null) {
            throw new IllegalArgumentException("PostgreSQL XADataSource doesn't support connection URLs");
        }

        dataSource.setClassName("org.postgresql.xa.PGXADataSource");

        dataSource.getDriverProperties().put("serverName", "localhost");
        dataSource.getDriverProperties().put("user",       "postgres");
        dataSource.getDriverProperties().put("password",   "postgres");
    },
    PostgreSQL95Dialect.class.getName());

    public DataSourceConfiguration configuration;
    public String                  hibernateDialect;

    DatabaseProduct(DataSourceConfiguration configuration, String hibernateDialect) {
        this.configuration    = configuration;
        this.hibernateDialect = hibernateDialect;
    }

    @FunctionalInterface
    public interface DataSourceConfiguration {

        void configure(PoolingDataSource dataSource, String connectionUrl);
    }
}