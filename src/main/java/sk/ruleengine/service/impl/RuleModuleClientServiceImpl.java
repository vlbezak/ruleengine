package sk.ruleengine.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sk.ruleengine.bo.Rule;
import sk.ruleengine.service.RuleModuleClientService;

@Service
public class RuleModuleClientServiceImpl implements RuleModuleClientService {

	@Autowired
	@Qualifier("moduleRestTemplate")
	RestTemplate restTemplate;
	
	@Autowired
	String ruleModuleRestUrl;
	
	@Override
	public String getRuleToCheck(String lastRule) {
		Rule rule = new Rule("dummy");
	
		Map<String,String> vars = new HashMap<String,String>();
		
		String ruleReturned = restTemplate.getForObject(ruleModuleRestUrl, String.class, vars);
		
		System.out.println("RuleModuleClientServiceImpl: ruleModuleReturned rule:" + ruleReturned);
		
		//System.out.println("Returned rule:" + ruleReturned.getCypherStatement());
		
		//return ruleReturned.getCypherStatement();
		
		return ruleReturned;
	}
	
}
