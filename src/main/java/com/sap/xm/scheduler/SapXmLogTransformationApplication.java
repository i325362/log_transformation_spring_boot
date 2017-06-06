package com.sap.xm.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages="com.sap.xm.scheduler")
@EnableJpaRepositories("com.sap.xm.scheduler.repo")
@EntityScan("com.sap.xm.scheduler.models")
@EnableAspectJAutoProxy
@EnableScheduling
public class SapXmLogTransformationApplication {//extends SpringBootServletInitializer {
//
//	 @Override
//	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//	        return application.sources(SapXmLogTransformationApplication.class);
//	    }
//	 
	public static void main(String[] args) {
		SpringApplication.run(SapXmLogTransformationApplication.class, args);
	}
}
