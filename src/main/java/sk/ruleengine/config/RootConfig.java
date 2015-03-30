package sk.ruleengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"sk.ruleengine"}, excludeFilters={
 @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
		})
public class RootConfig {

	//:TODO - rework configuration
	
	@Bean
	String ruleModuleRestUrl(){
		return "http://localhost:8080/rulemodule/rulemodule/checkNextRule";	
	}
	
	@Bean
	RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		return restTemplate;
	}
}
