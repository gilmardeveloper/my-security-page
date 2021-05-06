package com.example.mysecuritypage;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
public class MySecurityPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySecurityPageApplication.class, args);
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("pt", "BR"));
		return cookieLocaleResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	
	@Bean
	public DataSource getDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		dataSource.setUser("root");
		dataSource.setPassword("");
		
		try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		dataSource.setJdbcUrl("jdbc:mysql://localhost/db_secpage?useUnicode=yes&characterEncoding=UTF-8");

		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(10);
		dataSource.setNumHelperThreads(5);
		dataSource.setIdleConnectionTestPeriod(100);
		
		return dataSource;
	}
}
