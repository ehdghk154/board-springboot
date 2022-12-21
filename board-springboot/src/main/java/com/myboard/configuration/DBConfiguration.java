package com.myboard.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfiguration {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    // hikariCP 객체 생성
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }
    
    // DataSource 객체 생성 (커넥션 풀을 지원하기 위한 인터페이스)
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
    
    // SqlSessionFactory 객체 생성 (MyBatis와 Spring의 연동 모듈로 사용. 
    //                          MyBatis XML Mapper, 설정 파일 위치 등을 지정하고, SqlSessionFactoryBean 자체가 아닌, getObject 메서드가 리턴하는 SqlSessionFactory를 생성.)
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        
        // getResources 메서드의 인자로 지정된 패턴에 포함되는 XML Mapper를 인식하도록 하는 역할
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
        
        // BoardMapper XML에서 parameterType과 resultType은 클래스의 풀 패키지 경로가 포함되어야 함.
        // BoardDTO와 같이 클래스의 이름만 지정했는데, 해당 메서드를 사용해서 풀 패키지 경로를 생략할 수 있다.
        // 패키지의 패턴이 복잡하다면, com.board.domain과 같이 전체를 의미하는 애스터리스크("")를 지정할 수 있다.
        factoryBean.setTypeAliasesPackage("com.myboard.domain");
        
        // 마이바티스 설정과 관련된 빈(Bean)을 설정 파일로 지정
        factoryBean.setConfiguration(mybatisConfig());
        return factoryBean.getObject();
    }
    
    // sqlSession 객체 생성
    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
    
    // application.properties에서 mybatis.configuration으로 시작하는 모든 설정을 읽어 들여 빈(Bean)으로 등록
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }
}
