/**
 * 
 */
package com.focalcxm.facedoc.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author FocalCXM
 * @since 5/30/2021
 *
 */
@Configuration
public class MySqlJPAConfig {
	
	@Value("${spring.mysqldb.url}")
	private String url;
	
	@Value("${spring.mysqldb.username}")
	private String username;

	@Value("${spring.mysqldb.password}")
	private String password;
	
	@Value("${spring.mysqldb.driverclass}")
	private String driverclass;
	
	
	@Bean
	public DataSource mySqlDS() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driverclass);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean
	public JdbcTemplate mysqlTemplate() {
		JdbcTemplate temp = new JdbcTemplate(mySqlDS());
		return temp;
	}
	
}
