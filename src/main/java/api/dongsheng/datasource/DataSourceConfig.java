package api.dongsheng.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.HintManager;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    private static final String MASTER = "master";
    private static final String SLAVE = "slave";

    @Value("${druid.driverClassName}")
    private String driverClassName;

    @Value("${druid.initialSize}")
    private int initialSize;

    @Value("${druid.minIdle}")
    private int minIdle;

    @Value("${druid.maxActive}")
    private int maxActive;

    @Value("${druid.maxWait}")
    private int maxWait;

    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${druid.validationQuery}")
    private String validationQuery;

    @Value("${druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${druid.filters}")
    private String filters;

    @Value("{druid.connectionProperties}")
    private String connectionProperties;

    @Value("${druid.master.url}")
    private String masterUrl;

    @Value("${druid.master.username}")
    private String masterUsername;

    @Value("${druid.master.password}")
    private String masterPassword;

    @Value("${druid.slave.url}")
    private String slaveUrl;

    @Value("${druid.slave.username}")
    private String slaveUsername;

    @Value("${druid.slave.password}")
    private String slavePassword;

    private Logger logger = Logger.getLogger(DataSourceConfig.class);

    private DataSource masterDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(masterUrl);
        datasource.setUsername(masterUsername);
        datasource.setPassword(masterPassword);
        datasource.setDriverClassName(driverClassName);

        //configuration    
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization interceptor", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    private DataSource slaveDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(slaveUrl);
        datasource.setUsername(slaveUsername);
        datasource.setPassword(slavePassword);
        datasource.setDriverClassName(driverClassName);

        //configuration    
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization interceptor", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean
    @Primary
    public DataSource DataSource() throws SQLException {
        Map<String, DataSource> dataSoures = new HashMap<String, DataSource>();
        dataSoures.put(MASTER, masterDataSource());
        dataSoures.put(SLAVE, slaveDataSource());

        MasterSlaveRuleConfiguration configuration = new MasterSlaveRuleConfiguration();
        configuration.setName("master-slave");
        configuration.setMasterDataSourceName(MASTER);
        configuration.getSlaveDataSourceNames().add(SLAVE);

        return MasterSlaveDataSourceFactory.createDataSource(dataSoures, configuration, new HashMap<String, Object>());
    }

    @Around("execution(public * com.hopeful.model.service..*.*(..))")
    public Object chooseDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = null;

        HintManager hintManager = HintManager.getInstance();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional != null) {
            if (!transactional.readOnly()) {
                hintManager.setMasterRouteOnly();
            }
        } else {
            transactional = joinPoint.getTarget().getClass().getAnnotation(Transactional.class);
            if ((transactional != null) && (!transactional.readOnly())) {
                hintManager.setMasterRouteOnly();
            }
        }

        object = joinPoint.proceed();

        hintManager.close();

        return object;
    }
}
