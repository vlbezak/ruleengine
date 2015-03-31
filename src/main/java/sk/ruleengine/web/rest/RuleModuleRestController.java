package sk.ruleengine.web.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import sk.ruleengine.service.RuleModuleClientService;

@Controller
@RequestMapping("/ruleprocessor")
public class RuleModuleRestController {

	@Autowired
	RuleModuleClientService ruleModuleClientService;

	@Autowired
	@Qualifier("neo4jRestTemplate")
	RestTemplate restTemplate;
	
	
	@RequestMapping(value = "/cmdb", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String storeObjects(@RequestBody String cypherStatement, HttpServletResponse response) {
		System.out.println("RuleEngine: REST: received cypherStatement:"
				+ cypherStatement);

		String toReturn = "OK";

		long transStartTime = System.currentTimeMillis();
		long transFinishTime;
		int ruleCount = 1;
		
		
		URI location = restTemplate.postForLocation("http://localhost:7474/db/data/transaction", "", new HashMap<String, String>());
		
		String[] pathList = location.getPath().split("\\/");
		String transactionId =pathList[pathList.length-1];
		
		
		System.out.println("XXX location: " + location + " trId: " + transactionId);
		
		try 
		{
			for (int i = 0; i < ruleCount; i++) {
				
				String cypherQuery = ruleModuleClientService
						.getRuleToCheck("dummy");
				System.out
						.println("RuleEngine: REST: called ruleModule. returned rule:"
								+ cypherQuery);

				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("param1", "ParamValue1");

				ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:7474/db/data/transaction" + "/" + transactionId, "{'statements' : [ {'statement' : '" + cypherQuery + "' }]}" , String.class);
				System.out.println("XXX result: " + result);
				
				restTemplate.postForEntity("http://localhost:7474/db/data/transaction" + "/" + transactionId + "/commit", "" , String.class);
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			restTemplate.delete("http://localhost:7474/db/data/transaction" + "/" + transactionId);
		}
		
		return null;
/*
		try {

			// :TODO: Setting created for now
			// :TODO try to do it other way (advice ?)

			for (int i = 0; i < ruleCount; i++) {
				
				String cypherQuery = ruleModuleClientService
						.getRuleToCheck("dummy");
				System.out
						.println("RuleEngine: REST: called ruleModule. returned rule:"
								+ cypherQuery);

				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("param1", "ParamValue1");

				ResponseEntity<String> result = restTemplate.postForEntity(location, "{'statements' : [ {'statement' : '" + cypherQuery + "' }]}" , String.class);
				
				System.out.println("XXX result: " + result);

				Iterator it = result.iterator();

				while (it.hasNext()) {
					Object o = it.next();
					// System.out.println("Iterating: " + o.getClass().getName()
					// +
					// " : " + o.toString());
					System.out.println("********************");
					if (o instanceof HashMap) {

						System.out.println("It is hashMap:");
						System.out.println("==============");
						HashMap<String, Object> map = (HashMap<String, Object>) o;
						Set<String> keySet = map.keySet();

						Iterator keySetIt = keySet.iterator();
						while (keySetIt.hasNext()) {
							String key = (String) keySetIt.next();

							Object value = map.get(key);
							if (value instanceof String) {
								System.out.println(key + ":" + value);
							} else if ( value != null ) {
								System.out.println("!!!!!" + key
										+ " - not String:"
										+ value.getClass().getName());
							}
							else{
								System.out.println(" value is null");
							}
						}

					}
				}
				result.finish();

				System.out.println("after query========:" + i);

			}
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			toReturn = "FAILURE";
			tx.failure();
		} finally {
			tx.close();
		}
		
		transFinishTime = System.currentTimeMillis();
		
		System.out.println("Transaction time:" + (transFinishTime - transStartTime) + " ms");

		// :TODO: only temporary
		return toReturn;*/
	}

	@RequestMapping(value = "/cmdb", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String test() {
		return "OK";
	}

}
