package com.hnair.wallet.admincenter.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.hnair.wallet.admincenter.dao.service.impl.CommonServiceImpl;
import com.hnair.wallet.admincenter.dao.spi.impl.CommonDaoImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 7/21/2018 10:54 AM
 */
@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.slave.url}")
    private String dbUrl_slave;

    @Value("${spring.datasource.slave.username}")
    private String username_slave;

    @Value("${spring.datasource.slave.password}")
    private String password_slave;

    @Value("${spring.datasource.master.url}")
    private String dbUrl_master;

    @Value("${spring.datasource.master.username}")
    private String username_master;

    @Value("${spring.datasource.master.password}")
    private String password_master;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    private DruidDataSource parentDatasource() {
        final DruidDataSource druid = new DruidDataSource();
        druid.setDriverClassName(driverClassName);
        druid.setInitialSize(initialSize);
        druid.setMinIdle(minIdle);
        druid.setMaxActive(maxActive);
        druid.setMaxWait(maxWait);
        druid.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druid.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druid.setValidationQuery(validationQuery);
        druid.setTestWhileIdle(testWhileIdle);
        druid.setTestOnBorrow(testOnBorrow);
        druid.setTestOnReturn(testOnReturn);
        return druid;
    }

    @Bean
    public DataSource masterDbDatasource() {
        return getDataSource(dbUrl_master, username_master, password_master);
    }

    @Bean
    public DataSource slaveDbDatasource() {
        return getDataSource(dbUrl_slave, username_slave, password_slave);
    }

    private DataSource getDataSource(String dbUrl_slave, String username_slave, String password_slave) {
        final DruidDataSource dataSource = parentDatasource();
        dataSource.setUrl(dbUrl_slave);
        dataSource.setUsername(username_slave);
        dataSource.setPassword(password_slave);
        return dataSource;
    }

    /**
     * 配置从库
     *
     * @return 从库SqlSession
     */
    @Bean
    public SqlSessionFactoryBean slaveSqlSessionFactoryBean() throws IOException {
        final SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(slaveDbDatasource());
        final ClassPathResource configLocationResource = new ClassPathResource("EntityMappers/sql-map-config.xml");
        factoryBean.setConfigLocation(configLocationResource);
        final PathMatchingResourcePatternResolver multiResolver = new PathMatchingResourcePatternResolver();
        final Resource[] mapperLocationResource = multiResolver.getResources("EntityMappers/mapper/*.xml");
        factoryBean.setMapperLocations(mapperLocationResource);
        return factoryBean;
    }

    /**
     * 主库模板
     *
     * @return
     */
    @Bean
    public JdbcTemplate contentJdbcTemplate() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(masterDbDatasource());
        return jdbcTemplate;
    }

    /**
     * 从库模板
     *
     * @return
     */
    @Bean
    public JdbcTemplate contentJdbcQueryTemplate() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(slaveDbDatasource());
        return jdbcTemplate;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(masterDbDatasource());
        return transactionManager;
    }


    /**
     * 配置主库
     *
     * @return 主库qlSession
     */
    @Bean
    public SqlSessionFactoryBean masterSqlSessionFactoryBean() throws IOException {
        final SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(masterDbDatasource());
        final ClassPathResource configLocationResource = new ClassPathResource("EntityMappers/sql-map-config.xml");
        factoryBean.setConfigLocation(configLocationResource);
        final PathMatchingResourcePatternResolver multiResolver = new PathMatchingResourcePatternResolver();
        final Resource[] mapperLocationResource = multiResolver.getResources("EntityMappers/mapper/*.xml");
        factoryBean.setMapperLocations(mapperLocationResource);
        return factoryBean;
    }

    @Bean
    public SqlSessionTemplate masterSqlSession() throws Exception {
        return new SqlSessionTemplate(Objects.requireNonNull(masterSqlSessionFactoryBean().getObject()));
    }

    @Bean
    public SqlSessionTemplate slaveSqlSession() throws Exception {
        return new SqlSessionTemplate(Objects.requireNonNull(slaveSqlSessionFactoryBean().getObject()));
    }

    @Bean
    public CommonDaoImpl modelCommonDao() throws Exception {
        final CommonDaoImpl commonDao = new CommonDaoImpl();
        commonDao.setSqlSession(masterSqlSession());
        commonDao.setSqlSessionQurey(slaveSqlSession());
        return commonDao;
    }

    @Bean
    public CommonServiceImpl modelCommonService() throws Exception {
        final CommonServiceImpl service = new CommonServiceImpl();
        service.setCommonDao(modelCommonDao());
        return service;
    }


}
