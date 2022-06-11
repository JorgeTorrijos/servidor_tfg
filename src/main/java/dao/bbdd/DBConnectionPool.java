package dao.bbdd;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Configuration;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;

@Singleton
public class DBConnectionPool {

    private final Configuration config;
    private DataSource hirakiDatasource = null;

    @Inject
    public DBConnectionPool(Configuration config) {

        this.config = config;
        hirakiDatasource = getDataSourceHikari();
    }

    private DataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();


        config.setJdbcUrl(this.config.getRuta());
        config.setUsername(this.config.getUser());
        config.setPassword(this.config.getPassword());
        config.setDriverClassName(this.config.getDriver());
        config.setMaximumPoolSize(1);

        config.addDataSourceProperty(ConstantesDBConnectionPool.CACHE_PREP_STMTS, ConstantesDBConnectionPool.TRUE);
        config.addDataSourceProperty(ConstantesDBConnectionPool.PREP_STMT_CACHE_SIZE, ConstantesDBConnectionPool.VALUE);
        config.addDataSourceProperty(ConstantesDBConnectionPool.PREP_STMT_CACHE_SQL_LIMIT, ConstantesDBConnectionPool.VALUE_2048);

        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {

        return hirakiDatasource;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hirakiDatasource).close();
    }

}
