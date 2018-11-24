package com.muyuan.platform.common.dbmanger;

import com.muyuan.platform.common.configs.SkipDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author 范文武
 * @date 2018/11/23 17:05
 */
@Configuration
@MapperScan(basePackages = {"com.muyuan.platform"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class SmartDataConfig {
    // 配置数据源
    @Bean(name = "dataSource")
    public DataSource smartDeviceDataSource(SkipDataSource dataSource) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dataSource.getUrl());
        mysqlXaDataSource.setUser(dataSource.getUsername());
        mysqlXaDataSource.setPassword(dataSource.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("dataSource");
        return xaDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory smartDeviceSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate smartDeviceSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}