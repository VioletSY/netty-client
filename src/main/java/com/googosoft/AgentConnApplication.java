package com.googosoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 代理端启动类
 * @author songyan
 * @date 2020年4月22日 下午9:43:45
 * @desc
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class AgentConnApplication
{
    public static void main( String[] args ){
        SpringApplication.run(AgentConnApplication.class, args);
    }
     
}
