package com.sap.xm.scheduler.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.Spring;

import org.hibernate.Hibernate;
import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = {
		"com.sap.xm.scheduler.models" })
public class DataSourceConfig {

	@Primary
	@Bean
	@Qualifier("batchDataSource")
	public DataSource h2DataSource() throws SQLException {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new org.hsqldb.jdbcDriver());
		dataSource.setUrl("jdbc:hsqldb:mem:mydb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	
	@Bean
	@Qualifier("primaryDataSource")
	@ConfigurationProperties(prefix = "primary.datasource")
	public DataSource primaryDataSource() throws SQLException {
		return DataSourceBuilder.create().build();

	}

	@Bean
	@Qualifier("secondaryDataSource")
	@ConfigurationProperties(prefix = "secondary.datasource")
	public DataSource secondaryDataSource() throws SQLException {
		return DataSourceBuilder.create().build();

	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("secondaryDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean build = new LocalContainerEntityManagerFactoryBean();
		build.setPackagesToScan("com.sap.xm.scheduler.models");
		build.setDataSource(dataSource);

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(org.hibernate.jpa.AvailableSettings.NON_JTA_DATASOURCE, dataSource);
//		properties.put(org.hibernate.cfg.AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, true);
		properties.put(org.hibernate.cfg.AvailableSettings.USE_GET_GENERATED_KEYS, true);
		build.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		build.setJpaPropertyMap(properties);
		build.afterPropertiesSet();
		return build;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
