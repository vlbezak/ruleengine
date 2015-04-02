package sk.ruleengine.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import sk.ruleengine.interceptors.HeaderRequestInterceptor;
import sk.ruleengine.interceptors.LoginInterceptor;

import com.sun.corba.se.impl.logging.InterceptorsSystemException;

@Configuration
@PropertySource("classpath:/ruleengine.properties")
@ComponentScan(basePackages = { "sk.ruleengine" }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfig {

	// :TODO - rework configuration

	@Resource
	Environment environment;
	
	@Bean
	String ruleModuleRestUrl() {
		return environment.getProperty("rulemodule.rulemodule1.uri");
	}
	
	@Bean
	String neo4jTransactionRestUrl() {
		return environment.getProperty("neo4j.transaction.rest.url");
	}

	@Bean(name="moduleRestTemplate")
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());

		return restTemplate;
	}
	
	@Bean(name="neo4jRestTemplate")
	RestTemplate neo4jRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		
		if(interceptors == null)
		{
			interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		}
		
		interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic bmVvNGo6emFxMTIz"));
		interceptors.add(new HeaderRequestInterceptor("Accept", "application/json; charset=UTF-8"));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		
		return restTemplate;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
		return new PropertySourcesPlaceholderConfigurer();
	}
}
