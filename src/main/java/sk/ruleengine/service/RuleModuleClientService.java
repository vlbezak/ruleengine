package sk.ruleengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public interface RuleModuleClientService {
	
	public String getRuleToCheck(String lastRule);
	
}
