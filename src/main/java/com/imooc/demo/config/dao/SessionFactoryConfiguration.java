package com.imooc.demo.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 事务提交配置类
 * @author Huangfobo
 * @create 2018-09-29 15:10
 **/
@Configuration
public class SessionFactoryConfiguration {

    @Autowired
    @Qualifier("dataSource") /*指定要注入的Bean*/
    private DataSource dataSource;

    /*mybatis-config.xml配置文件的路径*/
    private  static String mybatisConfigFilePath;

    @Value("${mybatis_config_file}")
    public void setMybatisConfigFile(String mybatisConfigFilePath) {

        SessionFactoryConfiguration.mybatisConfigFilePath = mybatisConfigFilePath;
    }

     /*mybatis mapper文件所在路径*/
    private static String mapperPath;

    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {

        SessionFactoryConfiguration.mapperPath = mapperPath;
    }

    /*实体类所在的package*/
    @Value("${entity_package}")
    private String entityPackage;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置mybatis configuration 扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
        // 添加mapper 扫描路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        // 设置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置typeAlias 包扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return sqlSessionFactoryBean;
    }
}
